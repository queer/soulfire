package gg.amy.soulfire.api.minecraft.block.entity;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/28/21.
 */
@Bridge("net.minecraft.world.level.block.entity.ChestBlockEntity")
public interface ChestTileEntity extends TileEntity {
    @Nonnull
    @BridgeMethod("<init>()")
    static ChestTileEntity create() {
        return unimplemented();
    }
}
