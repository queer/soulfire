package gg.amy.soulfire.api.minecraft.registry;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.Placeholder.unimplementedReturn;

/**
 * @author amy
 * @since 5/17/21.
 */
public interface Registry<T> {
    // register(net.minecraft.core.Registry,java.lang.String,java.lang.Object)
    static <E> E register(@Nonnull final Registry<E> registry, @Nonnull final String identifier, @Nonnull final E obj) {
        return unimplementedReturn();
    }
}
