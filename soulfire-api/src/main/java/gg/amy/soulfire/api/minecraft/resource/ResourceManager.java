package gg.amy.soulfire.api.minecraft.resource;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.annotations.Interface;
import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceLocation;

import java.util.List;
import java.util.Set;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.server.packs.resources.ResourceManager")
public interface ResourceManager {
    @Interface
    @BridgeMethod("getNamespaces()")
    Set<String> namespaces();

    @Interface
    @BridgeMethod("getResource(net.minecraft.resources.ResourceLocation)")
    Resource resource(ResourceLocation location);

    @Interface
    @BridgeMethod("getResources(net.minecraft.resources.ResourceLocation)")
    List<Resource> resources(ResourceLocation location);
}
