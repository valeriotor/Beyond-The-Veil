package com.valeriotor.beyondtheveil.rituals;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

import java.util.List;

public class RitualStatus {

    public static final double STEP_SIZE = 0.05; // per tick

    private int primaryInstability;
    private int secondaryInstability;
    private int currentHop;
    private double progressUntilNextHop; // In block distance

    public static RitualStatus startRitual(Level level, List<BlockPos> altars) {

        return null;
    }

    private RitualStatus(Level level, List<BlockPos> altars) {

    }

    public RitualStatus(CompoundTag tag) {
    }

    public void tick() {
        // validity checks
        progressUntilNextHop += STEP_SIZE;
    }

    public CompoundTag saveToNBT(CompoundTag tag) {

        return tag;
    }


}
