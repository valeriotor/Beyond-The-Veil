package com.valeriotor.beyondtheveil.surgery.arsenal;

import net.minecraft.nbt.CompoundTag;

public class Burst {

    private final BurstType burstType;
    private final int extension;
    // TODO other modifiers?

    public Burst(CompoundTag tag) {
        burstType = BurstRegistry.REGISTRY.get(tag.getString("type"));
        extension = tag.getInt("extension");
    }

    public Burst(BurstType burstType, int extension) {
        this.burstType = burstType;
        this.extension = extension;
    }

    public CompoundTag writeToNBT(CompoundTag tag) {
        tag.putString("type", burstType.getName());
        tag.putInt("extension", extension);
        return tag;
    }

}
