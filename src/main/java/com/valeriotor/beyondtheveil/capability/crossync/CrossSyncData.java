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

    public void copyToNewStore(@NotNull CrossSyncData newStore, boolean died) {
        if (died) {
            newStore.loadFromNBT(crossSync.saveToNBTForRespawn(new CompoundTag()));
        } else {
            newStore.loadFromNBT(crossSync.saveToNBT(new CompoundTag()));
        }
    }
}

