package gg.amy.soulfire.api.minecraft.physics;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.annotations.Nontransforming;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/19/21.
 */
@Nontransforming
@Bridge("net.minecraft.world.phys.shapes.Shapes")
public interface VoxelShapes {
    // TODO: If we try to call the vanilla empty()/block() methods, they fuck up. Why?

    static VoxelShape empty() {
        return box(0, 0, 0, 0, 0, 0);
    }

    static VoxelShape block() {
        return box(0, 0, 0, 1, 1, 1);
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
