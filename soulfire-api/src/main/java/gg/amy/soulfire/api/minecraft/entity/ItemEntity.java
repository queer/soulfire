package gg.amy.soulfire.api.minecraft.entity;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.api.minecraft.item.ItemStack;
import gg.amy.soulfire.api.minecraft.world.World;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/30/21.
 */
@Bridge("net.minecraft.world.entity.item.ItemEntity")
public interface ItemEntity extends Entity {
    @BridgeMethod("<init>(net.minecraft.world.level.Level,double,double,double,net.minecraft.world.item.ItemStack)")
    static ItemEntity create(@Nonnull final World world, final double x, final double y, final double z,
                             @Nonnull final ItemStack stack) {
        return unimplemented();
    }
}
