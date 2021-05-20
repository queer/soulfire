package gg.amy.soulfire.api.minecraft.resource;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.resource.ResourcePackInfo.PackInfoConstructor;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * @author amy
 * @since 5/19/21.
 */
@FunctionalInterface
@Bridge("net.minecraft.server.packs.repository.RepositorySource")
public interface RepositorySource {
    @BridgeMethod("loadPacks(java.util.function.Consumer,net.minecraft.server.packs.repository.Pack$PackConstructor)")
    void loadPacks(@Nonnull final Consumer<ResourcePackInfo> consumer, @Nonnull final PackInfoConstructor pif);
}
