package gg.amy.soulfire.loader;

import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.api.events.event.GameReady;
import gg.amy.soulfire.api.events.event.MinecraftInit;
import gg.amy.soulfire.api.events.event.ResourceManagerReload;
import gg.amy.soulfire.api.minecraft.Minecraft;
import gg.amy.soulfire.api.minecraft.block.Block;
import gg.amy.soulfire.api.minecraft.block.BlockProperties;
import gg.amy.soulfire.api.minecraft.block.Material;
import gg.amy.soulfire.api.minecraft.item.BlockItem;
import gg.amy.soulfire.api.minecraft.item.Identifier;
import gg.amy.soulfire.api.minecraft.item.ItemCategory;
import gg.amy.soulfire.api.minecraft.item.ItemProperties;
import gg.amy.soulfire.api.minecraft.registry.Registries;
import gg.amy.soulfire.api.minecraft.registry.Registry;
import gg.amy.soulfire.api.minecraft.resource.FileResourcePack;
import gg.amy.soulfire.api.minecraft.resource.SimpleReloadableResourceManager;
import gg.amy.soulfire.api.mod.Mod;
import gg.amy.soulfire.api.mod.lifecycle.Init;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.reflect.Modifier;
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

    public void init() {
        Soulfire.soulfire().bus().register(MinecraftInit.class, event -> {
            logger.info("Initialising soulfire from Minecraft gamedir {}", Minecraft.getInstance().gameDir());

            locateModJars();
            loadMods();
            return event;
        });
        Soulfire.soulfire().bus().register(ResourceManagerReload.class, event -> {
            logger.info("Reloading mod assets due to repository reload!");
            loadModAssets();
            return event;
        });
        Soulfire.soulfire().bus().register(GameReady.class, event -> event);
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
                        for(final var m : modClass.getDeclaredMethods()) {
                            if(m.isAnnotationPresent(Init.class)) {
                                if(!Modifier.isPublic(m.getModifiers())) {
                                    throw new IllegalStateException(String.format("Unable to access @Init for mod %s! MOD LOADING WILL NOT WORK RIGHT.", modClass));
                                }
                                if(Modifier.isStatic(m.getModifiers())) {
                                    throw new IllegalStateException(String.format("@Init for mod %s is static! MOD LOADING WILL NOT WORK RIGHT.", modClass));
                                }
                                m.invoke(mod);
                            }
                        }
                        logger.info("Loaded mod: {}", mod);
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
