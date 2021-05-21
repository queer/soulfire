package gg.amy.soulfire.api.events.event.resource;

import gg.amy.soulfire.api.minecraft.resource.SimpleReloadableResourceManager;

import javax.annotation.Nonnull;

/**
 * Fired when a resource manager reloads. Contains a reference to the reloaded
 * resource manager.
 *
 * @author amy
 * @since 5/19/21.
 */
public record ResourceManagerReload(@Nonnull SimpleReloadableResourceManager repo) {
}
