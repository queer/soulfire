package gg.amy.soulfire.loader;

import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.api.events.event.game.MinecraftInit;
import gg.amy.soulfire.api.events.event.game.MinecraftReady;
import gg.amy.soulfire.api.events.event.resource.ResourceManagerReload;
import gg.amy.soulfire.api.minecraft.Minecraft;
import gg.amy.soulfire.api.minecraft.resource.FileResourcePack;
import gg.amy.soulfire.api.minecraft.resource.SimpleReloadableResourceManager;
import gg.amy.soulfire.api.mod.Mod;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author amy
 * @since 5/19/21.
 */
public final class ModLoader {
    private final Logger logger = LogManager.getLogger();
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Collection<Object> loadedMods = new ArrayList<>();
    private final Collection<File> modJars = new ArrayList<>();
    private boolean init;
    private boolean refire;

    public void init() {
        Soulfire.soulfire().bus().register(MinecraftInit.class, event -> {
            if(!init) {
                init = true;
                logger.info("Initialising soulfire from Minecraft gamedir {}", Minecraft.getInstance().gameDir());

                locateModJars();
                loadMods();
            }
            if(init && !refire) {
                refire = true;
                Soulfire.soulfire().bus().fire(new MinecraftInit());
            }
            return event;
        });
        Soulfire.soulfire().bus().register(ResourceManagerReload.class, event -> {
            if(event.repo().equals(Minecraft.getInstance().resourceManager())) {
                logger.info("Reloading mod assets due to repository reload!");
                loadModAssets();
            }
            return event;
        });
        Soulfire.soulfire().bus().register(MinecraftReady.class, event -> {
            //noinspection CodeBlock2Expr
            return event;
        });
    }

    private void locateModJars() {
        final var files = Soulfire.soulfire().modsDir().listFiles();
        if(files == null) {
            throw new IllegalStateException("mods dir files is null");
        }
        for(final var modJar : files) {
            if(modJar.getName().toLowerCase().endsWith(".jar")) {
                modJars.add(modJar);
            } else {
                logger.warn("Unknown non-mod file found in mods directory: {}", modJar);
            }
        }
    }

    private void loadMods() {
        for(final var modJar : modJars) {
            try {
                logger.info("Loading mod from JAR: {}", modJar);
                try(final var graph = new ClassGraph()
                        .overrideClasspath(modJar)
                        .overrideClassLoaders(new JarClassLoader(modJar))
                        .ignoreParentClassLoaders()
                        .enableAllInfo()
                        .scan()) {
                    logger.info("Loading all classes...");
                    graph.getAllClasses().forEach(c -> logger.info("Loading class: {}", c.loadClass()));

                    logger.info("Loading @Mod classes...");
                    final var modClasses = graph.getClassesWithAnnotation(Mod.class.getName())
                            .stream()
                            .map(ClassInfo::loadClass)
                            .collect(Collectors.toList());
                    if(modClasses.isEmpty()) {
                        logger.warn("This mod JAR has no @Mod classes in it!? Are you sure it's supposed to be here?");
                    } else if(modClasses.size() > 1) {
                        logger.error("This mod JAR has multiple @Mod classes in it! You can only have one mod per JAR file. List of @Mod classes: {}", modClasses);
                        throw new IllegalStateException("Too many @Mod classes in a single JAR!");
                    } else {
                        final Class<?> modClass = modClasses.get(0);
                        final var mod = modClass.getConstructor().newInstance();
                        loadedMods.add(mod);
                        logger.info("Loaded mod {}: {}", mod.getClass().getAnnotation(Mod.class).value(), mod.getClass());
                    }
                }
            } catch(final Throwable t) {
                logger.error("Encountered error loading mod JAR {}:", modJar, t);
            }
        }
    }

    private void loadModAssets() {
        for(final var modJar : modJars) {
            ((SimpleReloadableResourceManager) Minecraft.getInstance().resourceManager()).add(FileResourcePack.of(modJar));
            logger.info("Loaded mod resource pack: {}", modJar);
        }
    }
}
