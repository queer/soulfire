package gg.amy.soulfire;

import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.Redefiner;
import gg.amy.soulfire.bytecode.injectors.ClientBrandRetrieverInjector;
import gg.amy.soulfire.bytecode.injectors.MinecraftInjector;
import gg.amy.soulfire.bytecode.injectors.TitleScreenInjector;
import gg.amy.soulfire.bytecode.redefiners.SoulfireClientBrandRetrieverRedefiner;
import gg.amy.soulfire.bytecode.redefiners.SoulfireMinecraftRedefiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.List;

/**
 * @author amy
 * @since 5/16/21.
 */
public final class SoulfireAgent {
    private static final Logger LOGGER = LogManager.getLogger();

    private SoulfireAgent() {
    }

    public static void premain(@Nonnull final String agentArgs, @Nonnull final Instrumentation i) {
        try {
            ClassMap.map();

            // Injectors and redefiners require mappings to already be parsed
            final var injectors = List.of(
                    new MinecraftInjector(),
                    new ClientBrandRetrieverInjector(),
                    new TitleScreenInjector()
            );
            final var redefiners = List.of(
                    new SoulfireMinecraftRedefiner(),
                    new SoulfireClientBrandRetrieverRedefiner()
            );

            // Add injectors
            injectors.forEach(injector -> {
                i.addTransformer(injector, injector.needsRetransform());
                LOGGER.info("Added transformer for: '{}'", injector.classToInject());
            });

            // Add redefiners
            i.redefineClasses(redefiners.stream().map(Redefiner::redefine).toArray(ClassDefinition[]::new));
            injectors.stream().filter(Injector::needsRetransform).forEach(injector -> {
                try {
                    i.retransformClasses(Class.forName(injector.classToInject().replace("/", ".")));
                } catch(final UnmodifiableClassException | ClassNotFoundException e) {
                    throw new IllegalStateException(e);
                }
            });
            LOGGER.info("premain finished! Booting Minecraft...");
        } catch(final Throwable t) {
            LOGGER.error("Couldn't finish premain:", t);
            System.exit(1);
        }
    }
}