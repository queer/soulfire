package gg.amy.soulfire.api.thesaurus;

import gg.amy.soulfire.api.minecraft.block.Block;
import gg.amy.soulfire.api.minecraft.item.Item;
import gg.amy.soulfire.api.minecraft.registry.Identifier;
import gg.amy.soulfire.api.minecraft.registry.Registries;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/30/21.
 */
public interface Thesaurus {
    void register(@Nonnull final ThesaurusCategory category, @Nonnull final Identifier identifier);

    default void register(@Nonnull final ThesaurusCategory category, @Nonnull final Item item) {
        final var location = Registries.items().getKey(item).get().location();
        register(category, Identifier.of(location));
    }

    default void register(@Nonnull final ThesaurusCategory category, @Nonnull final Block block) {
        final var location = Registries.blocks().getKey(block).get().location();
        register(category, Identifier.of(location));
    }

    boolean is(@Nonnull final ThesaurusCategory category, @Nonnull final Identifier identifier);

    default boolean is(@Nonnull final ThesaurusCategory category, @Nonnull final Item item) {
        final var location = Registries.items().getKey(item).get().location();
        return is(category, Identifier.of(location));
    }

    default boolean is(@Nonnull final ThesaurusCategory category, @Nonnull final Block block) {
        final var location = Registries.blocks().getKey(block).get().location();
        return is(category, Identifier.of(location));
    }

    enum ThesaurusCategory {
        LOG,
    }
}
