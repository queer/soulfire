package gg.amy.ingot.bytecode;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/16/21.
 */
@SuppressWarnings("Singleton")
public enum Mappings {
    V1_16_5(
            "https://launcher.mojang.com/v1/objects/374c6b789574afbdc901371207155661e0509e17/client.txt",
            "https://launcher.mojang.com/v1/objects/41285beda6d251d190f2bf33beadd4fee187df7a/server.txt"
    ),
    ;

    private final String client;
    private final String server;

    Mappings(@Nonnull final String client, @Nonnull final String server) {
        this.client = client;
        this.server = server;
    }

    @Nonnull
    public String client() {
        return client;
    }

    @Nonnull
    public String server() {
        return server;
    }

    @Nonnull
    public static Mappings byVersion(@Nonnull final String version) {
        final var up = 'V' + version.replace('.', '_');
        return valueOf(up)  ;
    }
}
