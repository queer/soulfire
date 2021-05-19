package gg.amy.soulfire.api.minecraft.registry;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.item.Identifier;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplementedReturn;

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
        return unimplementedReturn();
    }
}
