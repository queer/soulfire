package gg.amy.soulfire.api.minecraft.physics;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;

/**
 * @author amy
 * @since 5/23/21.
 */
@Bridge("net.minecraft.world.phys.HitResult$Type")
public interface HitResultType {
    @BridgeField("BLOCK")
    HitResultType block();

    @BridgeField("ENTITY")
    HitResultType entity();

    @BridgeField("MISS")
    HitResultType miss();
}
