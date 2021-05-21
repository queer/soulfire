package gg.amy.soulfire.api.events;

import gg.amy.soulfire.api.events.event.ResourceEvent;
import gg.amy.soulfire.api.minecraft.registry.Identifier;

import javax.annotation.Nonnull;
import java.util.function.Function;
import java.util.function.Predicate;

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
     * @param event    The type of event to unlisten on.
     * @param listener The listener to unregister.
     * @param <T>      The type of event.
     */
    <T> void unregister(Class<T> event, Function<T, T> listener);

    /**
     * Registers a listener for the given resource event.
     *
     * @param event     The type of event to listen on.
     * @param predicate A predicate filtering events based on an {@link Identifier}.
     * @param listener  The listener to register.
     * @param <T>       The type of event
     */
    <T extends ResourceEvent> void register(Class<T> event, Predicate<Identifier> predicate, Function<T, T> listener);

    // TODO: How to unregister these?
//    <T extends ResourceEvent> void unregister(Class<T> event, Predicate<Identifier> predicate, Function<T, T> listener);
}
