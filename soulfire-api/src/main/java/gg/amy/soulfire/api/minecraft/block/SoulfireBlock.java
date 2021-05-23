package gg.amy.soulfire.api.minecraft.block;

import gg.amy.soulfire.annotations.Constructor;
import gg.amy.soulfire.annotations.Shim;
import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.api.events.event.block.BlockInteraction;
import gg.amy.soulfire.api.minecraft.entity.Player;
import gg.amy.soulfire.api.minecraft.item.InteractionHand;
import gg.amy.soulfire.api.minecraft.item.InteractionResult;
import gg.amy.soulfire.api.minecraft.physics.BlockHitResult;
import gg.amy.soulfire.api.minecraft.physics.CollisionContext;
import gg.amy.soulfire.api.minecraft.physics.VoxelShape;
import gg.amy.soulfire.api.minecraft.physics.VoxelShapes;
import gg.amy.soulfire.api.minecraft.registry.Identifier;
import gg.amy.soulfire.api.minecraft.registry.Registries;
import gg.amy.soulfire.api.minecraft.world.World;

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

    @Override
    public InteractionResult use(@Nonnull final BlockState state, @Nonnull final World world, @Nonnull final BlockPos pos, @Nonnull final Player player, @Nonnull final InteractionHand hand, @Nonnull final BlockHitResult result) {
        // TODO: Figure out reducing duplication between this and hooks
        final var resourceLocation = Registries.blocks().getKey(this).get().location();
        return Soulfire.soulfire().bus().fire(new BlockInteraction(
                this, new Identifier(resourceLocation.namespace(), resourceLocation.path()),
                world, player, hand, InteractionResult.pass()
        )).result();
    }
}
