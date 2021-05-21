package gg.amy.soulfire.api.minecraft.entity;

import gg.amy.soulfire.annotations.*;
import gg.amy.soulfire.api.minecraft.sound.Sound;
import gg.amy.soulfire.api.minecraft.world.ClientWorld;
import gg.amy.soulfire.api.minecraft.world.ServerWorld;
import gg.amy.soulfire.api.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/20/21.
 */
@Retransforming
@TransformAfter({World.class, ClientWorld.class, ServerWorld.class})
@Bridge("net.minecraft.world.entity.Entity")
public interface Entity {
    @DumpASM
    @BridgeMethod("playSound(net.minecraft.sounds.SoundEvent,float,float)")
    void playSound(@Nonnull final Sound sound, float a, float b);

    @BridgeMethod("isPassenger()")
    boolean passenger();

    @BridgeMethod("isVehicle()")
    boolean vehicle();

    @BridgeMethod("isCrouching()")
    boolean crouching();

    @BridgeMethod("isSprinting()")
    boolean sprinting();

    @BridgeMethod("isSwimming()")
    boolean swimming();

    @BridgeMethod("isVisuallySwimming()")
    boolean visuallySwimming();

    @BridgeMethod("isVisuallyCrawling()")
    boolean visuallyCrawling();

    @BridgeMethod("getX()")
    double x();

    @BridgeMethod("getY()")
    double y();

    @BridgeMethod("getZ()")
    double z();

    @BridgeField("level")
    World world();
}
