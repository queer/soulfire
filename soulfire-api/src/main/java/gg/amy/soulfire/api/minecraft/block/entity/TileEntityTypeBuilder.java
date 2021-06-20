package gg.amy.soulfire.api.minecraft.block.entity;

import com.mojang.datafixers.types.Type;
import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/28/21.
 */
// TODO: Generates broken bytecode
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
//@Bridge("net.minecraft.world.level.block.entity.BlockEntityType$Builder")
public interface TileEntityTypeBuilder<T extends TileEntity> {
    @Nonnull
    @BridgeMethod("of(java.util.function.Supplier,net.minecraft.world.level.block.Block[])")
    static <T extends TileEntity> TileEntityTypeBuilder<T> of(@Nonnull final Supplier<? extends T> supplier,
                                                              @Nonnull final Block... blocks) {
        return unimplemented();
    }

    @Nonnull
    @BridgeMethod("net.minecraft.world.level.block.entity.BlockEntityType build(com.mojang.datafixers.types.Type)")
    TileEntityType<T> build(@Nullable Type<?> type);
}
