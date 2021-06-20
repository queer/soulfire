package gg.amy.soulfire.api;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import gg.amy.soulfire.api.minecraft.block.Blocks;
import gg.amy.soulfire.api.minecraft.registry.Identifier;
import gg.amy.soulfire.api.thesaurus.Thesaurus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.thesaurus.Thesaurus.ThesaurusCategory.LOG;

/**
 * The {@code Thesaurus} is like Forge's {@code OreDict}.
 *
 * @author amy
 * @since 5/30/21.
 */
public class ThesaurusImpl implements Thesaurus {
    private final Logger logger = LogManager.getLogger();
    private final Multimap<ThesaurusCategory, Identifier> thesaurus = HashMultimap.create();

    @Override
    public void register(@Nonnull final ThesaurusCategory category, @Nonnull final Identifier identifier) {
        if(thesaurus.put(category, identifier)) {
            logger.info("Loaded new Thesaurus entry: {} -> {}", identifier, category);
        }
    }

    @Override
    public boolean is(@Nonnull final ThesaurusCategory category, @Nonnull final Identifier identifier) {
        return thesaurus.containsEntry(category, identifier);
    }

    public void setup() {
        register(LOG, Blocks.oakLog());
        register(LOG, Blocks.acaciaLog());
        register(LOG, Blocks.birchLog());
        register(LOG, Blocks.jungleLog());
        register(LOG, Blocks.spruceLog());
        register(LOG, Blocks.darkOakLeaves());
    }
}
