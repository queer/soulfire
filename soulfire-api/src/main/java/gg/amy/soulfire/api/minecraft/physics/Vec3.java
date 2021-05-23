package gg.amy.soulfire.api.minecraft.physics;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.BridgeMethod;

import javax.annotation.Nonnull;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/23/21.
 */
@Bridge("net.minecraft.world.phys.Vec3")
public interface Vec3 {
    @Nonnull
    @BridgeField("ZERO")
    static Vec3 zero() {
        return unimplemented();
    }

    @Nonnull
    @BridgeMethod("cross(net.minecraft.world.phys.Vec3)")
    Vec3 cross(@Nonnull Vec3 vec3);

    @Nonnull
    @BridgeMethod("subtract(net.minecraft.world.phys.Vec3)")
    Vec3 subtract(@Nonnull Vec3 vec3);

    @Nonnull
    @BridgeMethod("subtract(double,double,double)")
    Vec3 subtract(double x, double y, double z);

    @Nonnull
    @BridgeMethod("add(net.minecraft.world.phys.Vec3)")
    Vec3 add(@Nonnull Vec3 vec3);

    @Nonnull
    @BridgeMethod("add(double,double,double)")
    Vec3 add(double x, double y, double z);

    @Nonnull
    @BridgeMethod("multiply(net.minecraft.world.phys.Vec3)")
    Vec3 multiply(@Nonnull Vec3 vec3);

    @Nonnull
    @BridgeMethod("multiply(double,double,double)")
    Vec3 multiply(double x, double y, double z);

    @BridgeMethod("x()")
    double x();

    @BridgeMethod("y()")
    double y();

    @BridgeMethod("z()")
    double z();

    @Nonnull
    @BridgeMethod("scale(double)")
    Vec3 scale(double scale);

    @Nonnull
    @BridgeMethod("reverse()")
    Vec3 reverse();
}
