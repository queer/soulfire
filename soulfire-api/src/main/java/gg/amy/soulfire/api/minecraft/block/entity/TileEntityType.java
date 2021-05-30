package gg.amy.soulfire.api.minecraft.block.entity;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.DumpASM;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/28/21.
 */
@Bridge("net.minecraft.world.level.block.entity.BlockEntityType")
public interface TileEntityType<T extends TileEntity> {
    @BridgeField("FURNACE")
    static TileEntityType<FurnaceTileEntity> furnace() {
        return unimplemented();
    }

    @DumpASM
    @BridgeField("CHEST")
    static TileEntityType<ChestTileEntity> chest() {
        return unimplemented();
    }

    @BridgeField("TRAPPED_CHEST")
    static TileEntityType trappedChest() {
        return unimplemented();
    }

    @BridgeField("ENDER_CHEST")
    static TileEntityType enderChest() {
        return unimplemented();
    }

    @BridgeField("JUKEBOX")
    static TileEntityType jukebox() {
        return unimplemented();
    }

    @BridgeField("DISPENSER")
    static TileEntityType dispenser() {
        return unimplemented();
    }

    @BridgeField("DROPPER")
    static TileEntityType dropper() {
        return unimplemented();
    }

    @BridgeField("SIGN")
    static TileEntityType sign() {
        return unimplemented();
    }

    @BridgeField("MOB_SPAWNER")
    static TileEntityType mobSpawner() {
        return unimplemented();
    }

    @BridgeField("PISTON")
    static TileEntityType piston() {
        return unimplemented();
    }

    @BridgeField("BREWING_STAND")
    static TileEntityType brewingStand() {
        return unimplemented();
    }

    @BridgeField("ENCHANTING_TABLE")
    static TileEntityType enchantingTable() {
        return unimplemented();
    }

    @BridgeField("END_PORTAL")
    static TileEntityType endPortal() {
        return unimplemented();
    }

    @BridgeField("BEACON")
    static TileEntityType beacon() {
        return unimplemented();
    }

    @BridgeField("SKULL")
    static TileEntityType skull() {
        return unimplemented();
    }

    @BridgeField("DAYLIGHT_DETECTOR")
    static TileEntityType daylightDetector() {
        return unimplemented();
    }

    @BridgeField("HOPPER")
    static TileEntityType hopper() {
        return unimplemented();
    }

    @BridgeField("COMPARATOR")
    static TileEntityType comparator() {
        return unimplemented();
    }

    @BridgeField("BANNER")
    static TileEntityType banner() {
        return unimplemented();
    }

    @BridgeField("STRUCTURE_BLOCK")
    static TileEntityType structureBlock() {
        return unimplemented();
    }

    @BridgeField("END_GATEWAY")
    static TileEntityType endGateway() {
        return unimplemented();
    }

    @BridgeField("COMMAND_BLOCK")
    static TileEntityType commandBlock() {
        return unimplemented();
    }

    @BridgeField("SHULKER_BOX")
    static TileEntityType shulkerBox() {
        return unimplemented();
    }

    @BridgeField("BED")
    static TileEntityType bed() {
        return unimplemented();
    }

    @BridgeField("CONDUIT")
    static TileEntityType conduit() {
        return unimplemented();
    }

    @BridgeField("BARREL")
    static TileEntityType barrel() {
        return unimplemented();
    }

    @BridgeField("SMOKER")
    static TileEntityType smoker() {
        return unimplemented();
    }

    @BridgeField("BLAST_FURNACE")
    static TileEntityType blastFurnace() {
        return unimplemented();
    }

    @BridgeField("LECTERN")
    static TileEntityType lectern() {
        return unimplemented();
    }

    @BridgeField("BELL")
    static TileEntityType bell() {
        return unimplemented();
    }

    @BridgeField("JIGSAW")
    static TileEntityType jigsaw() {
        return unimplemented();
    }

    @BridgeField("CAMPFIRE")
    static TileEntityType campfire() {
        return unimplemented();
    }

    @BridgeField("BEEHIVE")
    static TileEntityType beehive() {
        return unimplemented();
    }
}
