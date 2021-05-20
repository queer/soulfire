package gg.amy.soulfire.api.minecraft.resource;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.util.Set;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.server.packs.PackResources")
public interface ResourcePack extends AutoCloseable {
    @Nonnull
    @BridgeMethod("getRootResource(java.lang.String)")
    InputStream rootResource(@Nonnull final String string);

    @Nonnull
    @BridgeMethod("getResource(net.minecraft.server.packs.PackType,net.minecraft.resources.ResourceLocation)")
    InputStream resource(@Nonnull final PackType type, @Nonnull final ResourceLocation location);

    @BridgeMethod("hasResource(net.minecraft.server.packs.PackType,net.minecraft.resources.ResourceLocation)")
    boolean hasResource(@Nonnull final PackType type, @Nonnull final ResourceLocation location);

    // TODO: This breaks codegen -- why?
//    @BridgeMethod("getResources(net.minecraft.server.packs.PackType,java.lang.String,java.lang.String,int,java.util.function.Predicate)")
//    Collection<ResourceLocation> resources(@Nonnull final PackType type, @Nonnull final String a, @Nonnull final String b, @Nonnull final Predicate<ResourceLocation> predicate);

    @BridgeMethod("getNamespaces(net.minecraft.server.packs.PackType)")
    Set<String> namespaces(@Nonnull final PackType type);

    @BridgeMethod("getMetadataSection(net.minecraft.server.packs.metadata.MetadataSectionSerializer)")
    <T> T metadataSection(@Nonnull final MetadataSectionSerializer<T> metadataSectionSerializer);

    @BridgeMethod("getName()")
    String name();
}
