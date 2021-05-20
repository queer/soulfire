package gg.amy.soulfire.api.minecraft.resource;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.server.packs.resources.SimpleReloadableResourceManager")
public interface SimpleReloadableResourceManager extends ReloadableResourceManager {
    @BridgeMethod("add(net.minecraft.server.packs.PackResources)")
    void add(@Nonnull final ResourcePack pack);
}
