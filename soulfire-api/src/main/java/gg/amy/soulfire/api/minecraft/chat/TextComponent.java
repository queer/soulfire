package gg.amy.soulfire.api.minecraft.chat;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.network.chat.TextComponent")
public interface TextComponent extends MutableComponent {
    @BridgeMethod("<init>(java.lang.String)")
    static TextComponent of(@Nonnull final String message) {
        return unimplemented();
    }
}
