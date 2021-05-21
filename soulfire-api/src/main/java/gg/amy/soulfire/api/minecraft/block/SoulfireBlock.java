package gg.amy.soulfire.api.minecraft.block;

import gg.amy.soulfire.annotations.Constructor;
import gg.amy.soulfire.annotations.Shim;
import gg.amy.soulfire.api.minecraft.physics.CollisionContext;
import gg.amy.soulfire.api.minecraft.physics.VoxelShape;
import gg.amy.soulfire.api.minecraft.physics.VoxelShapes;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/21/21.
 */
@Shim("net.minecraft.world.level.block.Block")
public class SoulfireBlock implements Block {
    @Constructor("<init>(net.minecraft.world.level.block.state.BlockBehaviour$Properties)")
    public SoulfireBlock(@Nonnull final BlockProperties properties) {
    }

    @Override
    public VoxelShape getOcclusionShape(@Nonnull final BlockState state, @Nonnull final BlockGetter getter, @Nonnull final BlockPos pos) {
        return VoxelShapes.block();
    }

    @Override
    public VoxelShape getBlockSupportShape(@Nonnull final BlockState state, @Nonnull final BlockGetter getter, @Nonnull final BlockPos pos) {
        return VoxelShapes.block();
    }

    @Override
    public VoxelShape getInteractionShape(@Nonnull final BlockState state, @Nonnull final BlockGetter getter, @Nonnull final BlockPos pos) {
        return VoxelShapes.block();
    }

    @Override
    public VoxelShape getShape(@Nonnull final BlockState state, @Nonnull final BlockGetter getter, @Nonnull final BlockPos pos, @Nonnull final CollisionContext ctx) {
        return VoxelShapes.block();
    }

    @Override
    public VoxelShape getCollisionShape(@Nonnull final BlockState state, @Nonnull final BlockGetter getter, @Nonnull final BlockPos pos, @Nonnull final CollisionContext ctx) {
        return VoxelShapes.block();
    }

    @Override
    public VoxelShape getVisualShape(@Nonnull final BlockState state, @Nonnull final BlockGetter getter, @Nonnull final BlockPos pos, @Nonnull final CollisionContext ctx) {
        return VoxelShapes.block();
    }
}
