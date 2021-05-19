package gg.amy.soulfire.api.minecraft.registry;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceLocation;

/**
 * @author amy
 * @since 5/18/21.
 */
@Bridge("net.minecraft.core.DefaultedRegistry")
public interface DefaultedRegistry<T> extends Registry<T> {
    @BridgeMethod("getDefaultKey()")
    ResourceLocation defaultKey();

    @BridgeMethod("get(net.minecraft.resources.ResourceLocation)")
    T get(ResourceLocation location);


}
