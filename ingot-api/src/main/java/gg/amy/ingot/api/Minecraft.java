package gg.amy.ingot.api;

/**
 * @author amy
 * @since 5/16/21.
 */
public interface Minecraft {
    String getVersion();

    static Minecraft getInstance() {
        throw new IllegalStateException("Unimplemented! Did you forget to inject?");
    }
}
