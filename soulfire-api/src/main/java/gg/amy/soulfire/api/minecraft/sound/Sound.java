package gg.amy.soulfire.api.minecraft.sound;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceLocation;

/**
 * @author amy
 * @since 5/20/21.
 */
@Bridge("net.minecraft.sounds.SoundEvent")
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface Sound {
    @BridgeMethod("getLocation()")
    ResourceLocation location();
}
