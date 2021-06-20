package gg.amy.soulfire.api.minecraft.world;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.block.BlockPos;
import gg.amy.soulfire.api.minecraft.block.BlockState;
import gg.amy.soulfire.api.minecraft.block.entity.TileEntity;
import gg.amy.soulfire.api.minecraft.entity.Player;
import gg.amy.soulfire.api.minecraft.sound.Sound;
import gg.amy.soulfire.api.minecraft.sound.SoundSource;

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

    @BridgeMethod("removeBlock(net.minecraft.core.BlockPos,boolean)")
    boolean removeBlock(@Nonnull BlockPos pos, boolean a);

    @Nonnull
    @BridgeMethod("getBlockState(net.minecraft.core.BlockPos)")
    BlockState blockState(@Nonnull BlockPos pos);

    @BridgeMethod("playLocalSound(double,double,double,net.minecraft.sounds.SoundEvent,net.minecraft.sounds.SoundSource,float,float,boolean)")
    void playLocalSound(double x, double y, double z, @Nonnull Sound sound, @Nonnull SoundSource source, float a, float b, boolean c);

    @BridgeMethod("playSound(net.minecraft.world.entity.player.Player,double,double,double,net.minecraft.sounds.SoundEvent,net.minecraft.sounds.SoundSource,float,float)")
    void playSound(Player player, double x, double y, double z, @Nonnull Sound sound, @Nonnull SoundSource source, float a, float b);
}
