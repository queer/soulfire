package gg.amy.soulfire.api.minecraft.resource;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.server.packs.metadata.pack.PackMetadataSection")
public interface PackMetadataSection {
//    @BridgeMethod("getDescription()")
//    ChatComponent description();

    @BridgeMethod("getPackFormat()")
    int packFormat();
}
