package gg.amy.soulfire.api.minecraft.block.entity;

import gg.amy.soulfire.annotations.Constructor;
import gg.amy.soulfire.annotations.Shim;
import gg.amy.soulfire.api.minecraft.block.BlockPos;
import gg.amy.soulfire.api.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/28/21.
 */
@Shim("net.minecraft.world.level.block.entity.BlockEntity")
public class SoulfireTileEntity<T extends TileEntity> implements TileEntity {
    private final TileEntityType<T> type;

    @Constructor("<init>(net.minecraft.world.level.block.entity.BlockEntityType)")
    public SoulfireTileEntity(@Nonnull final TileEntityType<T> type) {
        this.type = type;
    }

    @Override
    public TileEntityType<T> type() {
        return type;
    }

    @Override
    public void setWorldAndPos(@Nonnull final World world, @Nonnull final BlockPos pos) {
        world.addTileEntity(this);
    }
}
