package gg.amy.soulfire.api.events;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * @author amy
 * @since 5/17/21.
 */
public interface EventBus {
    /**
     * Fire an event, returning the event after it's handled and potentially
     * mutated by event handlers.
     *
     * @param event The event to fire.
     * @param <T>   The type of event.
     * @return The potentially-mutated event.
     */
    @Nonnull
    <T> T fire(@Nonnull T event);

    /**
     * Registers an event handler for a specific event.
     *
     * @param event    The type of event to listen for.
     * @param listener The listener to register.
     * @param <T>      The type of event.
     */
    <T> void register(Class<T> event, Function<T, T> listener);

    /**
     * Unregisters the given handler for a specific event.
     *
     * @param event
     * @param listener
     * @param <T>
     */
    <T> void unregister(Class<T> event, Function<T, T> listener);
}
