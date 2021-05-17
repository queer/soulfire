package gg.amy.ingot.api;

import static gg.amy.ingot.api.Placeholder.unimplementedReturn;

/**
 * @author amy
 * @since 5/16/21.
 */
public interface Minecraft {
    static Minecraft getInstance() {
        return unimplementedReturn();
    }

    String getLaunchedVersion();
}
