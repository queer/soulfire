package gg.amy.soulfire.bytecode.mapping;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author amy
 * @since 5/16/21.
 */
public record MappedClass(
        @Nonnull String name,
        @Nonnull String obfName,
        @Nonnull Map<String, MappedField> fields,
        @Nonnull Map<String, MappedMethod> methods
) {
    @Nonnull
    public String descriptor() {
        return 'L' + obfName + ';';
    }

    public MappedField field(@Nonnull final String field) {
        return fields.get(field);
    }

    public MappedMethod method(@Nonnull final String method) {
        return methods.get(method);
    }
}
