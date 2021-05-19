package gg.amy.soulfire.api.minecraft.registry;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.annotations.Nontransforming;
import gg.amy.soulfire.api.minecraft.block.Block;
import gg.amy.soulfire.api.minecraft.item.Item;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/17/21.
 */
@SuppressWarnings("unused")
@Nontransforming
@Bridge("net.minecraft.core.Registry")
public interface Registries {
    // TODO: Apply types to literally ALL of these

    @BridgeField("SOUND_EVENT")
    static Registry soundEvents() {
        return unimplemented();
    }

    @BridgeField("FLUID")
    static DefaultedRegistry fluids() {
        return unimplemented();
    }

    @BridgeField("MOB_EFFECT")
    static Registry mobEffects() {
        return unimplemented();
    }

    @BridgeField("BLOCK")
    static DefaultedRegistry<Block> blocks() {
        return unimplemented();
    }

    @BridgeField("ENCHANTMENT")
    static Registry enchantments() {
        return unimplemented();
    }

    @BridgeField("ENTITY_TYPE")
    static DefaultedRegistry entityTypes() {
        return unimplemented();
    }

    @BridgeField("ITEM")
    static DefaultedRegistry<Item> items() {
        return unimplemented();
    }

    @BridgeField("POTION")
    static DefaultedRegistry potions() {
        return unimplemented();
    }

    @BridgeField("PARTICLE_TYPE")
    static Registry particleTypes() {
        return unimplemented();
    }

    @BridgeField("BLOCK_ENTITY_TYPE")
    static Registry blockEntityTypes() {
        return unimplemented();
    }

    @BridgeField("MOTIVE")
    static DefaultedRegistry motives() {
        return unimplemented();
    }

    @BridgeField("CUSTOM_STAT")
    static Registry customStats() {
        return unimplemented();
    }

    @BridgeField("CHUNK_STATUS")
    static DefaultedRegistry chunkStatuses() {
        return unimplemented();
    }

    @BridgeField("RULE_TEST")
    static Registry ruleTests() {
        return unimplemented();
    }

    @BridgeField("POS_RULE_TEST")
    static Registry posRuleTests() {
        return unimplemented();
    }

    @BridgeField("MENU")
    static Registry menus() {
        return unimplemented();
    }

    @BridgeField("RECIPE_TYPE")
    static Registry recipeTypes() {
        return unimplemented();
    }

    @BridgeField("RECIPE_SERIALIZER")
    static Registry recipeSerializers() {
        return unimplemented();
    }

    @BridgeField("ATTRIBUTE")
    static Registry attributes() {
        return unimplemented();
    }

    @BridgeField("STAT_TYPE")
    static Registry statTypes() {
        return unimplemented();
    }

    @BridgeField("VILLAGER_TYPE")
    static DefaultedRegistry villagerTypes() {
        return unimplemented();
    }

    @BridgeField("VILLAGER_PROFESSION")
    static DefaultedRegistry villagerProfessions() {
        return unimplemented();
    }

    @BridgeField("POINT_OF_INTEREST_TYPE")
    static DefaultedRegistry pointOfInterestTypes() {
        return unimplemented();
    }

    @BridgeField("MEMORY_MODULE_TYPE")
    static DefaultedRegistry memoryModuleTypes() {
        return unimplemented();
    }

    @BridgeField("SENSOR_TYPE")
    static DefaultedRegistry sensorTypes() {
        return unimplemented();
    }

    @BridgeField("SCHEDULE")
    static Registry schedules() {
        return unimplemented();
    }

    @BridgeField("ACTIVITY")
    static Registry activities() {
        return unimplemented();
    }

    @BridgeField("LOOT_POOL_ENTRY_TYPE")
    static Registry lootEntries() {
        return unimplemented();
    }

