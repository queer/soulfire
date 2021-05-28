package gg.amy.soulfire.example;

import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.api.events.event.game.MinecraftInit;
import gg.amy.soulfire.api.events.event.item.ItemInteraction;
import gg.amy.soulfire.api.minecraft.block.*;
import gg.amy.soulfire.api.minecraft.chat.TextComponent;
import gg.amy.soulfire.api.minecraft.entity.Player;
import gg.amy.soulfire.api.minecraft.item.*;
import gg.amy.soulfire.api.minecraft.physics.BlockHitResult;
import gg.amy.soulfire.api.minecraft.registry.Identifier;
import gg.amy.soulfire.api.minecraft.registry.Registry;
import gg.amy.soulfire.api.minecraft.sound.Sounds;
import gg.amy.soulfire.api.minecraft.world.World;
import gg.amy.soulfire.api.mod.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/19/21.
 */
@Mod("example")
public class ExampleMod {
    private static final Item TEST_ITEM = new SoulfireSwordItem(Tiers.netherite(), 1, 10F, ItemProperties.create().category(ItemCategory.misc())) {
        @Nonnull
        @Override
        public InteractionResult useOn(@Nonnull final ItemUseContext ctx) {
            // Handle click via override
            super.useOn(ctx);
            if(ctx.world().server()) {
                ctx.player().sendMessage("You just used the example item!", true);
            }
            return InteractionResult.pass();
        }
    };
    private static final Block TEST_BLOCK = new SoulfireBlock(BlockProperties.of(Material.metal()).strength(2.0F)) {
        @Override
        public InteractionResult use(@Nonnull final BlockState state, @Nonnull final World world,
                                     @Nonnull final BlockPos pos, @Nonnull final Player player,
                                     @Nonnull final InteractionHand hand, @Nonnull final BlockHitResult result) {
            super.use(state, world, pos, player, hand, result);
            if(world.server()) {
                player.sendMessage("You just clicked the example block! Good job! :D", true);
            }
            return InteractionResult.pass();
        }
    };
    private final Logger logger = LogManager.getLogger(getClass());

    public ExampleMod() {
        Soulfire.soulfire().bus().register(MinecraftInit.class, event -> {
            logger.info("############################ Mod init");

            Registry.registerItem(new Identifier("example", "test_item"), TEST_ITEM);
            Registry.registerBlock(new Identifier("example", "test_block"), TEST_BLOCK);

            logger.info("############################ Mod init finished");

            return event;
        });

        Soulfire.soulfire().bus().register(
                ItemInteraction.class,
                identifier -> identifier.matches("example", "test_item"),
                event -> {
                    // Or handle click via event
                    final var player = event.ctx().player();
                    player.playSound(Sounds.playerLevelup(), 1F, 1F);
                    if(event.ctx().world().server()) {
                        player.sendMessage(TextComponent.of(String.format(
                                "You are at (%d, %d, %d) in %s",
                                (int) player.x(), (int) player.y(), (int) player.z(), player.world()
                        )), false);
                    }
                    return event;
                });
    }
}
