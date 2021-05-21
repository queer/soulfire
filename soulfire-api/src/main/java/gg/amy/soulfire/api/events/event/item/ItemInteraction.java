package gg.amy.soulfire.api.events.event.item;

import gg.amy.soulfire.api.events.event.ResourceEvent;
import gg.amy.soulfire.api.minecraft.item.InteractionResult;
import gg.amy.soulfire.api.minecraft.item.Item;
import gg.amy.soulfire.api.minecraft.item.ItemUseContext;
import gg.amy.soulfire.api.minecraft.registry.Identifier;

/**
 * Fired when an item is used. Change the {@link #result} to change, well, the
 * result of using the item.
 *
 * @author amy
 * @since 5/20/21.
 */
public record ItemInteraction(Item item, Identifier identifier, ItemUseContext ctx,
                              InteractionResult result) implements ResourceEvent {
    @Override
    public String toString() {
        return String.format("%s:%s", identifier.namespace(), identifier.id());
    }
}
