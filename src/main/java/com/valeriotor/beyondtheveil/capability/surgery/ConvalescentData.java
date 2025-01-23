package com.valeriotor.beyondtheveil.capability.surgery;

import com.valeriotor.beyondtheveil.surgery.PatientCondition;
import com.valeriotor.beyondtheveil.surgery.arsenal.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConvalescentData {

    public static ConvalescentData of(PatientCondition condition, Map<String, Integer> flags) {
        ConvalescentData data = new ConvalescentData();
        data.setFlags(flags);
        data.setCondition(condition);
        return data;
    }

    private PatientCondition condition = PatientCondition.STABLE;
    private final Map<String, Integer> flags = new HashMap<>(); // the int value stands for how many times it was applied in the procedure
    private final Map<String, Integer> counters = new HashMap<>(); // populated lazily. Keys are the flags, values are any integer counter that may be of use

    public void setCondition(PatientCondition condition) {
        this.condition = condition;
    }

    public PatientCondition getCondition() {
        return condition;
    }

    public void setFlags(Map<String, Integer> flags) {
        this.flags.clear();
        this.flags.putAll(flags);
    }

    public Map<String, Integer> getCounters() {
        return counters;
    }

    public Map<String, Integer> getFlags() {
        return flags;
    }

    public CompoundTag saveToNBT(CompoundTag tag) {
        CompoundTag subTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : flags.entrySet()) {
            CompoundTag entryTag = new CompoundTag();
            entryTag.putInt("value", entry.getValue());
            if (counters.containsKey(entry.getKey())) {
                entryTag.putInt("counter", counters.get(entry.getKey()));
            }
            subTag.put(entry.getKey(), entryTag);
        }
        tag.put("subTag", subTag);
        tag.putString("condition", condition.name());
        return tag;
    }

    public void loadFromNBT(CompoundTag tag) {
        CompoundTag subTag = tag.getCompound("subTag");
        for (String key : subTag.getAllKeys()) {
            CompoundTag entryTag = subTag.getCompound(key);
            flags.put(key, entryTag.getInt("value"));
            if (entryTag.contains("counter")) {
                counters.put(key, entryTag.getInt("counter"));
            }
        }
        condition = PatientCondition.valueOf(tag.getString("condition"));


    }
}
