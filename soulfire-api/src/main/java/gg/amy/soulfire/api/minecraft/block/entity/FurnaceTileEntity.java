package gg.amy.soulfire.api.minecraft.block.entity;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.chat.ChatComponent;

/**
 * @author amy
 * @since 5/28/21.
 */
@Bridge("net.minecraft.world.level.block.entity.FurnaceBlockEntity")
public interface FurnaceTileEntity extends AbstractFurnaceTileEntity {
    @BridgeMethod("getDefaultName()")
    ChatComponent defaultName();
}
