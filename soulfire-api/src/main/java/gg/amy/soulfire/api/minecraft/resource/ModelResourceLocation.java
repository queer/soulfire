package gg.amy.soulfire.api.minecraft.resource;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceLocation;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.client.resources.model.ModelResourceLocation")
public interface ModelResourceLocation extends ResourceLocation {
    @BridgeMethod("<init>(java.lang.String,java.lang.String,java.lang.String)")
    static ModelResourceLocation of(@Nonnull final String maybeRegistry, @Nonnull final String namespace, @Nonnull final String path) {
        return unimplemented();
    }

    @BridgeMethod("<init>(java.lang.String)")
    static ModelResourceLocation of(@Nonnull final String unknown) {
        return unimplemented();
    }

    @BridgeMethod("<init>(net.minecraft.resources.ResourceLocation,java.lang.String)")
    static ModelResourceLocation of(@Nonnull final ResourceLocation location, @Nonnull final String id) {
        return unimplemented();
    }
}
