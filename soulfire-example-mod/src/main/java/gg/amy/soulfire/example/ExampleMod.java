package gg.amy.soulfire.example;

import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.api.events.event.game.MinecraftInit;
import gg.amy.soulfire.api.events.event.game.MinecraftReady;
import gg.amy.soulfire.api.events.event.item.ItemInteraction;
import gg.amy.soulfire.api.minecraft.block.Block;
import gg.amy.soulfire.api.minecraft.block.BlockProperties;
import gg.amy.soulfire.api.minecraft.block.Material;
import gg.amy.soulfire.api.minecraft.chat.TextComponent;
import gg.amy.soulfire.api.minecraft.item.Item;
import gg.amy.soulfire.api.minecraft.item.ItemCategory;
import gg.amy.soulfire.api.minecraft.item.ItemProperties;
import gg.amy.soulfire.api.minecraft.registry.Identifier;
import gg.amy.soulfire.api.minecraft.registry.Registry;
import gg.amy.soulfire.api.mod.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author amy
 * @since 5/19/21.
 */
@Mod("example")
public class ExampleMod {
    private static final Item TEST_ITEM = Item.create(ItemProperties.create().category(ItemCategory.misc()));
    private static final Block TEST_BLOCK = Block.create(BlockProperties.of(Material.metal()).strength(2.0F));
    private final Logger logger = LogManager.getLogger(getClass());

    public ExampleMod() {
        Soulfire.soulfire().bus().register(MinecraftInit.class, event -> {
            logger.info("############################ Mod init");

            Registry.registerItem(new Identifier("example", "test_item"), TEST_ITEM);
            Registry.registerBlock(new Identifier("example", "test_block"), TEST_BLOCK);

            logger.info("############################ Mod init finished");

            return event;
        });

        Soulfire.soulfire().bus().register(MinecraftReady.class, event -> {
            logger.info("### MINECRAFT READY ###");
            return event;
        });

        Soulfire.soulfire().bus().register(
                ItemInteraction.class,
                identifier -> identifier.matches("example", "test_item"),
                event -> {
                    logger.info("### server? {}", event.ctx().world().server());
                    if(event.ctx().world().server()) {
                        final var player = event.ctx().player();
                        player.sendMessage(TextComponent.of("You just used the test item!"), true);
                        player.sendMessage(TextComponent.of(String.format("You are at (%d, %d, %d) in %s", (int) player.x(), (int) player.y(), (int) player.z(), player.world())), false);
                    }
                    return event;
                });
    }
}
