package gg.amy.soulfire.api.minecraft.physics;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.world.phys.shapes.Shapes")
public interface VoxelShapes {
    @BridgeMethod("empty()")
    static VoxelShape empty() {
        return unimplemented();
    }
    @BridgeMethod("block()")
    static VoxelShape block() {
        return unimplemented();
    }
    @BridgeMethod("box(double,double,double,double,double,double)")
    static VoxelShape box(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        return unimplemented();
    }

    @BridgeMethod("create(net.minecraft.world.phys.AABB)")
    static VoxelShape create(@Nonnull final AABB bb) {
        return unimplemented();
    }
}
