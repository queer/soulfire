package gg.amy.soulfire.api.minecraft.block;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.physics.CollisionContext;
import gg.amy.soulfire.api.minecraft.physics.VoxelShape;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/18/21.
 */
@Bridge("net.minecraft.world.level.block.state.BlockBehaviour")
public interface BlockBehaviour {
    @BridgeMethod("getOcclusionShape(net.minecraft.world.level.block.state.BlockState,net.minecraft.world.level.BlockGetter,net.minecraft.core.BlockPos)")
    VoxelShape getOcclusionShape(@Nonnull BlockState state, @Nonnull BlockGetter getter, @Nonnull BlockPos pos);

    @BridgeMethod("getBlockSupportShape(net.minecraft.world.level.block.state.BlockState,net.minecraft.world.level.BlockGetter,net.minecraft.core.BlockPos)")
    VoxelShape getBlockSupportShape(@Nonnull BlockState state, @Nonnull BlockGetter getter, @Nonnull BlockPos pos);

    @BridgeMethod("getInteractionShape(net.minecraft.world.level.block.state.BlockState,net.minecraft.world.level.BlockGetter,net.minecraft.core.BlockPos)")
    VoxelShape getInteractionShape(@Nonnull BlockState state, @Nonnull BlockGetter getter, @Nonnull BlockPos pos);

    @BridgeMethod("getShape(net.minecraft.world.level.block.state.BlockState,net.minecraft.world.level.BlockGetter,net.minecraft.core.BlockPos,net.minecraft.world.phys.shapes.CollisionContext)")
    VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter getter, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx);

    @BridgeMethod("getCollisionShape(net.minecraft.world.level.block.state.BlockState,net.minecraft.world.level.BlockGetter,net.minecraft.core.BlockPos,net.minecraft.world.phys.shapes.CollisionContext)")
    VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter getter, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx);

    @BridgeMethod("getVisualShape(net.minecraft.world.level.block.state.BlockState,net.minecraft.world.level.BlockGetter,net.minecraft.core.BlockPos,net.minecraft.world.phys.shapes.CollisionContext)")
    VoxelShape getVisualShape(@Nonnull BlockState state, @Nonnull BlockGetter getter, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx);
}
