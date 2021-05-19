package gg.amy.soulfire.api.minecraft.block;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/18/21.
 */
@SuppressWarnings("unused")
@Bridge("net.minecraft.world.level.material.Material")
public interface Material {
    @BridgeField("AIR")
    static Material air() {
        return unimplemented();
    }

    @BridgeField("STRUCTURAL_AIR")
    static Material structuralAir() {
        return unimplemented();
    }

    @BridgeField("PORTAL")
    static Material portal() {
        return unimplemented();
    }

    @BridgeField("CLOTH_DECORATION")
    static Material clothDecoration() {
        return unimplemented();
    }

    @BridgeField("PLANT")
    static Material plant() {
        return unimplemented();
    }

    @BridgeField("WATER_PLANT")
    static Material waterPlant() {
        return unimplemented();
    }

    @BridgeField("REPLACEABLE_PLANT")
    static Material replaceablePlant() {
        return unimplemented();
    }

    @BridgeField("REPLACEABLE_FIREPROOF_PLANT")
    static Material replaceableFireproofPlant() {
        return unimplemented();
    }

    @BridgeField("REPLACEABLE_WATER_PLANT")
    static Material replaceableWaterPlant() {
        return unimplemented();
    }

    @BridgeField("WATER")
    static Material water() {
        return unimplemented();
    }

    @BridgeField("BUBBLE_COLUMN")
    static Material bubbleColumn() {
        return unimplemented();
    }

    @BridgeField("LAVA")
    static Material lava() {
        return unimplemented();
    }

    @BridgeField("TOP_SNOW")
    static Material topSnow() {
        return unimplemented();
    }

    @BridgeField("FIRE")
    static Material fire() {
        return unimplemented();
    }

    @BridgeField("DECORATION")
    static Material decoration() {
        return unimplemented();
    }

    @BridgeField("WEB")
    static Material web() {
        return unimplemented();
    }

    @BridgeField("BUILDABLE_GLASS")
    static Material buildableGlass() {
        return unimplemented();
    }

    @BridgeField("CLAY")
    static Material clay() {
        return unimplemented();
    }

    @BridgeField("DIRT")
    static Material dirt() {
        return unimplemented();
    }

    @BridgeField("GRASS")
    static Material grass() {
        return unimplemented();
    }

    @BridgeField("ICE_SOLID")
    static Material iceSolid() {
        return unimplemented();
    }

    @BridgeField("SAND")
    static Material sand() {
        return unimplemented();
    }

    @BridgeField("SPONGE")
    static Material sponge() {
        return unimplemented();
    }

    @BridgeField("SHULKER_SHELL")
    static Material shulkerShell() {
        return unimplemented();
    }

    @BridgeField("WOOD")
    static Material wood() {
        return unimplemented();
    }

    @BridgeField("NETHER_WOOD")
    static Material netherWood() {
        return unimplemented();
    }

    @BridgeField("BAMBOO_SAPLING")
    static Material bambooSapling() {
        return unimplemented();
    }

    @BridgeField("BAMBOO")
    static Material bamboo() {
        return unimplemented();
    }

    @BridgeField("WOOL")
    static Material wool() {
        return unimplemented();
    }

    @BridgeField("EXPLOSIVE")
    static Material explosive() {
        return unimplemented();
    }

    @BridgeField("LEAVES")
    static Material leaves() {
        return unimplemented();
    }

    @BridgeField("GLASS")
    static Material glass() {
        return unimplemented();
    }

    @BridgeField("ICE")
    static Material ice() {
        return unimplemented();
    }

    @BridgeField("CACTUS")
    static Material cactus() {
        return unimplemented();
    }

    @BridgeField("STONE")
    static Material stone() {
        return unimplemented();
    }

    @BridgeField("METAL")
    static Material metal() {
        return unimplemented();
    }

    @BridgeField("SNOW")
    static Material snow() {
        return unimplemented();
    }

    @BridgeField("HEAVY_METAL")
    static Material heavyMetal() {
        return unimplemented();
    }

    @BridgeField("BARRIER")
    static Material barrier() {
        return unimplemented();
    }

    @BridgeField("PISTON")
    static Material piston() {
        return unimplemented();
    }

    @BridgeField("CORAL")
    static Material coral() {
        return unimplemented();
    }

    @BridgeField("VEGETABLE")
    static Material vegetable() {
        return unimplemented();
    }

    @BridgeField("EGG")
    static Material egg() {
        return unimplemented();
    }

    @BridgeField("CAKE")
    static Material cake() {
        return unimplemented();
    }
}