    @BridgeField("LOOT_FUNCTION_TYPE")
    static Registry lootFunctions() {
        return unimplemented();
    }

//    TODO: What are these four?
//    @BridgeField("LOOT_CONDITION_TYPE")
//    static Registry lootItems() {
//        return unimplementedReturn();
//    }
//
//    @BridgeField("")
//    static Registry dimensionTypes() {
//        return unimplementedReturn();
//    }
//
//    @BridgeField("")
//    static Registry dimensions() {
//        return unimplementedReturn();
//    }
//
//    @BridgeField("")
//    static Registry levelStems() {
//        return unimplementedReturn();
//    }

    @BridgeField("NOISE_GENERATOR_SETTINGS_REGISTRY")
    static Registry noiseGeneratorSettings() {
        return unimplemented();
    }

    @BridgeField("CONFIGURED_SURFACE_BUILDER_REGISTRY")
    static Registry configuredSurfaceBuilders() {
        return unimplemented();
    }

    @BridgeField("CONFIGURED_CARVER_REGISTRY")
    static Registry configuredCarvers() {
        return unimplemented();
    }

    @BridgeField("CONFIGURED_FEATURE_REGISTRY")
    static Registry configuredFeatures() {
        return unimplemented();
    }

    @BridgeField("CONFIGURED_STRUCTURE_FEATURE_REGISTRY")
    static Registry configuredStructureFeatures() {
        return unimplemented();
    }

    @BridgeField("PROCESSOR_LIST_REGISTRY")
    static Registry processorLists() {
        return unimplemented();
    }

    @BridgeField("TEMPLATE_POOL_REGISTRY")
    static Registry templatePools() {
        return unimplemented();
    }

    @BridgeField("BIOME_REGISTRY")
    static Registry biomes() {
        return unimplemented();
    }

    @BridgeField("SURFACE_BUILDER")
    static Registry surfaceBuilders() {
        return unimplemented();
    }

    @BridgeField("BLOCKSTATE_PROVIDER_TYPES")
    static Registry blockStateProviderTypes() {
        return unimplemented();
    }

    @BridgeField("BLOCK_PLACER_TYPES")
    static Registry blockPlacerTypes() {
        return unimplemented();
    }

    @BridgeField("FOLIAGE_PLACER_TYPES")
    static Registry foliagePlacerTypes() {
        return unimplemented();
    }

    @BridgeField("TRUNK_PLACER_TYPES")
    static Registry trunkPlacerTypes() {
        return unimplemented();
    }

    @BridgeField("TREE_DECORATOR_TYPES")
    static Registry treeDecoratorTypes() {
        return unimplemented();
    }

    @BridgeField("FEATURE_SIZE_TYPES")
    static Registry featureSizeTypes() {
        return unimplemented();
    }

    @BridgeField("BIOME_SOURCE")
    static Registry biomeSources() {
        return unimplemented();
    }

    @BridgeField("CHUNK_GENERATOR")
    static Registry chunkGenerators() {
        return unimplemented();
    }

    @BridgeField("STRUCTURE_PROCESSOR")
    static Registry structureProcessors() {
        return unimplemented();
    }

    @BridgeField("STRUCTURE_POOL_ELEMENT")
    static Registry structurePoolElements() {
        return unimplemented();
    }

    @Nontransforming
    @Bridge("net.minecraft.core.Registry")
    interface ResourceKeys {
        @BridgeField("SOUND_EVENT_REGISTRY")
        static ResourceKey soundEvents() {
            return unimplemented();
        }

        @BridgeField("FLUID_REGISTRY")
        static ResourceKey fluids() {
            return unimplemented();
        }

        @BridgeField("MOB_EFFECT_REGISTRY")
        static ResourceKey mobEffects() {
            return unimplemented();
        }

        @BridgeField("BLOCK_REGISTRY")
        static ResourceKey blocks() {
            return unimplemented();
        }

        @BridgeField("ENCHANTMENT_REGISTRY")
        static ResourceKey enchantments() {
            return unimplemented();
        }

        @BridgeField("ENTITY_TYPE_REGISTRY")
        static ResourceKey entityTypes() {
            return unimplemented();
        }

        @BridgeField("ITEM_REGISTRY")
        static ResourceKey items() {
            return unimplemented();
        }

        @BridgeField("POTION_REGISTRY")
        static ResourceKey potions() {
            return unimplemented();
        }

