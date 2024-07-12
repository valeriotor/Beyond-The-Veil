package com.valeriotor.beyondtheveil.capability.crossync;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class CrossSyncData {

    private final CrossSync crossSync = new CrossSync();

    public CrossSync getCrossSync() {
        return crossSync;
    }

    public void loadFromNBT(CompoundTag compoundTag) {
        crossSync.loadFromNBT(compoundTag);
    }

    public void saveToNBT(CompoundTag compoundTag) {
        crossSync.saveToNBT(compoundTag);
    }

    public void copyToNewStore(@NotNull CrossSyncData newStore) {
        CompoundTag tag = new CompoundTag();
        saveToNBT(tag);
        newStore.loadFromNBT(tag);
    }
}

