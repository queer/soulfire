package gg.amy.soulfire.api.minecraft.entity;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.TransformAfter;

/**
 * @author amy
 * @since 5/20/21.
 */
@TransformAfter(Player.class)
@Bridge("net.minecraft.client.player.AbstractClientPlayer")
public interface AbstractClientPlayer extends Player {
}
