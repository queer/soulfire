package gg.amy.soulfire.example;

import gg.amy.soulfire.api.minecraft.block.Block;
import gg.amy.soulfire.api.minecraft.block.BlockProperties;
import gg.amy.soulfire.api.minecraft.block.Material;
import gg.amy.soulfire.api.minecraft.item.*;
import gg.amy.soulfire.api.minecraft.registry.Registries;
import gg.amy.soulfire.api.minecraft.registry.Registry;
import gg.amy.soulfire.api.mod.Mod;
import gg.amy.soulfire.api.mod.lifecycle.Init;

/**
 * @author amy
 * @since 5/19/21.
 */
@Mod("example")
public class ExampleMod {
    private final Item testItem = Item.create(ItemProperties.create().category(ItemCategory.misc()));
    private final Block block = Block.create(BlockProperties.of(Material.metal()));
    private final BlockItem blockItem = BlockItem.create(block, ItemProperties.create().category(ItemCategory.buildingBlocks()));

    @Init
    public void init() {
        Registry.register(Registries.items(), new Identifier("example", "test_item"), testItem);

        // TODO: WHY DOES THIS NOT SHOW UP IN THE CREATIVE VIEW!?
        Registry.register(Registries.blocks(), new Identifier("example", "test_block"), block);
        Registry.register(Registries.items(), new Identifier("example", "test_block"), blockItem);
    }
}
