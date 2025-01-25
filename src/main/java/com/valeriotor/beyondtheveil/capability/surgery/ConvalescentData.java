package com.valeriotor.beyondtheveil.capability.surgery;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.surgery.PatientCondition;
import com.valeriotor.beyondtheveil.surgery.arsenal.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConvalescentData {

    public static ConvalescentData of(PatientCondition condition, Map<String, Integer> flags, TriggerData triggerData, int capacity) {
        ConvalescentData data = new ConvalescentData();
        data.setFlags(flags);
        data.setCondition(condition);
        data.setTriggerData(triggerData);
        data.setCapacity(capacity);
        return data;
    }

    private PatientCondition condition = PatientCondition.STABLE;
    private final Map<String, Integer> flags = new HashMap<>(); // the int value stands for how many times it was applied in the procedure
    private final Map<String, Integer> counters = new HashMap<>(); // populated lazily. Keys are the flags, values are any integer counter that may be of use
    private TriggerData triggerData;
    private int capacity;

    public TriggerData getTriggerData() {
        if (triggerData != null) {
            return triggerData;
        }
        return new TriggerData();
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setTriggerData(TriggerData triggerData) {
        this.triggerData = triggerData;
    }

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
        if (triggerData != null) {
            tag.put("triggerData", triggerData.saveToNBT(new CompoundTag()));
        }
        tag.putInt("capacity", capacity);
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
        if (tag.contains("triggerData")) {
            triggerData = new TriggerData();
            triggerData.loadFromNBT(tag.getCompound("triggerData"));
        }
        capacity = tag.getInt("capacity");
    }
}
