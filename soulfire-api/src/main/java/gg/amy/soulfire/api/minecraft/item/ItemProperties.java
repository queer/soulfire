package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/17/21.
 */
@Bridge("net.minecraft.world.item.Item$Properties")
public interface ItemProperties {
    @Nonnull
    @BridgeMethod("<init>()")
    static ItemProperties create() {
        return unimplemented();
    }

    @Nonnull
    @BridgeMethod("stacksTo(int)")
    ItemProperties stacksTo(@Nonnegative int count);

    @Nonnull
    @BridgeMethod("defaultDurability(int)")
    ItemProperties defaultDurability(@Nonnegative int durability);

    @Nonnull
    @BridgeMethod("durability(int)")
    ItemProperties durability(@Nonnegative int durability);

    @Nonnull
    @BridgeMethod("fireResistant()")
    ItemProperties fireResistant();

    @Nonnull
    @BridgeMethod("tab(net.minecraft.world.item.CreativeModeTab)")
    ItemProperties category(@Nonnull ItemCategory category);
}
