package gg.amy.soulfire.api.minecraft.registry;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.Nontransforming;

import static gg.amy.soulfire.api.Placeholder.unimplementedReturn;

/**
 * @author amy
 * @since 5/17/21.
 */
@SuppressWarnings({"unused", "rawtypes"})
public interface Registries {
    // TODO: Apply types to literally ALL of these

    static Registry soundEvents() {
        return unimplementedReturn();
    }

    static Registry fluids() {
        return unimplementedReturn();
    }

    static Registry mobEffects() {
        return unimplementedReturn();
    }

    static Registry blocks() {
        return unimplementedReturn();
    }

    static Registry enchantments() {
        return unimplementedReturn();
    }

    static Registry entityTypes() {
        return unimplementedReturn();
    }

    static Registry items() {
        return unimplementedReturn();
    }

    static Registry potions() {
        return unimplementedReturn();
    }

    static Registry particleTypes() {
        return unimplementedReturn();
    }

    static Registry blockEntityTypes() {
        return unimplementedReturn();
    }

    static Registry motives() {
        return unimplementedReturn();
    }

    static Registry customStats() {
        return unimplementedReturn();
    }

    static Registry chunkStatuses() {
        return unimplementedReturn();
    }

    static Registry ruleTests() {
        return unimplementedReturn();
    }

    static Registry posRuleTests() {
        return unimplementedReturn();
    }

    static Registry menus() {
        return unimplementedReturn();
    }

    static Registry recipeTypes() {
        return unimplementedReturn();
    }

    static Registry recipeSerializers() {
        return unimplementedReturn();
    }

    static Registry attributes() {
        return unimplementedReturn();
    }

    static Registry statTypes() {
        return unimplementedReturn();
    }

    static Registry villagerTypes() {
        return unimplementedReturn();
    }

    static Registry villagerProfessions() {
        return unimplementedReturn();
    }

    static Registry pointOfInterestTypes() {
        return unimplementedReturn();
    }

    static Registry memoryModuleTypes() {
        return unimplementedReturn();
    }

    static Registry sensorTypes() {
        return unimplementedReturn();
    }

    static Registry schedules() {
        return unimplementedReturn();
    }

    static Registry activities() {
        return unimplementedReturn();
    }

    static Registry lootEntries() {
        return unimplementedReturn();
    }

    static Registry lootFunctions() {
        return unimplementedReturn();
    }

    static Registry lootItems() {
        return unimplementedReturn();
    }

    static Registry dimensionTypes() {
        return unimplementedReturn();
    }

    static Registry dimensions() {
        return unimplementedReturn();
    }

    static Registry levelStems() {
        return unimplementedReturn();
    }

    static Registry noiseGeneratorSettings() {
        return unimplementedReturn();
    }

    static Registry configuredSurfaceBuilders() {
        return unimplementedReturn();
    }

    static Registry configuredCarvers() {
        return unimplementedReturn();
    }

    static Registry configuredFeatures() {
        return unimplementedReturn();
    }

    static Registry configuredStructureFeatures() {
        return unimplementedReturn();
    }

    static Registry processorLists() {
        return unimplementedReturn();
    }

    static Registry templatePools() {
        return unimplementedReturn();
    }

    static Registry biomes() {
        return unimplementedReturn();
    }

    static Registry surfaceBuilders() {
        return unimplementedReturn();
    }

    static Registry blockStateProviderTypes() {
        return unimplementedReturn();
    }

    static Registry blockPlacerTypes() {
        return unimplementedReturn();
    }

    static Registry foliagePlacerTypes() {
        return unimplementedReturn();
    }

    static Registry trunkPlacerTypes() {
        return unimplementedReturn();
    }

    static Registry treeDecoratorTypes() {
        return unimplementedReturn();
    }

    static Registry featureSizeTypes() {
        return unimplementedReturn();
    }

    static Registry biomeSources() {
        return unimplementedReturn();
    }

    static Registry chunkGenerators() {
        return unimplementedReturn();
    }

    static Registry structureProcessors() {
        return unimplementedReturn();
    }

