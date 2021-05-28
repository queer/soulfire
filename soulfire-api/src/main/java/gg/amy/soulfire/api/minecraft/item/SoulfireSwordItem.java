package gg.amy.soulfire.api.minecraft.item;

import gg.amy.soulfire.annotations.Constructor;
import gg.amy.soulfire.annotations.DumpASM;
import gg.amy.soulfire.annotations.Shim;
import gg.amy.soulfire.annotations.ShimAfter;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/23/21.
 */
@Shim("net.minecraft.world.item.SwordItem")
@ShimAfter("gg.amy.soulfire.api.minecraft.item.SoulfireTieredItem")
public class SoulfireSwordItem extends SoulfireTieredItem implements SwordItem {
    @DumpASM
    @Constructor("<init>(net.minecraft.world.item.Tier,int,float,net.minecraft.world.item.Item$Properties)")
    public SoulfireSwordItem(@Nonnull final Tier tier, final int i, final float f, @Nonnull final ItemProperties properties) {
        super(tier, properties);
    }
}
