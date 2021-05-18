package gg.amy.soulfire.bytecode.mapping;

import gg.amy.soulfire.bytecode.ClassMap;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author amy
 * @since 5/17/21.
 */
public record MappedMethod(@Nonnull String name, @Nonnull String obfName, @Nonnull String type) {
    public String desc() {
        return desc(",");
    }

    public String descNoComma() {
        return desc("");
    }

    public String desc(@Nonnull final String delim) {
        final var obfuscatedType = parseType(type);
        if(!name.endsWith("()")) {
            final var args = name.replace(")", "").split("\\(")[1].split(",");
            final var obfuscatedArgs = Arrays.stream(args)
                    .map(this::parseType)
                    .collect(Collectors.joining(delim));
            return '(' + obfuscatedArgs + ')' + obfuscatedType;
        } else {
            return "()" + obfuscatedType;
        }
    }

    public List<String> argTypes() {
        if(!name.endsWith("()")) {
            final var args = name.replace(")", "").split("\\(")[1].split(",");
            return Arrays.stream(args)
                    .map(this::parseType)
                    .map(t -> {
                        if(t.endsWith(";")) {
                            return t.replace(";", "").replaceFirst("L", "");
                        } else {
                            return t;
                        }
                    })
                    .toList();
        } else {
            return List.of();
        }
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
