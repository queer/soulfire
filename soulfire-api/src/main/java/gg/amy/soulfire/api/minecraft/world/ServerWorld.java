package gg.amy.soulfire.api.minecraft.world;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

/**
 * @author amy
 * @since 5/20/21.
 */
@Bridge("net.minecraft.server.level.ServerLevel")
public interface ServerWorld extends World {
    @BridgeMethod("setDayTime(long)")
    void setDayTime(long ticks);
}
