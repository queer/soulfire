package gg.amy.soulfire.api.minecraft;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import static gg.amy.soulfire.api.Placeholder.unimplementedReturn;

/**
 * @author amy
 * @since 5/16/21.
 */
@Bridge("net.minecraft.client.Minecraft")
public interface Minecraft {
    @BridgeMethod("getInstance()")
    static Minecraft getInstance() {
        return unimplementedReturn();
    }

    @BridgeMethod("getLaunchedVersion()")
    String launchedVersion();
}
