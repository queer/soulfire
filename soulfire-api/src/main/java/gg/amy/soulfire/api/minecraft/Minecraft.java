package gg.amy.soulfire.api.minecraft;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/16/21.
 */
@Bridge("net.minecraft.client.Minecraft")
public interface Minecraft {
    @BridgeMethod("getInstance()")
    static Minecraft getInstance() {
        return unimplemented();
    }

    @BridgeMethod("getLaunchedVersion()")
    String launchedVersion();
}
