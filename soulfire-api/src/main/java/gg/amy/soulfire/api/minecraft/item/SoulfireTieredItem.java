package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Constructor;
import gg.amy.soulfire.annotations.Shim;
import gg.amy.soulfire.annotations.ShimAfter;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/23/21.
 */
@Shim("net.minecraft.world.item.TieredItem")
@ShimAfter("gg.amy.soulfire.api.minecraft.item.Item")
public class SoulfireTieredItem extends SoulfireItem implements TieredItem {
    @Constructor("<init>(net.minecraft.world.item.Tier,net.minecraft.world.item.Item$Properties)")
    public SoulfireTieredItem(@Nonnull final Tier tier, @Nonnull final ItemProperties properties) {
        super(properties);
    }
}
