package gg.amy.soulfire.bytecode;

import java.lang.reflect.InvocationTargetException;

/**
 * @author amy
 * @since 5/21/21.
 */
public final class ClassDefiner {
    private ClassDefiner() {
    }

    public static void defineClass(final ClassLoader cl, final byte[] clazz, final String fullName) {
        try {
            final var define = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            define.setAccessible(true);
            define.invoke(cl, fullName, clazz, 0, clazz.length);
        } catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
