package com.valeriotor.beyondtheveil.dreaming.dreams;

import net.minecraft.nbt.CompoundTag;

public class ReminiscenceAnimal extends Reminiscence {

    private String entityKey;

    public ReminiscenceAnimal(String entityKey) {
        this.entityKey = entityKey;
    }


    @Override
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("entityKey", entityKey);
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        entityKey = tag.getString("entityKey");
    }

    public String getEntityKey() {
        return entityKey;
    }
}
