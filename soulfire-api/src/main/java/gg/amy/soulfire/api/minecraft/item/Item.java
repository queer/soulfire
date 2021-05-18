package gg.amy.soulfire.api.minecraft.item;

/**
 * @author amy
 * @since 5/17/21.
 */
//@Bridge("net.minecraft.world.item.Item")
public interface Item {
    //    @BridgeMethod("getMaxStackSize()")
    int maxStackSize();

    //    @BridgeMethod("getMaxDamage()")
    int maxDamage();
}
