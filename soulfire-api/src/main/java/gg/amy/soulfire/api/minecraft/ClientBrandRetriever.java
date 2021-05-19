package gg.amy.soulfire.api.minecraft;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import static gg.amy.soulfire.api.YouFuckedUp.unimplementedReturn;

/**
 * @author amy
 * @since 5/17/21.
 */
@Bridge("net.minecraft.client.ClientBrandRetriever")
public interface ClientBrandRetriever {
    @BridgeMethod("getClientModName()")
    static String clientModName() {
        return unimplementedReturn();
    }
}
