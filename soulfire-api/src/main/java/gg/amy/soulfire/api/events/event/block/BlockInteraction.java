package gg.amy.soulfire.api.events.event.block;

import gg.amy.soulfire.api.events.event.ResourceEvent;
import gg.amy.soulfire.api.minecraft.block.Block;
import gg.amy.soulfire.api.minecraft.block.BlockPos;
import gg.amy.soulfire.api.minecraft.entity.Player;
import gg.amy.soulfire.api.minecraft.item.InteractionHand;
import gg.amy.soulfire.api.minecraft.item.InteractionResult;
import gg.amy.soulfire.api.minecraft.registry.Identifier;
import gg.amy.soulfire.api.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/23/21.
 */
public record BlockInteraction(@Nonnull Block block, @Nonnull Identifier identifier, @Nonnull World world,
                               @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand,
                               @Nonnull InteractionResult result)
        implements ResourceEvent {
    @Override
    public String toString() {
        return String.format("%s:%s", identifier.namespace(), identifier.id());
    }
}
