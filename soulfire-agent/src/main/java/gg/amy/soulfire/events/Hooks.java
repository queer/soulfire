package gg.amy.soulfire.events;

import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.api.SoulfireImpl;
import gg.amy.soulfire.api.events.event.*;
import gg.amy.soulfire.api.minecraft.resource.SimpleReloadableResourceManager;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/19/21.
 */
@SuppressWarnings("unused")
public final class Hooks {
    private Hooks() {
    }

    public static void fireInit() {
        ((SoulfireImpl) Soulfire.soulfire()).modLoader().init();
        Soulfire.soulfire().bus().fire(new MinecraftInit());
    }

    public static void fireResourceLoad() {
        Soulfire.soulfire().bus().fire(new ResourceInit());
    }

    public static void fireGameLoaded() {
        Soulfire.soulfire().bus().fire(new MinecraftReady());
    }

    public static void fireResourceManagerReload(@Nonnull final SimpleReloadableResourceManager manager) {
        Soulfire.soulfire().bus().fire(new ResourceManagerReload(manager));
    }

    public static void fireCreativeSearchUpdate(@Nonnull final String search) {
        Soulfire.soulfire().bus().fire(new CreativeSearchUpdate(search));
    }
}
