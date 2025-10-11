package com.valeriotor.beyondtheveil.capability.surgery;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.surgery.PatientCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ConvalescentData {

    public static ConvalescentData of(PatientCondition condition, Map<String, Integer> flags, TriggerData triggerData, int capacity, int usedCapacity) {
        ConvalescentData data = new ConvalescentData();
        data.setFlags(flags);
        data.setCondition(condition);
        data.setTriggerData(triggerData);
        data.setCapacity(capacity);
        data.setUsedCapacity(usedCapacity);
        return data;
    }

    private PatientCondition condition = PatientCondition.STABLE;
    private final Map<String, Integer> flags = new HashMap<>(); // the int value stands for how many times it was applied in the procedure
    private final Map<String, Integer> counters = new HashMap<>(); // populated lazily. Keys are the flags, values are any integer counter that may be of use
    private TriggerData triggerData;
    private int capacity;
    private int usedCapacity;
    private int collectedXP = 0;
    private BlockPos chestPos;
    private ItemStack heldStack = ItemStack.EMPTY;

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

    public void setUsedCapacity(int usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public int getUsedCapacity() {
        return usedCapacity;
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

    public int getCounter(String key) {
        return counters.getOrDefault(key, 0);
    }

    public void setCounter(String key, int value) {
        counters.put(key, value);
    }

    public Map<String, Integer> getFlags() {
        return flags;
    }

    public int getCollectedXP() {
        return collectedXP;
    }

    public void addXP(int amount) {
        collectedXP += amount;
    }

    public int takeXP() {
        int taken = Math.min(1000, collectedXP);
        collectedXP -= taken;
        return taken;
    }

    public void setChestPos(BlockPos chestPos) {
        this.chestPos = chestPos;
    }

    public BlockPos getChestPos() {
        return chestPos;
    }

    public void setHeldStack(ItemStack heldStack) {
        this.heldStack = heldStack;
    }

    public ItemStack getHeldStack() {
        return heldStack;
    }

    public void tick(LivingEntity entity) {
        counters.replaceAll((k, v) -> Math.max(0, v - 1));
        if (flags.containsKey("great_heart")) {
            if (entity.tickCount % 40 == 0) {
                entity.heal(1);
            }
        }
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
        tag.putInt("usedCapacity", usedCapacity);
        if (chestPos != null) {
            tag.putLong("chestPos", chestPos.asLong());
        }
        tag.put("heldStack", heldStack.save(new CompoundTag()));
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
        usedCapacity = tag.getInt("usedCapacity");
        if (tag.contains("chestPos")) {
            chestPos = BlockPos.of(tag.getLong("chestPos"));
        }
        if (tag.contains("heldStack")) {
            heldStack = ItemStack.of(tag.getCompound("heldStack"));
        }
    }
}
