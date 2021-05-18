package gg.amy.soulfire.bytecode.redefiners;

import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceKeys;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Redefiner;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;

/**
 * @author amy
 * @since 5/17/21.
 */
public class ResourceKeysRedefiner extends Redefiner {
    private final MappedClass registry = ClassMap.lookup("net.minecraft.core.Registry");

    public ResourceKeysRedefiner() {
        super(ResourceKeys.class.getName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        for(final var method : cn.methods) {
            final var insns = new InsnList();

            final var fieldName = switch(method.name) {
                case "soundEvents" -> "SOUND_EVENT_REGISTRY";
                case "fluids" -> "FLUID_REGISTRY";
                case "mobEffects" -> "MOB_EFFECT_REGISTRY";
                case "blocks" -> "BLOCK_REGISTRY";
                case "enchantments" -> "ENCHANTMENT_REGISTRY";
                case "entityTypes" -> "ENTITY_TYPE_REGISTRY";
                case "items" -> "ITEM_REGISTRY";
                case "potions" -> "POTION_REGISTRY";
                case "particleTypes" -> "PARTICLE_TYPE_REGISTRY";
                case "blockEntityTypes" -> "BLOCK_ENTITY_TYPE_REGISTRY";
                case "motives" -> "MOTIVE_REGISTRY";
                case "customStats" -> "CUSTOM_STAT_REGISTRY";
                case "chunkStatuses" -> "CHUNK_STATUS_REGISTRY";
                case "ruleTests" -> "RULE_TEST_REGISTRY";
                case "posRuleTests" -> "POS_RULE_TEST_REGISTRY";
                case "menus" -> "MENU_REGISTRY";
                case "recipeTypes" -> "RECIPE_TYPE_REGISTRY";
                case "recipeSerializers" -> "RECIPE_SERIALIZER_REGISTRY";
                case "attributes" -> "ATTRIBUTE_REGISTRY";
                case "statTypes" -> "STAT_TYPE_REGISTRY";
                case "villagerTypes" -> "VILLAGER_TYPE_REGISTRY";
                case "villagerProfessions" -> "VILLAGER_PROFESSION_REGISTRY";
                case "pointOfInterestTypes" -> "POINT_OF_INTEREST_TYPE_REGISTRY";
                case "memoryModuleTypes" -> "MEMORY_MODULE_TYPE_REGISTRY";
                case "sensorTypes" -> "SENSOR_TYPE_REGISTRY";
                case "schedules" -> "SCHEDULE_REGISTRY";
                case "activities" -> "ACTIVITY_REGISTRY";
                case "lootEntries" -> "LOOT_ENTRY_REGISTRY";
                case "lootFunctions" -> "LOOT_FUNCTION_REGISTRY";
                case "lootItems" -> "LOOT_ITEM_REGISTRY";
                case "dimensionTypes" -> "DIMENSION_TYPE_REGISTRY";
                case "dimensions" -> "DIMENSION_REGISTRY";
                case "levelStems" -> "LEVEL_STEM_REGISTRY";
                case "noiseGeneratorSettings" -> "NOISE_GENERATOR_SETTINGS_REGISTRY";
                case "configuredSurfaceBuilders" -> "CONFIGURED_SURFACE_BUILDER_REGISTRY";
                case "configuredCarvers" -> "CONFIGURED_CARVER_REGISTRY";
                case "configuredFeatures" -> "CONFIGURED_FEATURE_REGISTRY";
                case "configuredStructureFeatures" -> "CONFIGURED_STRUCTURE_FEATURE_REGISTRY";
                case "processorLists" -> "PROCESSOR_LIST_REGISTRY";
                case "templatePools" -> "TEMPLATE_POOL_REGISTRY";
                case "biomes" -> "BIOME_REGISTRY";
                case "surfaceBuilders" -> "SURFACE_BUILDER_REGISTRY";
                case "blockStateProviderTypes" -> "BLOCK_STATE_PROVIDER_TYPE_REGISTRY";
                case "blockPlacerTypes" -> "BLOCK_PLACER_TYPE_REGISTRY";
                case "foliagePlacerTypes" -> "FOLIAGE_PLACER_TYPE_REGISTRY";
                case "trunkPlacerTypes" -> "TRUNK_PLACER_TYPE_REGISTRY";
                case "treeDecoratorTypes" -> "TREE_DECORATOR_TYPE_REGISTRY";
                case "featureSizeTypes" -> "FEATURE_SIZE_TYPE_REGISTRY";
                case "biomeSources" -> "BIOME_SOURCE_REGISTRY";
                case "chunkGenerators" -> "CHUNK_GENERATOR_REGISTRY";
                case "structureProcessors" -> "STRUCTURE_PROCESSOR_REGISTRY";
                case "structurePoolElements" -> "STRUCTURE_POOL_ELEMENT_REGISTRY";
                default -> throw new IllegalArgumentException("Unknown registry method: " + method.name);
            };
            final var field = registry.field(fieldName);
            insns.add(new FieldInsnNode(GETSTATIC, registry.obfName(), field.obfName(), $$(field.type())));
            insns.add(new InsnNode(ARETURN));
            method.instructions.clear();
            method.instructions.add(insns);
            logger.info("Redefined ResourceKeys#{} -> {}", method.name, $$(field.type()));
        }
    }
}
