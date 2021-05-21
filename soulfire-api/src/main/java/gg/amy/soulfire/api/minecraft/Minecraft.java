package gg.amy.soulfire.api.minecraft;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.resource.ReloadableResourceManager;
import gg.amy.soulfire.api.minecraft.resource.ResourcePackRespository;

import java.io.File;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/16/21.
 */
@Bridge("net.minecraft.client.Minecraft")
public interface Minecraft {
    /**
     * @return The singleton Minecraft instance.
     */
    @BridgeMethod("getInstance()")
    static Minecraft getInstance() {
        return unimplemented();
    }

    @BridgeMethod("getLaunchedVersion()")
    String launchedVersion();

    @BridgeField("gameDirectory")
    File gameDir();

    @BridgeField("resourceManager")
    ReloadableResourceManager resourceManager();

    @BridgeField("resourcePackRepository")
    ResourcePackRespository resourcePackRepository();
}
