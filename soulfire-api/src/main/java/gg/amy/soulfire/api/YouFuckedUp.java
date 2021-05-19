package gg.amy.soulfire.api;

/**
 * @author amy
 * @since 5/17/21.
 */
public final class YouFuckedUp {
    private YouFuckedUp() {
    }

    public static void unimplementedVoid() {
        throw new IllegalStateException("Unimplemented! Did you forget to inject?");
    }

    public static <T> T unimplemented() {
        throw new IllegalStateException("Unimplemented! Did you forget to inject?");
    }
}
