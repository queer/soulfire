package gg.amy.soulfire.events;

import gg.amy.soulfire.api.events.EventBus;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.LinkedHashMap;
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
        for(final var handler : listeners.computeIfAbsent(event.getClass(), this::setCreator)) {
            //noinspection unchecked
            event = ((Function<T, T>) handler).apply(event);
        }
        return event;
    }

    @Override
    public <T> void register(final Class<T> event, final Function<T, T> listener) {
        listeners.computeIfAbsent(event, this::setCreator).add(listener);
    }

    @Override
    public <T> void unregister(final Class<T> event, final Function<T, T> listener) {
        listeners.computeIfAbsent(event, this::setCreator).remove(listener);
    }

    @Nonnull
    private Set<Function<?, ?>> setCreator(@Nonnull final Class<?> _cls) {
        // TODO: AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        return Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    }
}
