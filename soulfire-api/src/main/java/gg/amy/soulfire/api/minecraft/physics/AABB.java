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
@Bridge("net.minecraft.world.phys.AABB")
public interface AABB {
    @BridgeMethod("<init>(double,double,double,double,double,double)")
    static AABB create(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        return unimplemented();
    }

    @BridgeMethod("<init>(net.minecraft.core.BlockPos)")
    static AABB create(@Nonnull final BlockPos pos) {
        return unimplemented();
    }

    @BridgeMethod("<init>(net.minecraft.core.BlockPos,net.minecraft.core.BlockPos)")
    static AABB create(@Nonnull final BlockPos min, @Nonnull final BlockPos max) {
        return unimplemented();
    }

    @BridgeMethod("intersects(net.minecraft.world.phys.AABB)")
    boolean intersects(@Nonnull final AABB bb);

    @BridgeMethod("intersects(double,double,double,double,double,double)")
    boolean intersects(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ);
}
