package gg.amy.soulfire.api.minecraft.physics;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

/**
 * @author amy
 * @since 5/30/21.
 */
@Bridge("net.minecraft.core.Vec3i")
public interface Vec3i {
    @BridgeMethod("getX()")
    int x();

    @BridgeMethod("getY()")
    int y();

    @BridgeMethod("getZ()")
    int z();
}
