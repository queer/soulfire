package gg.amy.soulfire.api.minecraft.block;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.world.level.BlockGetter")
public interface BlockGetter {
//    @BridgeMethod("getBlockEntity(net.minecraft.core.BlockPos)")
//    BlockEntity getBlockEntity(@Nonnull BlockPos pos);

    @BridgeMethod("getBlockState(net.minecraft.core.BlockPos)")
    BlockState getBlockState(@Nonnull BlockPos pos);
}
