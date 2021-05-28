package gg.amy.soulfire.api.minecraft.world;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.block.BlockPos;
import gg.amy.soulfire.api.minecraft.block.entity.TileEntity;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/28/21.
 */
@Bridge("net.minecraft.world.level.chunk.LevelChunk")
public interface Chunk {
    @BridgeMethod("createBlockEntity(net.minecraft.core.BlockPos)")
    TileEntity createTileEntity(@Nonnull BlockPos pos);
}
