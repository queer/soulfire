package gg.amy.soulfire.events;

import gg.amy.soulfire.api.events.EventBus;
import gg.amy.soulfire.api.events.event.ResourceEvent;
import gg.amy.soulfire.api.minecraft.registry.Identifier;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author amy
 * @since 5/17/21.
 */
public class SoulfireEventBus implements EventBus {
    private final Map<Class<?>, List<Function<?, ?>>> listeners = new ConcurrentHashMap<>();

    @Nonnull
    @Override
    public <T> T fire(@Nonnull T event) {
        for(final var handler : listeners.computeIfAbsent(event.getClass(), __ -> new CopyOnWriteArrayList<>())) {
            //noinspection unchecked
            event = ((Function<T, T>) handler).apply(event);
        }
        return event;
    }

    @Override
    public <T> void register(final Class<T> event, final Function<T, T> listener) {
        listeners.computeIfAbsent(event, __ -> new CopyOnWriteArrayList<>()).add(listener);
    }

    @Override
    public <T> void unregister(final Class<T> event, final Function<T, T> listener) {
        listeners.computeIfAbsent(event, __ -> new CopyOnWriteArrayList<>()).remove(listener);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ResourceEvent> void register(final Class<T> event, final Predicate<Identifier> predicate, final Function<T, T> listener) {
        listeners.computeIfAbsent(event, __ -> new CopyOnWriteArrayList<>()).add(fired -> {
            if(predicate.test(((ResourceEvent) fired).identifier())) {
                return listener.apply((T) fired);
            } else {
                return fired;
            }
        });
    }
}
