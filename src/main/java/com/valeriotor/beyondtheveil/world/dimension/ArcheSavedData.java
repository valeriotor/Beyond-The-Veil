package com.valeriotor.beyondtheveil.world.dimension;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class ArcheSavedData extends SavedData {

    private long cycle = 0;

    public ArcheSavedData() {
    }

    public ArcheSavedData(CompoundTag tag) {
        cycle = tag.getLong("cycle");
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putLong("cycle", cycle);
        return tag;
    }

    public void tick(boolean dirty) {
        cycle++;
        if (dirty) {
            setDirty();
        }
    }

    public long getCycle() {
        return cycle;
    }
}
