package gg.amy.soulfire.api.minecraft.registry;

import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/18/21.
 */
public record Identifier(@Nonnull String namespace, @Nonnull String id) {
    public boolean matches(@Nonnull final String namespace, @Nonnull final String id) {
        return this.namespace.equals(namespace) && this.id.equals(id);
    }

    @Override
    public String toString() {
        return String.format("%s:%s", namespace, id);
    }

    public static Identifier of(@Nonnull final ResourceLocation location) {
        return new Identifier(location.namespace(), location.path());
    }
}
