package gg.amy.soulfire.api.minecraft.resource;

import gg.amy.soulfire.annotations.Bridge;

/**
 * @author amy
 * @since 5/20/21.
 */
@Bridge("net.minecraft.core.IdMap")
public interface IdMap<T> extends Iterable<T> {
}
