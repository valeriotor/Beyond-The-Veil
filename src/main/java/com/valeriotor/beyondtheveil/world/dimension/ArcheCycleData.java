package com.valeriotor.beyondtheveil.world.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashSet;
import java.util.Set;

public class ArcheCycleData extends SavedData {

    public static final long TICKS_PER_CYCLE = 16383;
    public static final long CURRENT_DURATION = 20 * 178;
    public static final long CURRENT_START = TICKS_PER_CYCLE - CURRENT_DURATION;
    public static final int CURRENT_PEAK = 110 * 20;
    private long cycle = 0;

    public static ArcheCycleData getInstance(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(ArcheCycleData::new, ArcheCycleData::new, "arche");
    }


    public ArcheCycleData() {
    }

    public ArcheCycleData(CompoundTag tag) {
        cycle = tag.getLong("cycle");
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putLong("cycle", cycle);
        return tag;
    }

    public void tick(boolean dirty) {
        //cycle = CURRENT_START - 200;
        cycle++;
        if (dirty) {
            setDirty();
        }
    }

    public long getCycle() {
        return cycle;
    }

    public long getModuloTicks() {
        return cycle & TICKS_PER_CYCLE;
    }

    public long ticksInCycle() {
        long ticks = cycle; //mc.levelRenderer.getTicks();

        ticks &= TICKS_PER_CYCLE;
        if (ticks < CURRENT_START) {
            return -1;
        }
        return ticks - CURRENT_START;
    }

    public float getCurrentIntensity() {
        long ticks = ticksInCycle();
        if (ticks <= 0) {
            return 0;
        }
        return ticks <= CURRENT_PEAK - 1 ? (ticks % (CURRENT_PEAK)) / (float) (CURRENT_PEAK) : (CURRENT_DURATION - ticks) / (float) (CURRENT_DURATION - CURRENT_PEAK);
    }
}
