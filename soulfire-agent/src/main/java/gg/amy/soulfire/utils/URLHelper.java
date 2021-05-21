package gg.amy.soulfire.utils;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author amy
 * @since 5/19/21.
 */
public final class URLHelper {
    private URLHelper() {
    }

    @Nonnull
    public static URL fromFile(@Nonnull final File file) {
        try {
            return file.toURI().toURL();
        } catch(final MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
