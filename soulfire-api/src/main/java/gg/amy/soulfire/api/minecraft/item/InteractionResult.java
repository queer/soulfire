package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/20/21.
 */
@Bridge("net.minecraft.world.InteractionResult")
public interface InteractionResult {
    @BridgeField("SUCCESS")
    static InteractionResult success() {
        return unimplemented();
    }

    @BridgeField("CONSUME")
    static InteractionResult consume() {
        return unimplemented();
    }

    @BridgeField("PASS")
    static InteractionResult pass() {
        return unimplemented();
    }

    @BridgeField("FAIL")
    static InteractionResult fail() {
        return unimplemented();
    }
}
