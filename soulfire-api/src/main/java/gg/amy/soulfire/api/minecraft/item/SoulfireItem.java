package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Constructor;
import gg.amy.soulfire.annotations.Shim;
import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.api.events.event.item.ItemInteraction;
import gg.amy.soulfire.api.minecraft.registry.Identifier;
import gg.amy.soulfire.api.minecraft.registry.Registries;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/23/21.
 */
@Shim("net.minecraft.world.item.Item")
public class SoulfireItem implements Item {
    @Constructor("<init>(net.minecraft.world.item.Item$Properties)")
    public SoulfireItem(@Nonnull final ItemProperties properties) {
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull final ItemUseContext ctx) {
        // TODO: Figure out removing code duplication between this and hooks
        final var resourceLocation = Registries.items().getKey(this).get().location();
        return Soulfire.soulfire().bus().fire(new ItemInteraction(
                this, new Identifier(resourceLocation.namespace(), resourceLocation.path()),
                ctx, InteractionResult.pass()
        )).result();
    }
}
