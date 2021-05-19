package gg.amy.soulfire.api.minecraft.physics;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.world.phys.shapes.CubeVoxelShape")
public interface CubeVoxelShape {
    @BridgeMethod("<init>(net.minecraft.world.phys.shapes.DiscreteVoxelShape)")
    static CubeVoxelShape create(@Nonnull final DiscreteVoxelShape shape) {
        return unimplemented();
    }
}
