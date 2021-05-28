package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.annotations.Final;
import gg.amy.soulfire.api.minecraft.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/17/21.
 */
@Bridge("net.minecraft.world.item.Item")
public interface Item {
    @Nonnull
    @BridgeMethod("<init>(net.minecraft.world.item.Item$Properties)")
    static Item create(@Nonnull final ItemProperties properties) {
        return unimplemented();
    }

    @Nonnull
    @BridgeField("BY_BLOCK")
    static Map<Block, Item> byBlock() {
        return unimplemented();
    }

    @Final
    @BridgeMethod("getMaxStackSize()")
    default int maxStackSize() {
        return unimplemented();
    }

    @Final
    @BridgeMethod("getMaxDamage()")
    default int maxDamage() {
        return unimplemented();
    }

    @Nonnull
    @BridgeMethod("useOn(net.minecraft.world.item.context.UseOnContext)")
    InteractionResult useOn(@Nonnull ItemUseContext ctx);

    @Final
    @BridgeMethod("hasCraftingRemainingItem()")
    default boolean hasCraftingRemainingItem() {
        return unimplemented();
    }

    @Final
    @Nullable
    @BridgeMethod("getCraftingRemainingItem()")
    default Item craftingRemainingItem() {
        return unimplemented();
    }
}
