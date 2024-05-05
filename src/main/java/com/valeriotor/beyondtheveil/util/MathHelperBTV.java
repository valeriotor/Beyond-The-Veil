package com.valeriotor.beyondtheveil.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.AABB;

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

    public static AABB surroundingChunks(BlockPos center, int yDiffDown, int yDiffUp, int chunkRadius) {
        ChunkPos chunkPos = new ChunkPos(center);
        ChunkPos chunkPosNW = new ChunkPos(chunkPos.x - chunkRadius, chunkPos.z - chunkRadius);
        ChunkPos chunkPosSE = new ChunkPos(chunkPos.x + chunkRadius, chunkPos.z + chunkRadius);
        return new AABB(chunkPosNW.getMinBlockX(), center.getY() - yDiffDown, chunkPosNW.getMinBlockZ(), chunkPosSE.getMaxBlockX(), center.getY() + yDiffUp, chunkPosSE.getMaxBlockZ());
    }

}
