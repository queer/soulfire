package gg.amy.ingot.bytecode.mapping;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author amy
 * @since 5/16/21.
 */
public record MappedClass(
        @Nonnull String name,
        @Nonnull String obfuscatedName,
        @Nonnull Map<String, MappedField> fields,
        @Nonnull Map<String, MappedMethod> methods
) {
    @Nonnull
    public String descriptor() {
        return 'L' + obfuscatedName + ';';
    }
}
