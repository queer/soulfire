package gg.amy.soulfire.api.minecraft;

import static gg.amy.soulfire.api.Placeholder.unimplementedReturn;

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
