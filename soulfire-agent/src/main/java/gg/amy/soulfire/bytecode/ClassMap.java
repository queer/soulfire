package gg.amy.soulfire.bytecode;

import gg.amy.soulfire.bytecode.mapping.MappedClass;
import gg.amy.soulfire.bytecode.mapping.MappingsParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

/**
 * @author amy
 * @since 5/17/21.
 */
public final class ClassMap {
    private static final Logger LOGGER = LogManager.getLogger();

    private static Map<String, MappedClass> CURRENT_MAPPINGS;

    private ClassMap() {
    }

    public static void map() throws IOException, InterruptedException {
        final var version = System.getProperty("soulfire.minecraft.version");
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
    }

    public static MappedClass lookup(@Nonnull final String cls) {
        return CURRENT_MAPPINGS.get(cls);
    }
}
