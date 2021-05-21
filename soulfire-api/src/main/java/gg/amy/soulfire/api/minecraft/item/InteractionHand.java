package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;

/**
 * @author amy
 * @since 5/20/21.
 */
@Bridge("net.minecraft.world.InteractionHand")
public interface InteractionHand {
    @BridgeField("MAIN_HAND")
    InteractionHand mainHand();

    @BridgeField("OFF_HAND")
    InteractionHand offHand();
}
