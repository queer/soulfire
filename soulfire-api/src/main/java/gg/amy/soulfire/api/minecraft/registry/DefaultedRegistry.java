package gg.amy.soulfire.api.minecraft.registry;

import gg.amy.soulfire.annotations.Bridge;

/**
 * @author amy
 * @since 5/18/21.
 */
@Bridge("net.minecraft.core.DefaultedRegistry")
public interface DefaultedRegistry<T> extends Registry<T> {
}
