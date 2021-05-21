package gg.amy.soulfire.api.minecraft.block;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.core.BlockPos")
public interface BlockPos {
    @BridgeMethod("<init>(int,int,int)")
    static BlockPos of(final int x, final int y, final int z) {
        return unimplemented();
    }

    @BridgeMethod("<init>(double,double,double)")
    static BlockPos of(final double x, final double y, final double z) {
        return unimplemented();
    }

    // TODO: These are supposed to take longs as params?
//    @BridgeMethod("getX()")
//    int x();
//
//    @BridgeMethod("getY()")
//    int y();
//
//    @BridgeMethod("getZ()")
//    int z();

    @BridgeMethod("above()")
    BlockPos above();

    @BridgeMethod("above(int)")
    BlockPos above(int amount);

    @BridgeMethod("below()")
    BlockPos below();

    @BridgeMethod("below(int)")
    BlockPos below(int amount);

    @BridgeMethod("north()")
    BlockPos north();

    @BridgeMethod("north(int)")
    BlockPos north(int amount);

    @BridgeMethod("south()")
    BlockPos south();

    @BridgeMethod("south(int)")
    BlockPos south(int amount);

    @BridgeMethod("west()")
    BlockPos west();

    @BridgeMethod("west(int)")
    BlockPos west(int amount);

    @BridgeMethod("east()")
    BlockPos east();

    @BridgeMethod("east(int)")
    BlockPos east(int amount);

    @BridgeMethod("immutable()")
    BlockPos immutable();
}
