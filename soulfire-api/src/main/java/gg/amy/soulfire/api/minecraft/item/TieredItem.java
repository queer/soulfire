package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.annotations.Final;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/23/21.
 */
@Bridge("net.minecraft.world.item.TieredItem")
public interface TieredItem extends Item {
    @Final
    @BridgeMethod("getTier()")
    default Tier tier() {
        return unimplemented();
    }
}
