package com.valeriotor.beyondtheveil.dreaming.dreams;

import net.minecraft.nbt.CompoundTag;

public abstract class Reminiscence {

    public Reminiscence() {
    }

    public abstract CompoundTag save();
    public abstract void load(CompoundTag tag);

}
