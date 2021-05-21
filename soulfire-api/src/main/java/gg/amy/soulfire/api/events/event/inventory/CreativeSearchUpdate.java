package gg.amy.soulfire.api.events.event.inventory;

import javax.annotation.Nonnull;

/**
 * An event fired when the creative search bar updates. You cannot mutate the
 * search box's contents with this event.
 *
 * @author amy
 * @since 5/20/21.
 */
public record CreativeSearchUpdate(@Nonnull String search) {
}
