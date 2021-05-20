package gg.amy.soulfire.api.minecraft.resource;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;
import java.io.File;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.server.packs.FilePackResources")
public interface FileResourcePack extends AbstractResourcePack {
    @BridgeMethod("<init>(java.io.File)")
    static FileResourcePack of(@Nonnull final File file) {
        return unimplemented();
    }
}