    static Registry structurePoolElements() {
        return unimplementedReturn();
    }

    @Nontransforming
    @Bridge("net.minecraft.core.Registry")
    interface ResourceKeys {
        @BridgeField("SOUND_EVENT_REGISTRY")
        static ResourceKey soundEvents() {
            return unimplementedReturn();
        }

        @BridgeField("FLUID_REGISTRY")
        static ResourceKey fluids() {
            return unimplementedReturn();
        }

        @BridgeField("MOB_EFFECT_REGISTRY")
        static ResourceKey mobEffects() {
            return unimplementedReturn();
        }

        @BridgeField("BLOCK_REGISTRY")
        static ResourceKey blocks() {
            return unimplementedReturn();
        }

        @BridgeField("ENCHANTMENT_REGISTRY")
        static ResourceKey enchantments() {
            return unimplementedReturn();
        }

        @BridgeField("ENTITY_TYPE_REGISTRY")
        static ResourceKey entityTypes() {
            return unimplementedReturn();
        }

        @BridgeField("ITEM_REGISTRY")
        static ResourceKey items() {
            return unimplementedReturn();
        }

        @BridgeField("POTION_REGISTRY")
        static ResourceKey potions() {
            return unimplementedReturn();
        }

        @BridgeField("PARTICLE_TYPE_REGISTRY")
        static ResourceKey particleTypes() {
            return unimplementedReturn();
        }

        @BridgeField("BLOCK_ENTITY_TYPE_REGISTRY")
        static ResourceKey blockEntityTypes() {
            return unimplementedReturn();
        }

        @BridgeField("MOTIVE_REGISTRY")
        static ResourceKey motives() {
            return unimplementedReturn();
        }

        @BridgeField("CUSTOM_STAT_REGISTRY")
        static ResourceKey customStats() {
            return unimplementedReturn();
        }

        @BridgeField("CHUNK_STATUS_REGISTRY")
        static ResourceKey chunkStatuses() {
            return unimplementedReturn();
        }

        @BridgeField("RULE_TEST_REGISTRY")
        static ResourceKey ruleTests() {
            return unimplementedReturn();
        }

        @BridgeField("POS_RULE_TEST_REGISTRY")
        static ResourceKey posRuleTests() {
            return unimplementedReturn();
        }

        @BridgeField("MENU_REGISTRY")
        static ResourceKey menus() {
            return unimplementedReturn();
        }

        @BridgeField("RECIPE_TYPE_REGISTRY")
        static ResourceKey recipeTypes() {
            return unimplementedReturn();
        }

        @BridgeField("RECIPE_SERIALIZER_REGISTRY")
        static ResourceKey recipeSerializers() {
            return unimplementedReturn();
        }

        @BridgeField("ATTRIBUTE_REGISTRY")
        static ResourceKey attributes() {
            return unimplementedReturn();
        }

        @BridgeField("STAT_TYPE_REGISTRY")
        static ResourceKey statTypes() {
            return unimplementedReturn();
        }

        @BridgeField("VILLAGER_TYPE_REGISTRY")
        static ResourceKey villagerTypes() {
            return unimplementedReturn();
        }

        @BridgeField("VILLAGER_PROFESSION_REGISTRY")
        static ResourceKey villagerProfessions() {
            return unimplementedReturn();
        }

        @BridgeField("POINT_OF_INTEREST_TYPE_REGISTRY")
        static ResourceKey pointOfInterestTypes() {
            return unimplementedReturn();
        }

        @BridgeField("MEMORY_MODULE_TYPE_REGISTRY")
        static ResourceKey memoryModuleTypes() {
            return unimplementedReturn();
        }

        @BridgeField("SENSOR_TYPE_REGISTRY")
        static ResourceKey sensorTypes() {
            return unimplementedReturn();
        }

        @BridgeField("SCHEDULE_REGISTRY")
        static ResourceKey schedules() {
            return unimplementedReturn();
        }

        @BridgeField("ACTIVITY_REGISTRY")
        static ResourceKey activities() {
            return unimplementedReturn();
        }

