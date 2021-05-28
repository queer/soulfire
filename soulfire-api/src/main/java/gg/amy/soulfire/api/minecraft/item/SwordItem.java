package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.annotations.Final;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/23/21.
 */
@Bridge("net.minecraft.world.item.SwordItem")
public interface SwordItem extends TieredItem {
    @Final
    @BridgeMethod("getDamage()")
    default float damage() {
        return unimplemented();
    }
}
