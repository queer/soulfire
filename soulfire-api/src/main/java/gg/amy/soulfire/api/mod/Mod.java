package gg.amy.soulfire.api.mod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as being the main class of a mod. You can only have a single
 * {@code @Mod} class per JAR file.
 *
 * @author amy
 * @since 5/17/21.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mod {
    String value();
}
