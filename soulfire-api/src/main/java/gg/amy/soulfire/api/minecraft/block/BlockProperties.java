package gg.amy.soulfire.api.minecraft.block;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;
import java.util.function.ToIntFunction;

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

    @BridgeMethod("noOcclusion()")
    BlockProperties noOcclusion();

    @BridgeMethod("friction(float)")
    BlockProperties friction(float friction);

    @BridgeMethod("speedFactor(float)")
    BlockProperties speedFactor(float speedFactor);

    @BridgeMethod("jumpFactor(float)")
    BlockProperties jumpFactor(float jumpFactor);

    @BridgeMethod("lightLevel(java.util.function.ToIntFunction)")
    BlockProperties lightLevel(@Nonnull ToIntFunction<BlockState> function);

    @BridgeMethod("strength(float,float)")
    BlockProperties strength(float a, float b);

    @BridgeMethod("instabreak()")
    BlockProperties instabreak();

    @BridgeMethod("strength(float)")
    BlockProperties strength(float strength);

    @BridgeMethod("randomTicks()")
    BlockProperties randomTicks();

    @BridgeMethod("dynamicShape()")
    BlockProperties dynamicShape();

    @BridgeMethod("noDrops()")
    BlockProperties noDrops();
}
