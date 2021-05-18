package gg.amy.soulfire.bytecode.mapping;

import gg.amy.soulfire.bytecode.ClassMap;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/17/21.
 */
public record MappedField(@Nonnull String name, @Nonnull String obfName, @Nonnull String type) {
    @Nonnull
    public String desc() {
        return parseType(type);
    }

    private String parseType(@Nonnull final String type) {
        return switch(type) {
            case "boolean" -> "Z";
            case "byte" -> "B";
            case "short" -> "S";
            case "char" -> "C";
            case "int" -> "I";
            case "long" -> "J";
            case "float" -> "F";
            case "double" -> "D";
            case "void" -> "V";
            default -> parseComplex(type);
        };
    }

    private String parseComplex(@Nonnull final String type) {
        if(type.contains(".")) {
            final var name = (type.startsWith("L") ? type.replaceFirst("L", "") : type).replace(";", "");
            final var lookup = ClassMap.lookup(name);
            if(lookup != null) {
                return lookup.descriptor();
            } else {
                return 'L' + type.replace('.', '/') + ';';
            }
        } else if(type.startsWith("[")) {
            return '[' + parseType(type.substring(1));
        } else {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
