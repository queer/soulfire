package gg.amy.soulfire.test;

import gg.amy.soulfire.api.minecraft.ClientBrandRetriever;
import gg.amy.soulfire.api.minecraft.Minecraft;
import gg.amy.soulfire.api.minecraft.block.Block;
import gg.amy.soulfire.api.minecraft.block.BlockProperties;
import gg.amy.soulfire.api.minecraft.block.Material;
import gg.amy.soulfire.api.minecraft.item.*;
import gg.amy.soulfire.api.minecraft.registry.Registries;
import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceKeys;
import gg.amy.soulfire.api.minecraft.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author amy
 * @since 5/16/21.
 */
@SuppressWarnings("unused")
public final class Events {
    private static final Logger LOGGER = LogManager.getLogger();

    private Events() {
    }

    public static void gameStarted() {
        LOGGER.info("soulfire running on version {} (brand: {})", Minecraft.getInstance().launchedVersion(), ClientBrandRetriever.clientModName());
        LOGGER.info("soulfire created properties: {}", ItemProperties.create());
        LOGGER.info("soulfire found registry resource key: {}", ResourceKeys.items());
        final var item = Registry.register(Registries.items(), new Identifier("test_mod", "test_item"), Item.create(ItemProperties.create().category(ItemCategory.misc())));
        LOGGER.info("soulfire created test item: {}", item);
        final var block = Registry.register(Registries.blocks(), new Identifier("test_mod", "test_block"), Block.create(BlockProperties.of(Material.metal())));
        final var blockItem = Registry.register(Registries.items(), new Identifier("test_mod", "test_block"), BlockItem.create(block, ItemProperties.create().category(ItemCategory.buildingBlocks())));
        LOGGER.info("soulfire created test block: {}, with item: {}", block, blockItem);
    }
}
