package gg.amy.soulfire.api;

import gg.amy.soulfire.api.events.EventBus;

import javax.annotation.Nonnull;
import java.io.File;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/19/21.
 */
public interface Soulfire {
    @Nonnull
    static Soulfire soulfire() {
        return unimplemented();
    }

    void init();

    @Nonnull
    EventBus bus();

    @Nonnull
    File baseDir();

    @Nonnull
    File modsDir();

    @Nonnull
    File configDir();
}
