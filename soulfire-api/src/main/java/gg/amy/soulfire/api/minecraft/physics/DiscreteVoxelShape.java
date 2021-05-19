package gg.amy.soulfire.api.minecraft.physics;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.world.phys.shapes.DiscreteVoxelShape")
public interface DiscreteVoxelShape {
    @BridgeMethod("getXSize()")
    int xSize();

    @BridgeMethod("getYSize()")
    int ySize();

    @BridgeMethod("getZSize()")
    int zSize();
}
