package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/20/21.
 */
@Bridge("net.minecraft.world.item.ItemStack")
public interface ItemStack {
    @BridgeMethod("<init>(net.minecraft.world.level.ItemLike)")
    static ItemStack create(@Nonnull final Item item) {
        return unimplemented();
    }

    @BridgeMethod("<init>(net.minecraft.world.level.ItemLike,int)")
    static ItemStack create(@Nonnull final Item item, @Nonnegative final int size) {
        return unimplemented();
    }
}
