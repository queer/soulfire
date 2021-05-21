package gg.amy.soulfire.api.events.event.resource;

import gg.amy.soulfire.api.minecraft.resource.SimpleReloadableResourceManager;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/19/21.
 */
public record ResourceManagerReload(@Nonnull SimpleReloadableResourceManager repo) {
}
