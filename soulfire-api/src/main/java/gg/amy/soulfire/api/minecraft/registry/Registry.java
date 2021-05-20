package gg.amy.soulfire.api.minecraft.registry;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.item.Identifier;
import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceKey;
import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Optional;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/17/21.
 */
@Bridge("net.minecraft.core.Registry")
public interface Registry<T> {
    static <E> E register(@Nonnull final Registry<E> registry, @Nonnull final Identifier identifier, @Nonnull final E obj) {
        return register(registry, String.format("%s:%s", identifier.namespace(), identifier.id()), obj);
    }

    @BridgeMethod("register(net.minecraft.core.Registry,java.lang.String,java.lang.Object)")
    static <E> E register(@Nonnull final Registry<E> registry, @Nonnull final String identifier, @Nonnull final E obj) {
        return unimplemented();
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
