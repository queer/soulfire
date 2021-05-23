package gg.amy.soulfire.api.minecraft.physics;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.block.BlockPos;

/**
 * @author amy
 * @since 5/23/21.
 */
@Bridge("net.minecraft.world.phys.BlockHitResult")
public interface BlockHitResult extends HitResult {
    @BridgeMethod("getBlockPos()")
    BlockPos pos();

    @BridgeMethod("getType()")
    HitResultType type();
}
