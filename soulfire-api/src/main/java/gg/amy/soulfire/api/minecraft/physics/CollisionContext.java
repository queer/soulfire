package gg.amy.soulfire.api.minecraft.physics;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.block.BlockPos;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.world.phys.shapes.CollisionContext")
public interface CollisionContext {
    @BridgeMethod("empty()")
    static CollisionContext empty() {
        return unimplemented();
    }

    @BridgeMethod("isAbove(net.minecraft.world.phys.shapes.VoxelShape,net.minecraft.core.BlockPos,boolean)")
    boolean isAbove(@Nonnull VoxelShape shape, BlockPos pos, boolean unknown);
}
