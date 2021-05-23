package gg.amy.soulfire.api.minecraft.physics;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.entity.Entity;

/**
 * @author amy
 * @since 5/23/21.
 */
@Bridge("net.minecraft.world.phys.EntityHitResult")
public interface EntityHitResult extends HitResult {
    @BridgeMethod("getEntity()")
    Entity entity();

    @BridgeMethod("getType()")
    HitResultType type();
}
