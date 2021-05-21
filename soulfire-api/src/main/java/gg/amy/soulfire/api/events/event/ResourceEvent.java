package gg.amy.soulfire.api.events.event;

import gg.amy.soulfire.api.events.event.item.ItemInteraction;
import gg.amy.soulfire.api.minecraft.registry.Identifier;

import javax.annotation.Nonnull;

/**
 * An event fired for a specific resource. An example is {@link ItemInteraction}.
 *
 * @author amy
 * @since 5/20/21.
 */
@FunctionalInterface
public interface ResourceEvent {
    @Nonnull
    Identifier identifier();
}
