package gg.amy.soulfire;

import gg.amy.soulfire.api.SoulfireImpl;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.Redefiner;
import gg.amy.soulfire.bytecode.injectors.*;
import gg.amy.soulfire.bytecode.redefiners.SoulfireRedefiner;
import gg.amy.soulfire.bytecode.synthesis.BridgeSynthesiser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            LOGGER.info("Opening up java.lang modules...");
            i.redefineModule(String.class.getModule(), Set.of(), Map.of(), Map.of("java.lang", Set.of(SoulfireAgent.class.getModule())), Set.of(), Map.of());
            i.redefineModule(Field.class.getModule(), Set.of(), Map.of(), Map.of("java.lang.reflect", Set.of(SoulfireAgent.class.getModule())), Set.of(), Map.of());
            LOGGER.info("Mapping classes...");
            ClassMap.map();

            LOGGER.info("Synthesising bridges...");
            BridgeSynthesiser.synthesise(i);

            LOGGER.info("Injecting and redefining...");
            // Injectors and redefiners require mappings to already be parsed
            final var injectors = List.of(
                    new MinecraftInjector(),
                    new ClientBrandRetrieverInjector(),
                    new TitleScreenInjector(),
                    new ResourceLocationInjector(),
                    new ResourceKeyInjector(),
                    new SimpleReloadableResourceManagerInjector()
            );
            final var redefiners = List.<Redefiner>of(
                    new SoulfireRedefiner()
            );

            // Add injectors
            injectors.forEach(injector -> {
                i.addTransformer(injector, injector.needsRetransform());
                LOGGER.info("Added transformer for: '{}'", injector.classToInject());
            });

            // Add redefiners
            i.redefineClasses(redefiners.stream().map(Redefiner::redefine).toArray(ClassDefinition[]::new));

            // Retransforming injectors
            injectors.stream().filter(Injector::needsRetransform).forEach(injector -> {
                try {
                    LOGGER.info("Retransforming class {}", injector.classToInject());
                    i.retransformClasses(Class.forName(injector.classToInject().replace("/", ".")));
                } catch(final UnmodifiableClassException | ClassNotFoundException e) {
                    throw new IllegalStateException(e);
                }
            });

            ((SoulfireImpl) SoulfireImpl.INSTANCE).modLoader().init();
            LOGGER.info("premain finished! Booting Minecraft...");
        } catch(final Throwable t) {
            LOGGER.error("Couldn't finish premain:", t);
            System.exit(1);
        }
    }
}