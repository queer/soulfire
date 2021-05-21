package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.entity.Player;
import gg.amy.soulfire.api.minecraft.world.World;

/**
 * @author amy
 * @since 5/20/21.
 */
@Bridge("net.minecraft.world.item.context.UseOnContext")
public interface ItemUseContext {
    @BridgeMethod("getItemInHand()")
    ItemStack itemInHand();

    @BridgeMethod("getHand()")
    InteractionHand hand();

    @BridgeMethod("getLevel()")
    World world();

    @BridgeMethod("getPlayer()")
    Player player();
}
