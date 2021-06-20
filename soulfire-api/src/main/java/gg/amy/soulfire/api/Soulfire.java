package gg.amy.soulfire.api;

import gg.amy.soulfire.api.events.EventBus;
import gg.amy.soulfire.api.thesaurus.Thesaurus;

import javax.annotation.Nonnull;
import java.io.File;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * The soulfire base. Use this to access non-Minecraft soulfire APIs, such as
 * the {@link EventBus}.
 *
 * @author amy
 * @since 5/19/21.
 */
public interface Soulfire {
    /**
     * @return The singleton soulfire instance.
     */
    @Nonnull
    static Soulfire soulfire() {
        return unimplemented();
    }

    // TODO: Gate this
    void init();

    /**
     * @return The current event bus.
     */
    @Nonnull
    EventBus bus();

    /**
     * @return The soulfire base directory. Should be {@code .minecraft/soulfire}.
     */
    @Nonnull
    File baseDir();

    /**
     * @return The mods directory.
     */
    @Nonnull
    File modsDir();

    /**
     * @return The mod config directory.
     */
    // TODO: Use this
    @Nonnull
    File configDir();

    /**
     * @return The thesaurus used for item/block mappings.
     */
    Thesaurus thesaurus();
}
