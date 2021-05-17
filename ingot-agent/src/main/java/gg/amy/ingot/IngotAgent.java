package gg.amy.ingot;

import gg.amy.ingot.bytecode.Mappings;
import gg.amy.ingot.bytecode.injectors.MinecraftInjector;
import gg.amy.ingot.bytecode.mapping.MappedClass;
import gg.amy.ingot.bytecode.mapping.MappingsParser;
import gg.amy.ingot.bytecode.redefiners.IngotMinecraftRedefiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author amy
 * @since 5/16/21.
 */
public final class IngotAgent {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Map<String, MappedClass> CURRENT_MAPPINGS;

    private IngotAgent() {
    }

    public static void premain(@Nonnull final String agentArgs, @Nonnull final Instrumentation i) {
        try {
            // Load and parse mappings
            final var version = System.getProperty("ingot.minecraft.version");
            final var mappings = Mappings.byVersion(version);
            final var client = HttpClient.newHttpClient();
            final var fetchMappingsReq = HttpRequest.newBuilder()
                    .GET()
                    // TODO: Autodetect client vs server
                    .uri(URI.create(mappings.client()))
                    .build();
            final var res = client.send(fetchMappingsReq, BodyHandlers.ofString());
            final var clientMappings = res.body();
            LOGGER.info("Downloaded client mappings, {} lines", clientMappings.lines().count());
            final var parsedMappings = MappingsParser.parseMappings(clientMappings);
            final var count = parsedMappings.size();
            LOGGER.info("Parsed {} mapped classes", count);
            CURRENT_MAPPINGS = parsedMappings;

            // Injectors and redefiners require mappings to already be parsed
            final var injectors = List.of(
                    new MinecraftInjector()
            );
            final var redefiners = List.of(
                    new IngotMinecraftRedefiner()
            );

            // Add injectors
            injectors.forEach(injector -> {
                i.addTransformer(injector);
                LOGGER.info("Added transformer for: '{}'", injector.classToInject());
            });

            // Add redefiners
            i.redefineClasses(redefiners.stream().map(IngotMinecraftRedefiner::redefine).toArray(ClassDefinition[]::new));
            LOGGER.info("premain finished! Booting Minecraft...");
        } catch(final Throwable t) {
            LOGGER.error("Couldn't finish premain:", t);
            System.exit(1);
        }
    }

    public static Map<String, MappedClass> currentMappings() {
        return CURRENT_MAPPINGS;
    }
}
