package gg.amy.soulfire.example;

import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.api.events.event.block.BlockInteraction;
import gg.amy.soulfire.api.events.event.block.BlockPlace;
import gg.amy.soulfire.api.events.event.game.MinecraftInit;
import gg.amy.soulfire.api.events.event.item.ItemInteraction;
import gg.amy.soulfire.api.minecraft.block.*;
import gg.amy.soulfire.api.minecraft.block.entity.ChestTileEntity;
import gg.amy.soulfire.api.minecraft.block.entity.SoulfireTileEntity;
import gg.amy.soulfire.api.minecraft.block.entity.TileEntityType;
import gg.amy.soulfire.api.minecraft.chat.TextComponent;
import gg.amy.soulfire.api.minecraft.entity.ItemEntity;
import gg.amy.soulfire.api.minecraft.entity.Player;
import gg.amy.soulfire.api.minecraft.item.*;
import gg.amy.soulfire.api.minecraft.physics.BlockHitResult;
import gg.amy.soulfire.api.minecraft.registry.Identifier;
import gg.amy.soulfire.api.minecraft.registry.Registries;
import gg.amy.soulfire.api.minecraft.registry.Registry;
import gg.amy.soulfire.api.minecraft.sound.SoundSource;
import gg.amy.soulfire.api.minecraft.sound.Sounds;
import gg.amy.soulfire.api.minecraft.world.World;
import gg.amy.soulfire.api.mod.Mod;
import gg.amy.soulfire.api.thesaurus.Thesaurus.ThesaurusCategory;
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
//
//        @Override
//        public void onPlace(@Nonnull final BlockState state, @Nonnull final World world, @Nonnull final BlockPos pos, @Nonnull final BlockState state2, final boolean a) {
//            final var entity = ChestTileEntity.create();
//            entity.setWorldAndPos(world, pos);
//            world.addTileEntity(entity);
//        }
    };
    private final Logger logger = LogManager.getLogger(getClass());

    public ExampleMod() {
        Soulfire.soulfire().bus().register(MinecraftInit.class, event -> {
            logger.info("############################ Mod init");

            Registry.registerItem(new Identifier("example", "test_item"), TEST_ITEM);
            Registry.registerBlock(new Identifier("example", "test_block"), TEST_BLOCK);
//            Registry.register(Registries.blockEntityTypes(), new Identifier("example", "test_tile_entity"), TileEntityTypeBuilder.of(ExampleTileEntity::new, new Block[0]).build(null));

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

        // Cut logs in half when on top of a saw
        Soulfire.soulfire().bus().register(BlockPlace.class, event -> {
            if(Soulfire.soulfire().thesaurus().is(ThesaurusCategory.LOG, event.block())) {
                final var world = event.world();
                final var pos = event.pos();
                if(world.blockState(pos.below()).block().equals(Blocks.stonecutter())) {
                    // TODO: Invent a task scheduler. This shouldn't be run on the exact same tick, but rather several ticks later
                    world.removeBlock(pos, true);
                    world.playSound(null, pos.x(), pos.y(), pos.z(), Sounds.parrotImitateEndermite(), SoundSource.blocks(), 10.0F, 10.0F);
                    // TODO: Cleaner...
                    world.chunkAt(pos).addEntity(ItemEntity.create(world, pos.x() + 1, pos.y() + 1, pos.z() + 1, ItemStack.create(Registries.items().get(new Identifier("minecraft", "oak_planks")), 2)));
                }
            }
            return event;
        });
    }

    public static class ExampleTileEntity extends SoulfireTileEntity<ChestTileEntity> {
        public ExampleTileEntity() {
            super(TileEntityType.chest());
        }
    }
}
