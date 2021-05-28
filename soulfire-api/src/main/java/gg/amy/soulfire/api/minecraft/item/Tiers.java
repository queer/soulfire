package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/23/21.
 */
@Bridge("net.minecraft.world.item.Tiers")
public interface Tiers extends Tier {
    @BridgeField("WOOD")
    static Tiers wood() {
        return unimplemented();
    }

    @BridgeField("STONE")
    static Tiers stone() {
        return unimplemented();
    }

    @BridgeField("IRON")
    static Tiers iron() {
        return unimplemented();
    }

    @BridgeField("DIAMOND")
    static Tiers diamond() {
        return unimplemented();
    }

    @BridgeField("GOLD")
    static Tiers gold() {
        return unimplemented();
    }

    @BridgeField("NETHERITE")
    static Tiers netherite() {
        return unimplemented();
    }
}
