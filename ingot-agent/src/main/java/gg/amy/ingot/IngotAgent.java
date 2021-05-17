package gg.amy.ingot;

import gg.amy.ingot.bytecode.ClassMap;
import gg.amy.ingot.bytecode.Redefiner;
import gg.amy.ingot.bytecode.injectors.ClientBrandRetrieverInjector;
import gg.amy.ingot.bytecode.injectors.MinecraftInjector;
import gg.amy.ingot.bytecode.redefiners.IngotClientBrandRetrieverRedefiner;
import gg.amy.ingot.bytecode.redefiners.IngotMinecraftRedefiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.List;

/**
 * @author amy
 * @since 5/16/21.
 */
public final class IngotAgent {
    private static final Logger LOGGER = LogManager.getLogger();

    private IngotAgent() {
    }

    public static void premain(@Nonnull final String agentArgs, @Nonnull final Instrumentation i) {
        try {
            ClassMap.map();

            // Injectors and redefiners require mappings to already be parsed
            final var injectors = List.of(
                    new MinecraftInjector(),
                    new ClientBrandRetrieverInjector()
            );
            final var redefiners = List.of(
                    new IngotMinecraftRedefiner(),
                    new IngotClientBrandRetrieverRedefiner()
            );

            // Add injectors
            injectors.forEach(injector -> {
                i.addTransformer(injector);
                LOGGER.info("Added transformer for: '{}'", injector.classToInject());
            });

            // Add redefiners
            i.redefineClasses(redefiners.stream().map(Redefiner::redefine).toArray(ClassDefinition[]::new));
            LOGGER.info("premain finished! Booting Minecraft...");
        } catch(final Throwable t) {
            LOGGER.error("Couldn't finish premain:", t);
            System.exit(1);
        }
    }
}