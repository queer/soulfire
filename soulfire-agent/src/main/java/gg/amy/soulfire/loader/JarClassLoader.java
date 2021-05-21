package gg.amy.soulfire.loader;

import gg.amy.soulfire.utils.URLHelper;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author amy
 * @since 5/19/21.
 */
public class JarClassLoader extends URLClassLoader {
    public JarClassLoader(final File jar) {
        super(new URL[]{URLHelper.fromFile(jar)}, ClassLoader.getSystemClassLoader());
    }
}