        @BridgeField("PARTICLE_TYPE_REGISTRY")
        static ResourceKey particleTypes() {
            return unimplemented();
        }

        @BridgeField("BLOCK_ENTITY_TYPE_REGISTRY")
        static ResourceKey blockEntityTypes() {
            return unimplemented();
        }

        @BridgeField("MOTIVE_REGISTRY")
        static ResourceKey motives() {
            return unimplemented();
        }

        @BridgeField("CUSTOM_STAT_REGISTRY")
        static ResourceKey customStats() {
            return unimplemented();
        }

        @BridgeField("CHUNK_STATUS_REGISTRY")
        static ResourceKey chunkStatuses() {
            return unimplemented();
        }

        @BridgeField("RULE_TEST_REGISTRY")
        static ResourceKey ruleTests() {
            return unimplemented();
        }

        @BridgeField("POS_RULE_TEST_REGISTRY")
        static ResourceKey posRuleTests() {
            return unimplemented();
        }

        @BridgeField("MENU_REGISTRY")
        static ResourceKey menus() {
            return unimplemented();
        }

        @BridgeField("RECIPE_TYPE_REGISTRY")
        static ResourceKey recipeTypes() {
            return unimplemented();
        }

        @BridgeField("RECIPE_SERIALIZER_REGISTRY")
        static ResourceKey recipeSerializers() {
            return unimplemented();
        }

        @BridgeField("ATTRIBUTE_REGISTRY")
        static ResourceKey attributes() {
            return unimplemented();
        }

        @BridgeField("STAT_TYPE_REGISTRY")
        static ResourceKey statTypes() {
            return unimplemented();
        }

        @BridgeField("VILLAGER_TYPE_REGISTRY")
        static ResourceKey villagerTypes() {
            return unimplemented();
        }

        @BridgeField("VILLAGER_PROFESSION_REGISTRY")
        static ResourceKey villagerProfessions() {
            return unimplemented();
        }

        @BridgeField("POINT_OF_INTEREST_TYPE_REGISTRY")
        static ResourceKey pointOfInterestTypes() {
            return unimplemented();
        }

        @BridgeField("MEMORY_MODULE_TYPE_REGISTRY")
        static ResourceKey memoryModuleTypes() {
            return unimplemented();
        }

        @BridgeField("SENSOR_TYPE_REGISTRY")
        static ResourceKey sensorTypes() {
            return unimplemented();
        }

        @BridgeField("SCHEDULE_REGISTRY")
        static ResourceKey schedules() {
            return unimplemented();
        }

        @BridgeField("ACTIVITY_REGISTRY")
        static ResourceKey activities() {
            return unimplemented();
        }

        @BridgeField("LOOT_ENTRY_REGISTRY")
        static ResourceKey lootEntries() {
            return unimplemented();
        }

        @BridgeField("LOOT_FUNCTION_REGISTRY")
        static ResourceKey lootFunctions() {
            return unimplemented();
        }

        @BridgeField("LOOT_ITEM_REGISTRY")
        static ResourceKey lootItems() {
            return unimplemented();
        }

        @BridgeField("DIMENSION_TYPE_REGISTRY")
        static ResourceKey dimensionTypes() {
            return unimplemented();
        }

        @BridgeField("DIMENSION_REGISTRY")
        static ResourceKey dimensions() {
            return unimplemented();
        }

        @BridgeField("LEVEL_STEM_REGISTRY")
        static ResourceKey levelStems() {
            return unimplemented();
        }

        @BridgeField("NOISE_GENERATOR_SETTINGS_REGISTRY")
        static ResourceKey noiseGeneratorSettings() {
            return unimplemented();
        }

        @BridgeField("CONFIGURED_SURFACE_BUILDER_REGISTRY")
        static ResourceKey configuredSurfaceBuilders() {
            return unimplemented();
        }

        @BridgeField("CONFIGURED_CARVER_REGISTRY")
        static ResourceKey configuredCarvers() {
            return unimplemented();
        }

