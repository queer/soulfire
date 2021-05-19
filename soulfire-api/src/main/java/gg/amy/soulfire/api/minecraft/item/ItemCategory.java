package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;

import static gg.amy.soulfire.api.YouFuckedUp.unimplementedReturn;

/**
 * @author amy
 * @since 5/18/21.
 */
@Bridge("net.minecraft.world.item.CreativeModeTab")
public interface ItemCategory {
    @BridgeField("TAB_BUILDING_BLOCKS")
    static ItemCategory buildingBlocks() {
        return unimplementedReturn();
    }

    @BridgeField("TAB_DECORATIONS")
    static ItemCategory decorations() {
        return unimplementedReturn();
    }

    @BridgeField("TAB_REDSTONE")
    static ItemCategory redstone() {
        return unimplementedReturn();
    }

    @BridgeField("TAB_TRANSPORTATION")
    static ItemCategory transportation() {
        return unimplementedReturn();
    }

    @BridgeField("TAB_MISC")
    static ItemCategory misc() {
        return unimplementedReturn();
    }

    @BridgeField("TAB_SEARCH")
    static ItemCategory search() {
        return unimplementedReturn();
    }

    @BridgeField("TAB_FOOD")
    static ItemCategory food() {
        return unimplementedReturn();
    }

    @BridgeField("TAB_TOOLS")
    static ItemCategory tools() {
        return unimplementedReturn();
    }

    @BridgeField("TAB_COMBAT")
    static ItemCategory combat() {
        return unimplementedReturn();
    }

    @BridgeField("TAB_BREWING")
    static ItemCategory brewing() {
        return unimplementedReturn();
    }

    @BridgeField("TAB_MATERIALS")
    static ItemCategory materials() {
        return unimplementedReturn();
    }

    @BridgeField("TAB_HOTBAR")
    static ItemCategory hotbar() {
        return unimplementedReturn();
    }

    @BridgeField("TAB_INVENTORY")
    static ItemCategory inventory() {
        return unimplementedReturn();
    }
}
