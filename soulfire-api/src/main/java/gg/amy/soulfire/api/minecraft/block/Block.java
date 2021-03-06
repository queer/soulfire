package gg.amy.soulfire.api.minecraft.block;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.annotations.TransformAfter;
import gg.amy.soulfire.api.minecraft.physics.BlockHitResult;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/18/21.
 */
@TransformAfter(BlockHitResult.class)
@Bridge("net.minecraft.world.level.block.Block")
public interface Block extends BlockBehaviour {
    @Nonnull
    @BridgeMethod("<init>(net.minecraft.world.level.block.state.BlockBehaviour$Properties)")
    static Block create(@Nonnull final BlockProperties properties) {
        return unimplemented();
    }
}
