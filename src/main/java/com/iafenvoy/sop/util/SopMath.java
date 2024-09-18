package com.iafenvoy.sop.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class SopMath {
    public static Vec3d getRotationVector(float pitch, float yaw) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }

    public static Vec3d getRotationVectorUnit(float pitch, float yaw) {
        return toUnit(getRotationVector(pitch, yaw));
    }

    public static Vec3d toUnit(double x, double y, double z) {
        return toUnit(new Vec3d(x, y, z));
    }

    public static Vec3d toUnit(Vec3d origin) {
        return origin.multiply(1 / origin.length());
    }
}
