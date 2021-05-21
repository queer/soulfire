package gg.amy.soulfire.api.events.event;

import gg.amy.soulfire.api.minecraft.registry.Identifier;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/20/21.
 */
@FunctionalInterface
public interface ResourceEvent {
    @Nonnull
    Identifier identifier();
}
