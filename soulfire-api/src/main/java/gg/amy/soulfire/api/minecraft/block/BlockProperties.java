package gg.amy.soulfire.api.minecraft.block;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/18/21.
 */
@Bridge("net.minecraft.world.level.block.state.BlockBehaviour$Properties")
public
interface BlockProperties {
    @BridgeMethod("of(net.minecraft.world.level.material.Material)")
    static BlockProperties of(@Nonnull final Material material) {
        return unimplemented();
    }
}
