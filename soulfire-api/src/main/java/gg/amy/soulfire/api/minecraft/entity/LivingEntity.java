package gg.amy.soulfire.api.minecraft.entity;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.TransformAfter;

/**
 * @author amy
 * @since 5/20/21.
 */
@TransformAfter(Entity.class)
@Bridge("net.minecraft.world.entity.LivingEntity")
public interface LivingEntity extends Entity {
}
