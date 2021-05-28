package gg.amy.soulfire.api.minecraft.block.entity;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/28/21.
 */
@Bridge("net.minecraft.world.level.block.entity.BlockEntity")
public interface TileEntity {
    // TODO: Probably shouldn't have this
    @BridgeMethod("<init>(net.minecraft.world.level.block.entity.BlockEntityType)")
    static TileEntity create(@Nonnull final TileEntityType type) {
        return unimplemented();
    }

    @BridgeField("type")
    TileEntityType type();
}
