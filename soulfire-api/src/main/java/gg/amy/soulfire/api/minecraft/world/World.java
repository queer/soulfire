package gg.amy.soulfire.api.minecraft.world;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.annotations.DumpASM;
import gg.amy.soulfire.api.minecraft.block.BlockPos;
import gg.amy.soulfire.api.minecraft.block.entity.TileEntity;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/20/21.
 */
@Bridge("net.minecraft.world.level.Level")
public interface World {
    @Nonnull
    @BridgeField("OVERWORLD")
    static World overworld() {
        return unimplemented();
    }

    @Nonnull
    @BridgeField("NETHER")
    static World nether() {
        return unimplemented();
    }

    @Nonnull
    @BridgeField("END")
    static World end() {
        return unimplemented();
    }

    @BridgeField("isClientSide")
    boolean client();

    default boolean server() {
        return !client();
    }

    @BridgeField("thunderLevel")
    float thunderLevel();

    @Nonnull
    @BridgeMethod("getChunkAt(net.minecraft.core.BlockPos)")
    Chunk chunkAt(@Nonnull BlockPos pos);

    @BridgeMethod("addBlockEntity(net.minecraft.world.level.block.entity.BlockEntity)")
    boolean addTileEntity(@Nonnull TileEntity entity);
}
