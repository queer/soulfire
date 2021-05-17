package gg.amy.soulfire.bytecode.mapping;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/17/21.
 */
public record MappedField(@Nonnull String name, @Nonnull String obfName, @Nonnull String type) {
}