        @BridgeField("LOOT_ENTRY_REGISTRY")
        static ResourceKey lootEntries() {
            return unimplementedReturn();
        }

        @BridgeField("LOOT_FUNCTION_REGISTRY")
        static ResourceKey lootFunctions() {
            return unimplementedReturn();
        }

        @BridgeField("LOOT_ITEM_REGISTRY")
        static ResourceKey lootItems() {
            return unimplementedReturn();
        }

        @BridgeField("DIMENSION_TYPE_REGISTRY")
        static ResourceKey dimensionTypes() {
            return unimplementedReturn();
        }

        @BridgeField("DIMENSION_REGISTRY")
        static ResourceKey dimensions() {
            return unimplementedReturn();
        }

        @BridgeField("LEVEL_STEM_REGISTRY")
        static ResourceKey levelStems() {
            return unimplementedReturn();
        }

        @BridgeField("NOISE_GENERATOR_SETTINGS_REGISTRY")
        static ResourceKey noiseGeneratorSettings() {
            return unimplementedReturn();
        }

        @BridgeField("CONFIGURED_SURFACE_BUILDER_REGISTRY")
        static ResourceKey configuredSurfaceBuilders() {
            return unimplementedReturn();
        }

        @BridgeField("CONFIGURED_CARVER_REGISTRY")
        static ResourceKey configuredCarvers() {
            return unimplementedReturn();
        }

        @BridgeField("CONFIGURED_FEATURE_REGISTRY")
        static ResourceKey configuredFeatures() {
            return unimplementedReturn();
        }

        @BridgeField("CONFIGURED_STRUCTURE_FEATURE_REGISTRY")
        static ResourceKey configuredStructureFeatures() {
            return unimplementedReturn();
        }

        @BridgeField("PROCESSOR_LIST_REGISTRY")
        static ResourceKey processorLists() {
            return unimplementedReturn();
        }

        @BridgeField("TEMPLATE_POOL_REGISTRY")
        static ResourceKey templatePools() {
            return unimplementedReturn();
        }

        @BridgeField("BIOME_REGISTRY")
        static ResourceKey biomes() {
            return unimplementedReturn();
        }

        @BridgeField("SURFACE_BUILDER_REGISTRY")
        static ResourceKey surfaceBuilders() {
            return unimplementedReturn();
        }

        @BridgeField("BLOCK_STATE_PROVIDER_TYPE_REGISTRY")
        static ResourceKey blockStateProviderTypes() {
            return unimplementedReturn();
        }

        @BridgeField("BLOCK_PLACER_TYPE_REGISTRY")
        static ResourceKey blockPlacerTypes() {
            return unimplementedReturn();
        }

        @BridgeField("FOLIAGE_PLACER_TYPE_REGISTRY")
        static ResourceKey foliagePlacerTypes() {
            return unimplementedReturn();
        }

        @BridgeField("TRUNK_PLACER_TYPE_REGISTRY")
        static ResourceKey trunkPlacerTypes() {
            return unimplementedReturn();
        }

        @BridgeField("TREE_DECORATOR_TYPE_REGISTRY")
        static ResourceKey treeDecoratorTypes() {
            return unimplementedReturn();
        }

        @BridgeField("FEATURE_SIZE_TYPE_REGISTRY")
        static ResourceKey featureSizeTypes() {
            return unimplementedReturn();
        }

        @BridgeField("BIOME_SOURCE_REGISTRY")
        static ResourceKey biomeSources() {
            return unimplementedReturn();
        }

        @BridgeField("CHUNK_GENERATOR_REGISTRY")
        static ResourceKey chunkGenerators() {
            return unimplementedReturn();
        }

        @BridgeField("STRUCTURE_PROCESSOR_REGISTRY")
        static ResourceKey structureProcessors() {
            return unimplementedReturn();
        }

        @BridgeField("STRUCTURE_POOL_ELEMENT_REGISTRY")
        static ResourceKey structurePoolElements() {
            return unimplementedReturn();
        }
    }

    @Bridge("net.minecraft.resources.ResourceKey")
    interface ResourceKey {
    }
}
