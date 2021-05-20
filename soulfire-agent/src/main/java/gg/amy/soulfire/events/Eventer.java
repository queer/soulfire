package gg.amy.soulfire.events;

import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.api.events.event.GameReady;
import gg.amy.soulfire.api.events.event.MinecraftInit;
import gg.amy.soulfire.api.events.event.ResourceManagerReload;
import gg.amy.soulfire.api.events.event.ResourceInit;
import gg.amy.soulfire.api.minecraft.resource.SimpleReloadableResourceManager;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/19/21.
 */
@SuppressWarnings("unused")
public final class Eventer {
    private Eventer() {
    }

    public static void fireInit() {
        Soulfire.soulfire().bus().fire(new MinecraftInit());
    }

    public static void fireResourceLoad() {
        Soulfire.soulfire().bus().fire(new ResourceInit());
    }

    public static void fireGameLoaded() {
        Soulfire.soulfire().bus().fire(new GameReady());
    }

    public static void fireResourceManagerReload(@Nonnull final SimpleReloadableResourceManager manager) {
        Soulfire.soulfire().bus().fire(new ResourceManagerReload(manager));
    }
}
