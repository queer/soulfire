package gg.amy.soulfire.api.events.event.block;

import gg.amy.soulfire.api.events.event.ResourceEvent;
import gg.amy.soulfire.api.minecraft.block.Block;
import gg.amy.soulfire.api.minecraft.block.BlockPos;
import gg.amy.soulfire.api.minecraft.block.BlockState;
import gg.amy.soulfire.api.minecraft.registry.Identifier;
import gg.amy.soulfire.api.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/30/21.
 */
public record BlockPlace(@Nonnull Block block, @Nonnull Identifier identifier, @Nonnull World world,
                         @Nonnull BlockPos pos, @Nonnull BlockState state) implements ResourceEvent {
}
