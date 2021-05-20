package gg.amy.soulfire.api;

import gg.amy.soulfire.api.events.EventBus;
import gg.amy.soulfire.api.minecraft.Minecraft;
import gg.amy.soulfire.events.SoulfireEventBus;
import gg.amy.soulfire.loader.ModLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * @author amy
 * @since 5/19/21.
 */
public final class SoulfireImpl implements Soulfire {
    @SuppressWarnings("unused")
    public static final Soulfire INSTANCE = new SoulfireImpl();
    private static final Logger LOGGER = LogManager.getLogger();

    private final EventBus bus = new SoulfireEventBus();
    private final ModLoader modLoader = new ModLoader();

    private SoulfireImpl() {
    }


    @Override
    public void init() {
        LOGGER.info("soulfire init.");
        if(baseDir().mkdirs()) {
            LOGGER.info("Created soulfire base dir.");
        }
        if(modsDir().mkdirs()) {
            LOGGER.info("Created soulfire mods dir.");
        }
        if(configDir().mkdirs()) {
            LOGGER.info("Created soulfire mods config dir.");
        }
    }

    @Nonnull
    @Override
    public EventBus bus() {
        return bus;
    }

    @Nonnull
    @Override
    public File baseDir() {
        return new File(Minecraft.getInstance().gameDir(), "soulfire");
    }

    @Nonnull
    @Override
    public File modsDir() {
        return new File(baseDir(), "mods");
    }

    @Nonnull
    @Override
    public File configDir() {
        return new File(baseDir(), "config");
    }

    @Nonnull
    public ModLoader modLoader() {
        return modLoader;
    }
}
