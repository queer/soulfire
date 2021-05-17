package gg.amy.soulfire.events;

import gg.amy.soulfire.api.events.EventBus;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author amy
 * @since 5/17/21.
 */
public class SoulfireEventBus implements EventBus {
    private final Map<Class<?>, Set<Function<?, ?>>> listeners = new ConcurrentHashMap<>();

    @Nonnull
    @Override
    public <T> T fire(@Nonnull T event) {
        for(final var handler : listeners.get(event.getClass())) {
            //noinspection unchecked
            event = ((Function<T, T>) handler).apply(event);
        }
        return event;
    }

    @Override
    public <T> void register(final Class<T> event, final Function<T, T> listener) {
        listeners.computeIfAbsent(event, __ -> ConcurrentHashMap.newKeySet()).add(listener);
    }

    @Override
    public <T> void unregister(final Class<T> event, final Function<T, T> listener) {
        listeners.computeIfAbsent(event, __ -> ConcurrentHashMap.newKeySet()).remove(listener);
    }
}
