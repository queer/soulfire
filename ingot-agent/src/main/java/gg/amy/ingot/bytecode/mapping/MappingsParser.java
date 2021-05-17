package gg.amy.ingot.bytecode.mapping;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author amy
 * @since 5/16/21.
 */
public final class MappingsParser {
    private MappingsParser() {
    }

    public static Map<String, MappedClass> parseMappings(@Nonnull final String mappingsFile) {
        final var lines = mappingsFile.lines().collect(Collectors.toList());
        final var mappings = new HashMap<String, MappedClass>();
        String currentLine = null;
        String currentClass = null;
        String obfCurrentClass = null;
        final var fields = new HashMap<String, String>();
        final var methods = new HashMap<String, String>();
        try {
            for(@Nonnull final var line : lines) {
                currentLine = line;
                if(line.startsWith("#")) {
                    continue;
                }
                // Mappings are laid out like
                //
                //     name -> obfName
                //         fieldType fieldName -> obfFieldName
                //         methodStartLine?:methodEndLine?:methodType methodNameAndArgs -> obfMethodName
                if(!line.startsWith(" ")) {
                    if(currentClass != null) {
                        mappings.put(currentClass, new MappedClass(currentClass, obfCurrentClass, Map.copyOf(fields), Map.copyOf(methods)));
                        fields.clear();
                        methods.clear();
                    }
                    final var parts = line.split(" -> ", 2);
                    currentClass = parts[0].trim();
                    obfCurrentClass = parts[1].trim().replace(":", "");
                } else {
                    final var parts = line.trim().split(" -> ", 2);
                    final var methodOrFieldTypeAndName = parts[0].split(" ", 2);
                    final var type = methodOrFieldTypeAndName[0];
                    final var name = methodOrFieldTypeAndName[1];
                    final var obfName = parts[1];
                    if(type.matches("^\\d+.*")) {
                        methods.put(name, obfName);
                    } else {
                        fields.put(name, obfName);
                    }
                }
            }
        } catch(final Exception e) {
            throw new IllegalStateException("Error parsing mappings!\nLast line was: " + currentLine, e);
        }
        return Map.copyOf(mappings);
    }
}
