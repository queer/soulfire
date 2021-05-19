package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplementedReturn;

/**
 * @author amy
 * @since 5/17/21.
 */
@Bridge("net.minecraft.world.item.Item")
public interface Item {
    @Nonnull
    static Item create(@Nonnull final ItemProperties properties) {
        return unimplementedReturn();
    }

    @BridgeMethod("getMaxStackSize()")
    int maxStackSize();

    @BridgeMethod("getMaxDamage()")
    int maxDamage();
}
