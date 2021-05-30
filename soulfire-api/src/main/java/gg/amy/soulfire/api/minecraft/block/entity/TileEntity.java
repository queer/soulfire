package gg.amy.soulfire.api.minecraft.block.entity;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.annotations.TransformAfter;
import gg.amy.soulfire.api.minecraft.block.BlockPos;
import gg.amy.soulfire.api.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/28/21.
 */
@TransformAfter(TileEntityType.class)
@Bridge("net.minecraft.world.level.block.entity.BlockEntity")
public interface TileEntity {
    @BridgeField("type")
    TileEntityType type();

    @BridgeMethod("setLevelAndPosition(net.minecraft.world.level.Level,net.minecraft.core.BlockPos)")
    void setWorldAndPos(@Nonnull final World world, @Nonnull final BlockPos pos);
}
