package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.Setter;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;
import static gg.amy.soulfire.api.YouFuckedUp.unimplementedVoid;

/**
 * @author amy
 * @since 5/18/21.
 */
@Bridge("net.minecraft.world.item.CreativeModeTab")
public interface ItemCategory {
//    @BridgeField("TABS")
//    static ItemCategory[] tabs() {
//        return unimplemented();
//    }

//    @Setter
//    @BridgeField("TABS")
//    static void tabs(@Nonnull final ItemCategory[] categories) {
//        unimplementedVoid();
//    }

    @BridgeField("TAB_BUILDING_BLOCKS")
    static ItemCategory buildingBlocks() {
        return unimplemented();
    }

    @BridgeField("TAB_DECORATIONS")
    static ItemCategory decorations() {
        return unimplemented();
    }

    @BridgeField("TAB_REDSTONE")
    static ItemCategory redstone() {
        return unimplemented();
    }

    @BridgeField("TAB_TRANSPORTATION")
    static ItemCategory transportation() {
        return unimplemented();
    }

    @BridgeField("TAB_MISC")
    static ItemCategory misc() {
        return unimplemented();
    }

    @BridgeField("TAB_SEARCH")
    static ItemCategory search() {
        return unimplemented();
    }

    @BridgeField("TAB_FOOD")
    static ItemCategory food() {
        return unimplemented();
    }

    @BridgeField("TAB_TOOLS")
    static ItemCategory tools() {
        return unimplemented();
    }

    @BridgeField("TAB_COMBAT")
    static ItemCategory combat() {
        return unimplemented();
    }

    @BridgeField("TAB_BREWING")
    static ItemCategory brewing() {
        return unimplemented();
    }

    @BridgeField("TAB_MATERIALS")
    static ItemCategory materials() {
        return unimplemented();
    }

    @BridgeField("TAB_HOTBAR")
    static ItemCategory hotbar() {
        return unimplemented();
    }

    @BridgeField("TAB_INVENTORY")
    static ItemCategory inventory() {
        return unimplemented();
    }
}
