package com.valeriotor.beyondtheveil.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

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
        return Math.atan2(-xDiff, zDiff) * 180 / Math.PI;
    }

    public static AABB surroundingChunks(BlockPos center, int yDiffDown, int yDiffUp, int chunkRadius) {
        ChunkPos chunkPos = new ChunkPos(center);
        ChunkPos chunkPosNW = new ChunkPos(chunkPos.x - chunkRadius, chunkPos.z - chunkRadius);
        ChunkPos chunkPosSE = new ChunkPos(chunkPos.x + chunkRadius, chunkPos.z + chunkRadius);
        return new AABB(chunkPosNW.getMinBlockX(), center.getY() - yDiffDown, chunkPosNW.getMinBlockZ(), chunkPosSE.getMaxBlockX(), center.getY() + yDiffUp, chunkPosSE.getMaxBlockZ());
    }

    public static boolean checkForRing(Block type, Level l, BlockPos pos, int radius) {
        if (isCorner(type, l, pos) && checkForRingFromCorner(type, l, pos, radius, pos)) {
            return true;
        }
        for (Direction value : Direction.values()) {
            if (value.getAxis().isVertical()) continue;
            for (int i = 1; i < radius - 1; i++) {
                BlockPos relative = pos.relative(value, i);
                if (l.getBlockState(relative).getBlock() != type) {
                    break;
                }
                if (isCorner(type, l, relative) && checkForRingFromCorner(type, l, relative, radius, pos)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isCorner(Block type, Level l, BlockPos pos) {
        for (Direction value : Direction.values()) {
            if (value.getAxis().isVertical()) continue;
            if (l.getBlockState(pos.relative(value)).getBlock() != type) continue;
            for (Direction value2 : Direction.values()) {
                if (value2 == value.getClockWise() || value2 == value.getCounterClockWise()) {
                    if (l.getBlockState(pos.relative(value2)).getBlock() != type) continue;
                    return true;
                }

            }
        }
        return false;
    }

    public static boolean checkForRingFromCorner(Block type, Level l, BlockPos pos, int radius, BlockPos mustContain) {
        for (Direction value : Direction.values()) {
            if (value.getAxis().isVertical()) continue;
            if (checkRingInDirection(type, l, pos, radius, value, Direction::getClockWise, mustContain) || checkRingInDirection(type, l, pos, radius, value, Direction::getCounterClockWise, mustContain)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkRingInDirection(Block type, Level l, BlockPos pos, int radius, Direction current, Function<Direction, Direction> update, BlockPos mustContain) {
        BlockPos currentPos = pos;
        Set<BlockPos> allPoses = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < radius - 1; j++) {
                currentPos = currentPos.relative(current);
                if (l.getBlockState(currentPos).getBlock() != type) {
                    return false;
                }
                allPoses.add(currentPos);
                if (currentPos.equals(pos)) {
                    if (allPoses.contains(mustContain)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            current = update.apply(current);
        }
        return false;
    }

}
