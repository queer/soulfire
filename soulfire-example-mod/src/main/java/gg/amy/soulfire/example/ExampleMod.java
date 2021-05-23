package gg.amy.soulfire.example;

import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.api.events.event.game.MinecraftInit;
import gg.amy.soulfire.api.events.event.item.ItemInteraction;
import gg.amy.soulfire.api.minecraft.block.*;
import gg.amy.soulfire.api.minecraft.chat.TextComponent;
import gg.amy.soulfire.api.minecraft.entity.Player;
import gg.amy.soulfire.api.minecraft.item.*;
import gg.amy.soulfire.api.minecraft.physics.BlockHitResult;
import gg.amy.soulfire.api.minecraft.physics.CollisionContext;
import gg.amy.soulfire.api.minecraft.physics.VoxelShape;
import gg.amy.soulfire.api.minecraft.physics.VoxelShapes;
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
    private static final Item TEST_ITEM = Item.create(ItemProperties.create().category(ItemCategory.misc()));
    @SuppressWarnings("AnonymousInnerClassWithTooManyMethods")
    private static final Block TEST_BLOCK = new SoulfireBlock(BlockProperties.of(Material.metal()).strength(2.0F)) {
        @Override
        public VoxelShape getOcclusionShape(@Nonnull final BlockState state, @Nonnull final BlockGetter getter, @Nonnull final BlockPos pos) {
            return getShape(state, getter, pos, null);
        }

        @Override
        public VoxelShape getCollisionShape(@Nonnull final BlockState state, @Nonnull final BlockGetter getter, @Nonnull final BlockPos pos, @Nonnull final CollisionContext ctx) {
            return getOcclusionShape(state, getter, pos);
        }

        @Override
        @SuppressWarnings("NullableProblems")
        public VoxelShape getShape(@Nonnull final BlockState state, @Nonnull final BlockGetter getter, @Nonnull final BlockPos pos, @Nonnull final CollisionContext ctx) {
            return VoxelShapes.box(0, 0.5, 0, 1, 1, 1);
        }

        @Override
        public InteractionResult use(@Nonnull final BlockState state, @Nonnull final World world, @Nonnull final BlockPos pos, @Nonnull final Player player, @Nonnull final InteractionHand hand, @Nonnull final BlockHitResult result) {
            super.use(state, world, pos, player, hand, result);
            if(world.server()) {
                player.sendMessage(TextComponent.of("You just clicked the example block! Good job! :D"), true);
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
                    final var player = event.ctx().player();
                    player.playSound(Sounds.playerLevelup(), 1F, 1F);
                    if(event.ctx().world().server()) {
                        player.sendMessage(TextComponent.of("You just used the test item!"), true);
                        player.sendMessage(TextComponent.of(String.format(
                                "You are at (%d, %d, %d) in %s",
                                (int) player.x(), (int) player.y(), (int) player.z(), player.world()
                        )), false);
                    }
                    return event;
                });
    }
}
