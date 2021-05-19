package gg.amy.soulfire.api.minecraft.block;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplementedReturn;

/**
 * @author amy
 * @since 5/18/21.
 */
@Bridge("net.minecraft.world.level.block.Block")
public interface Block {
    @Nonnull
    @BridgeMethod("<init>(net.minecraft.world.level.block.state.BlockBehaviour$Properties)")
    static Block create(@Nonnull final BlockProperties properties) {
        return unimplementedReturn();
    }
}
