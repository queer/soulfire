package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

/**
 * @author amy
 * @since 5/23/21.
 */
@Bridge("net.minecraft.world.item.Tier")
public interface Tier {
    @BridgeMethod("getUses()")
    int uses();

    @BridgeMethod("getSpeed()")
    float speed();

    @BridgeMethod("getAttackDamageBonus()")
    float attackDamageBonus();

    @BridgeMethod("getLevel()")
    int level();

    @BridgeMethod("getEnchantmentValue()")
    int enchantmentLevel();

    @BridgeMethod("getRepairIngredient()")
    Ingredient repairIngredient();
}
