package gg.amy.soulfire.api.minecraft.resource;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.server.packs.repository.Pack")
public interface ResourcePackInfo {
    @BridgeMethod("create(java.lang.String,boolean,java.util.function.Supplier,net.minecraft.server.packs.repository.Pack$PackConstructor,net.minecraft.server.packs.repository.Pack$Position,net.minecraft.server.packs.repository.PackSource)")
    static ResourcePackInfo create(@Nonnull final String packId, final boolean allowFallback,
                                   final Supplier<ResourcePack> packSupplier, final PackInfoConstructor factory,
                                   final PackPosition position, final PackSource source) {
        return unimplemented();
    }

    @FunctionalInterface
    @Bridge("net.minecraft.server.packs.repository.Pack$PackConstructor")
    interface PackInfoConstructor {
        //        @BridgeMethod("create(java.lang.String,boolean,java.util.function.Supplier,net.minecraft.server.packs.PackResources,net.minecraft.server.packs.metadata.pack.PackMetadataSection,net.minecraft.server.packs.repository.Pack$Position,net.minecraft.server.packs.repository.PackSource)")
        ResourcePackInfo create(@Nonnull final String packId, boolean allowFallback,
                                @Nonnull Supplier<ResourcePack> supplier, @Nonnull PackMetadataSection section,
                                PackPosition position, PackSource source);
    }

    @Bridge("net.minecraft.server.packs.repository.Pack$Position")
    interface PackPosition {
        @BridgeField("TOP")
        PackPosition top();

        @BridgeField("BOTTOM")
        PackPosition bottom();
    }

    @Bridge("net.minecraft.server.packs.repository.PackSource")
    interface PackSource {
        @BridgeField("DEFAULT")
        PackSource default_();

        @BridgeField("BUILT_IN")
        PackSource builtIn();

        @BridgeField("WORLD")
        PackSource world();

        @BridgeField("SERVER")
        PackSource server();
    }
}
