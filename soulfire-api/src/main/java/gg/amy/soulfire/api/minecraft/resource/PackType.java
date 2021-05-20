package gg.amy.soulfire.api.minecraft.resource;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.server.packs.PackType")
public interface PackType {
    @BridgeField("CLIENT_RESOURCES")
    PackType clientResources();

    @BridgeField("SERVER_DATA")
    PackType serverData();
}