        @BridgeField("CONFIGURED_FEATURE_REGISTRY")
        static ResourceKey configuredFeatures() {
            return unimplemented();
        }

        @BridgeField("CONFIGURED_STRUCTURE_FEATURE_REGISTRY")
        static ResourceKey configuredStructureFeatures() {
            return unimplemented();
        }

        @BridgeField("PROCESSOR_LIST_REGISTRY")
        static ResourceKey processorLists() {
            return unimplemented();
        }

        @BridgeField("TEMPLATE_POOL_REGISTRY")
        static ResourceKey templatePools() {
            return unimplemented();
        }

        @BridgeField("BIOME_REGISTRY")
        static ResourceKey biomes() {
            return unimplemented();
        }

        @BridgeField("SURFACE_BUILDER_REGISTRY")
        static ResourceKey surfaceBuilders() {
            return unimplemented();
        }

        @BridgeField("BLOCK_STATE_PROVIDER_TYPE_REGISTRY")
        static ResourceKey blockStateProviderTypes() {
            return unimplemented();
        }

        @BridgeField("BLOCK_PLACER_TYPE_REGISTRY")
        static ResourceKey blockPlacerTypes() {
            return unimplemented();
        }

        @BridgeField("FOLIAGE_PLACER_TYPE_REGISTRY")
        static ResourceKey foliagePlacerTypes() {
            return unimplemented();
        }

        @BridgeField("TRUNK_PLACER_TYPE_REGISTRY")
        static ResourceKey trunkPlacerTypes() {
            return unimplemented();
        }

        @BridgeField("TREE_DECORATOR_TYPE_REGISTRY")
        static ResourceKey treeDecoratorTypes() {
            return unimplemented();
        }

        @BridgeField("FEATURE_SIZE_TYPE_REGISTRY")
        static ResourceKey featureSizeTypes() {
            return unimplemented();
        }

        @BridgeField("BIOME_SOURCE_REGISTRY")
        static ResourceKey biomeSources() {
            return unimplemented();
        }

        @BridgeField("CHUNK_GENERATOR_REGISTRY")
        static ResourceKey chunkGenerators() {
            return unimplemented();
        }

        @BridgeField("STRUCTURE_PROCESSOR_REGISTRY")
        static ResourceKey structureProcessors() {
            return unimplemented();
        }

        @BridgeField("STRUCTURE_POOL_ELEMENT_REGISTRY")
        static ResourceKey structurePoolElements() {
            return unimplemented();
        }
    }

    @Bridge("net.minecraft.resources.ResourceKey")
    interface ResourceKey {
        @Nonnull
        @BridgeMethod("<init>(net.minecraft.resources.ResourceLocation,net.minecraft.resources.ResourceLocation)")
        static ResourceKey of(@Nonnull final ResourceLocation registryLocation, @Nonnull final ResourceLocation thingLocation) {
            return unimplemented();
        }

        @Nonnull
        @BridgeMethod("createRegistryKey(net.minecraft.resources.ResourceLocation)")
        static ResourceKey createRegistryKey(@Nonnull final ResourceLocation location) {
            return unimplemented();
        }

        @Nonnull
        @BridgeMethod("create(net.minecraft.resources.ResourceKey,net.minecraft.resources.ResourceLocation)")
        static ResourceKey of(@Nonnull final ResourceKey key, @Nonnull final ResourceLocation location) {
            return unimplemented();
        }
    }

    @Bridge("net.minecraft.resources.ResourceLocation")
    interface ResourceLocation {
//        @BridgeMethod("<init>(java.lang.String[])")
        static ResourceLocation of(@Nonnull final String[] parts) {
            return unimplemented();
        }

        @BridgeMethod("<init>(java.lang.String)")
        static ResourceLocation of(@Nonnull final String id) {
            return unimplemented();
        }

        @BridgeMethod("<init>(java.lang.String,java.lang.String)")
        static ResourceLocation of(@Nonnull final String namespace, @Nonnull final String id) {
            return unimplemented();
        }

        @BridgeMethod("getNamespace()")
        String namespace();

        @BridgeMethod("getPath()")
        String path();
    }
}
