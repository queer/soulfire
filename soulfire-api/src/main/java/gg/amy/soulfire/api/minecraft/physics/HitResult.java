package gg.amy.soulfire.api.minecraft.physics;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.annotations.TransformAfter;

/**
 * @author amy
 * @since 5/23/21.
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
@Bridge("net.minecraft.world.phys.HitResult")
@TransformAfter(Vec3.class)
public interface HitResult {
//    @BridgeMethod("<init>(net.minecraft.world.phys.Vec3)")
//    static HitResult of() {
//        return unimplemented();
//    }

    @BridgeMethod("getLocation()")
    Vec3 location();
}
