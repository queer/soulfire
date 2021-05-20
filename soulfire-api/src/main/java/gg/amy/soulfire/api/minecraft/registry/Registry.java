package gg.amy.soulfire.api.minecraft.registry;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.block.Block;
import gg.amy.soulfire.api.minecraft.item.*;
import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceKey;
import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceLocation;
import gg.amy.soulfire.api.minecraft.resource.IdMap;

import javax.annotation.Nonnull;
import java.util.Optional;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/17/21.
 */
@SuppressWarnings("UnusedReturnValue")
@Bridge("net.minecraft.core.Registry")
public interface Registry<T> extends IdMap<T> {
    static <E> E register(@Nonnull final Registry<E> registry, @Nonnull final Identifier identifier, @Nonnull final E obj) {
        return register(registry, String.format("%s:%s", identifier.namespace(), identifier.id()), obj);
    }

    @BridgeMethod("register(net.minecraft.core.Registry,java.lang.String,java.lang.Object)")
    static <E> E register(@Nonnull final Registry<E> registry, @Nonnull final String identifier, @Nonnull final E obj) {
        return unimplemented();
    }

    @BridgeMethod("register(net.minecraft.core.Registry,net.minecraft.resources.ResourceLocation,java.lang.Object)")
    static <E> E register(@Nonnull final Registry<E> registry, @Nonnull final ResourceLocation identifier, @Nonnull final E obj) {
        return unimplemented();
    }

    @Nonnull
    static Item registerItem(@Nonnull final Identifier identifier, @Nonnull final Item item) {
        try {
            if(item instanceof BlockItem blockItem) {
                Item.byBlock().put(blockItem.block(), item);
            }
            return register(Registries.items(), identifier, item);
        } catch(final Exception e) {
            e.printStackTrace();
            return item;
        }
    }

    @Nonnull
    static Block registerBlock(@Nonnull final Identifier identifier, @Nonnull final Block block) {
        return registerBlock(identifier, block, ItemProperties.create().category(ItemCategory.buildingBlocks()));
    }

    @Nonnull
    static Block registerBlock(@Nonnull final Identifier identifier, @Nonnull final Block block,
                               @Nonnull final ItemProperties properties) {
        try {
            final var out = register(Registries.blocks(), identifier, block);
            registerItem(identifier, BlockItem.create(block, properties));
            return out;
        } catch(final Exception e) {
            e.printStackTrace();
            return block;
        }
    }

    @BridgeMethod("get(net.minecraft.resources.ResourceKey)")
    T get(ResourceKey key);

    default T get(@Nonnull final Identifier identifier) {
        return get(ResourceKey.of(registryKey(), ResourceLocation.of(identifier.namespace(), identifier.id())));
    }

    @Nonnull
    @BridgeMethod("getResourceKey(java.lang.Object)")
    Optional<ResourceKey> getKey(@Nonnull final T obj);

    @BridgeField("key")
    ResourceKey registryKey();
}
