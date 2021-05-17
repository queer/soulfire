package gg.amy.soulfire.events;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author amy
 * @since 5/17/21.
 */
public class SoulfireEventBusTest {
    @Test
    public void testThatItWorks() {
        final var bus = new SoulfireEventBus();
        final Function<String, String> handler = event -> event + " handled";
        bus.register(String.class, handler);
        assertEquals("test handled", bus.fire("test"));
        bus.unregister(String.class, handler);
        assertEquals("test", bus.fire("test"));
    }
}
