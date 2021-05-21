package gg.amy.soulfire.api.minecraft.world;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/20/21.
 */
@Bridge("net.minecraft.world.level.Level")
public interface World {
    @BridgeField("OVERWORLD")
    static World overworld() {
        return unimplemented();
    }

    @BridgeField("NETHER")
    static World nether() {
        return unimplemented();
    }

    @BridgeField("END")
    static World end() {
        return unimplemented();
    }

    @BridgeField("isClientSide")
    boolean client();

    default boolean server() {
        return !client();
    }

    @BridgeField("thunderLevel")
    float thunderLevel();
}
