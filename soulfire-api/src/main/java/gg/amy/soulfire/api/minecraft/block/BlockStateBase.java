package gg.amy.soulfire.api.minecraft.block;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

/**
 * @author amy
 * @since 5/30/21.
 */
@Bridge("net.minecraft.world.level.block.state.BlockBehaviour$BlockStateBase")
public interface BlockStateBase {
    @BridgeMethod("getBlock()")
    Block block();

    @BridgeMethod("getMaterial()")
    Material material();
}
