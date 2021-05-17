package gg.amy.ingot.bytecode.mapping;

import gg.amy.ingot.bytecode.ClassMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author amy
 * @since 5/17/21.
 */
public record MappedMethod(@Nonnull String name, @Nonnull String obfName, @Nonnull String type) {
    public String desc() {
        final var obfuscatedType = parseType(type);
        if(!name.endsWith("()")) {
            final var args = name.replace(")", "").split("\\(")[1].split(",");
            final var obfuscatedArgs = Arrays.stream(args)
                    .map(this::parseType)
                    .collect(Collectors.joining(","));
            return '(' + obfuscatedArgs + ')' + obfuscatedType;
        } else {
            return "()" + obfuscatedType;
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
                return type;
            }
        } else if(type.startsWith("[")) {
            return '[' + parseType(type.substring(1));
        } else {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
