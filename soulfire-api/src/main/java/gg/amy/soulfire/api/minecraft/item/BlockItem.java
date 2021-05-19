package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.api.minecraft.block.Block;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplementedReturn;

/**
 * @author amy
 * @since 5/18/21.
 */
@Bridge("net.minecraft.world.item.BlockItem")
public interface BlockItem extends Item {
    @Nonnull
    static BlockItem create(@Nonnull final Block block, @Nonnull final ItemProperties properties) {
        return unimplementedReturn();
    }
}
