package gg.amy.soulfire.api.minecraft.resource;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.registry.Registries;

import javax.annotation.Nonnull;
import java.io.InputStream;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.server.packs.AbstractPackResources")
public interface AbstractResourcePack extends ResourcePack {
    // TODO: Handle constructors...

    @Nonnull
    @BridgeMethod("getRootResource(java.lang.String)")
    InputStream rootResource(@Nonnull final String path);

    @BridgeMethod("hasResource(java.lang.String)")
    boolean hasResource(@Nonnull final String path);
}
