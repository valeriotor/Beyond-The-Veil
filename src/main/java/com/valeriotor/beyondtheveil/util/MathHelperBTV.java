package com.valeriotor.beyondtheveil.util;

import net.minecraft.world.entity.Entity;

public class MathHelperBTV {

    public static int clamp(int min, int max, int val) {
        return val < min ? min : (val > max ? max : val);
    }

    public static float clamp(float min, float max, float val) {
        return val < min ? min : (val > max ? max : val);
    }

    public static double clamp(double min, double max, double val) {
        return val < min ? min : (val > max ? max : val);
    }

    public static long clamp(long min, long max, long val) {
        return val < min ? min : (val > max ? max : val);
    }

    public static double angleBetween(Entity source, Entity target) {
        return angleBetween(source.xOld, source.zOld, target.xOld, target.zOld);
    }

    public static double angleBetween(double sourceX, double sourceZ, double targetX, double targetZ) {
        double xDiff = targetX - sourceX;
        double zDiff = targetZ - sourceZ;
        return Math.atan2(-xDiff, zDiff)*180/Math.PI;
    }

}
