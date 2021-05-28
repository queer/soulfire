package gg.amy.soulfire.events;

import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.api.SoulfireImpl;
import gg.amy.soulfire.api.events.event.block.BlockInteraction;
import gg.amy.soulfire.api.events.event.game.MinecraftInit;
import gg.amy.soulfire.api.events.event.game.MinecraftReady;
import gg.amy.soulfire.api.events.event.inventory.CreativeSearchUpdate;
import gg.amy.soulfire.api.events.event.item.ItemInteraction;
import gg.amy.soulfire.api.events.event.resource.ResourceInit;
import gg.amy.soulfire.api.events.event.resource.ResourceManagerReload;
import gg.amy.soulfire.api.minecraft.block.Block;
import gg.amy.soulfire.api.minecraft.block.BlockPos;
import gg.amy.soulfire.api.minecraft.entity.Player;
import gg.amy.soulfire.api.minecraft.item.InteractionHand;
import gg.amy.soulfire.api.minecraft.item.InteractionResult;
import gg.amy.soulfire.api.minecraft.item.Item;
import gg.amy.soulfire.api.minecraft.item.ItemUseContext;
import gg.amy.soulfire.api.minecraft.registry.Identifier;
import gg.amy.soulfire.api.minecraft.registry.Registries;
import gg.amy.soulfire.api.minecraft.resource.SimpleReloadableResourceManager;
import gg.amy.soulfire.api.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/19/21.
 */
@SuppressWarnings("unused")
public final class Hooks {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger();

    private Hooks() {
    }

    public static void fireInit() {
        ((SoulfireImpl) Soulfire.soulfire()).modLoader().init();
        Soulfire.soulfire().bus().fire(new MinecraftInit());
    }

    public static void fireResourceLoad() {
        Soulfire.soulfire().bus().fire(new ResourceInit());
    }

    public static void fireGameLoaded() {
        Soulfire.soulfire().bus().fire(new MinecraftReady());
    }

    public static void fireResourceManagerReload(@Nonnull final SimpleReloadableResourceManager manager) {
        Soulfire.soulfire().bus().fire(new ResourceManagerReload(manager));
    }

    // TODO: Return to allow modifications?
    public static void fireCreativeSearchUpdate(@Nonnull final String search) {
        Soulfire.soulfire().bus().fire(new CreativeSearchUpdate(search));
    }

    @SuppressWarnings("UnusedReturnValue")
    public static InteractionResult fireItemUse(@Nonnull final Item item, @Nonnull final ItemUseContext ctx) {
        final var resourceLocation = Registries.items().getKey(item).get().location();
        return Soulfire.soulfire().bus().fire(new ItemInteraction(
                item, new Identifier(resourceLocation.namespace(), resourceLocation.path()),
                ctx, InteractionResult.pass()
        )).result();
    }

    @SuppressWarnings("UnusedReturnValue")
    public static InteractionResult fireBlockUse(@Nonnull final Block block, @Nonnull final World world,
                                                 @Nonnull final BlockPos pos, @Nonnull final Player player,
                                                 @Nonnull final InteractionHand hand) {
        final var resourceLocation = Registries.blocks().getKey(block).get().location();
        return Soulfire.soulfire().bus().fire(new BlockInteraction(
                block, new Identifier(resourceLocation.namespace(), resourceLocation.path()),
                world, pos, player, hand, InteractionResult.pass()
        )).result();
    }
}
