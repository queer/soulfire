package gg.amy.soulfire.api.minecraft.entity;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.annotations.TransformAfter;
import gg.amy.soulfire.api.minecraft.chat.ChatComponent;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/20/21.
 */
@TransformAfter(LivingEntity.class)
@Bridge("net.minecraft.world.entity.player.Player")
public interface Player extends LivingEntity {
    @BridgeMethod("displayClientMessage(net.minecraft.network.chat.Component,boolean)")
    void sendMessage(@Nonnull ChatComponent component, boolean aboveHotbar);

    @BridgeMethod("onSoulSpeedBlock()")
    boolean onSoulBlock();
}
