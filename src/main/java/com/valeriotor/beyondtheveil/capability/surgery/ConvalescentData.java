package com.valeriotor.beyondtheveil.capability.surgery;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.entity.WeeperEntity;
import com.valeriotor.beyondtheveil.event.LivingTickEvents;
import com.valeriotor.beyondtheveil.item.BlackjackItem;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.surgery.Operation;
import com.valeriotor.beyondtheveil.surgery.OperationRegistry;
import com.valeriotor.beyondtheveil.surgery.PatientCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

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

    public Item swollenGrowthItem(LivingEntity e) {
        if (counters.getOrDefault("swollen_growth", -1) != 0) {
            return null;
        }
        List<Item> pool = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : flags.entrySet()) {
            if (entry.getValue() > 0) {
                Item item = OperationRegistry.itemForInsertionOperation(entry.getKey());
                if (item != null) {
                    pool.add(item);
                }
            }
        }
        if (!pool.isEmpty()) { // should never be empty thanks to the swollen growth itself
            counters.put("swollen_growth", 20 * 60 * 15);
            flags.put(OperationRegistry.INSERT_SWOLLEN_GROWTH_CHEST.getName(), flags.getOrDefault(OperationRegistry.INSERT_SWOLLEN_GROWTH_CHEST.getName(), 1) + 1);
            if (flags.get(OperationRegistry.INSERT_SWOLLEN_GROWTH_CHEST.getName()) >= 4) {
                e.kill();
                e.setHealth(0);
            }
            return pool.get(e.getRandom().nextInt(pool.size()));
        }
        return null;
    }

    private static final UUID GREAT_HEART_HEALTH = UUID.fromString("8c498269-ccf0-4e93-9fb8-c4a5eda417f8");

    private static final UUID LIVING_IRON_SLOW = UUID.fromString("8c498269-ccf0-4e93-9fb8-c4a5eda417f9");
    public void tick(LivingEntity entity) { // only called server side
        counters.replaceAll((k, v) -> Math.max(0, v - 1));
        if (counters.containsKey("spreading_iron") && counters.get("spreading_iron") == 0) {
            counters.remove("spreading_iron");
            flags.put(OperationRegistry.IRON_SPINE, 1);
            if (entity instanceof Villager villager) {
                BlackjackItem.knockDownVillager(villager);
            }
        }
        if (flags.getOrDefault(OperationRegistry.GREAT_HEART.getName(), 0) > 0) {
            if (entity.tickCount % 40 == 0) {
                entity.heal(1);
            }
        }
        if (flags.getOrDefault(OperationRegistry.INSERT_SWOLLEN_GROWTH_CHEST.getName(), 0) > 0) {
            if (!counters.containsKey("swollen_growth")) {
                counters.put("swollen_growth", 20 * 60 * 15);
            } else if (entity.level() instanceof ServerLevel sl && counters.get("swollen_growth") == 0 && entity.tickCount % 5 == 0) {
                sl.sendParticles(BTVParticles.BLOODSPILL.get(), entity.getX(), entity.getY(), entity.getZ(), 30, 0.5, 0.5, 0.5, 0);
            }
        }
        if (entity.tickCount % 100 == 0 && flags.getOrDefault(OperationRegistry.INSERT_FERTILIZER_GLAND_BACK.getName(), 0) > 0 && entity.level() instanceof ServerLevel sl) {
            BlockPos onPos = entity.getOnPos().above();
            BlockState state = entity.level().getBlockState(onPos);
            if (state.getBlock() instanceof BonemealableBlock bb) {
                bb.performBonemeal(sl, entity.getRandom(), onPos, state);
                sl.sendParticles(ParticleTypes.FALLING_WATER, entity.getX(), entity.getY(), entity.getZ(), 30, 0.5, 0.5, 0.5, 1);
            }
        }
        if (entity.tickCount % 1000 == 0 && flags.getOrDefault(OperationRegistry.INSERT_MARROW_GLAND_BACK.getName(), 0) > 0) {
            ItemEntity bonemeal = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.BONE_MEAL));
            entity.level().addFreshEntity(bonemeal);
        }
        if (entity.isOnFire() && flags.getOrDefault(OperationRegistry.INSERT_GUNPOWDER_BLADDER_BACK.getName(), 0) > 0 && !counters.containsKey("exploding")) {
            counters.put("exploding", 40);
            entity.level().playSound(null, entity.blockPosition(), SoundEvents.CREEPER_PRIMED, SoundSource.HOSTILE, 1, 1);
        }
        if (counters.getOrDefault("exploding", -1) == 0) {
            entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), 6, Level.ExplosionInteraction.MOB);
            entity.kill();
            entity.setHealth(0);
            counters.remove("exploding");
            flags.remove(OperationRegistry.INSERT_GUNPOWDER_BLADDER_BACK.getName());
        }
        if (entity instanceof Player p) {
            if (flags.containsKey(OperationRegistry.SPINELESS) || flags.containsKey(OperationRegistry.IRON_SPINE)) {
                p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).ifPresent(crossSyncData -> crossSyncData.getCrossSync().setCrawling(true, p));
            }
        }
        applyConvalescentAttributes(entity);
    }

    /**
     * It seems the best way is to check every few ticks...
     */
    public static void applyConvalescentAttributes(LivingEntity e) {
        if (e.tickCount % 20 == 0) {
            e.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(c -> {
                if (c.getFlags().getOrDefault(OperationRegistry.GREAT_HEART.getName(), 0) > 0 && !(e instanceof WeeperEntity)) {
                    AttributeMap attributes = e.getAttributes();
                    if (!attributes.hasModifier(Attributes.MAX_HEALTH, GREAT_HEART_HEALTH)) {
                        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
                        map.put(Attributes.MAX_HEALTH, new AttributeModifier(GREAT_HEART_HEALTH, "great_heart_health", 20, AttributeModifier.Operation.ADDITION));
                        attributes.addTransientAttributeModifiers(map);
                    }
                    //e.heal(1);
                }
                if (c.getFlags().getOrDefault(OperationRegistry.INSERT_LIVING_IRON_CHEST.getName(), 0) > 0) {
                    AttributeMap attributes = e.getAttributes();
                    if (!attributes.hasModifier(Attributes.MAX_HEALTH, LIVING_IRON_SLOW)) {
                        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
                        map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(LIVING_IRON_SLOW, "living_iron_slow", -1, AttributeModifier.Operation.MULTIPLY_TOTAL));
                        attributes.addTransientAttributeModifiers(map);
                    }
                }
            });
        }
    }

    public CompoundTag saveToNBT(CompoundTag tag) {
        CompoundTag subTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : flags.entrySet()) {
            CompoundTag entryTag = new CompoundTag();
            entryTag.putInt("value", entry.getValue());
            //if (counters.containsKey(entry.getKey())) {
                //entryTag.putInt("counter", counters.get(entry.getKey()));
            //}
            subTag.put(entry.getKey(), entryTag);
        }
        tag.put("subTag", subTag);
        if (!counters.isEmpty()) {
            CompoundTag counterTag = new CompoundTag();
            for (Map.Entry<String, Integer> entry : counters.entrySet()) {
                counterTag.putInt(entry.getKey(), entry.getValue());
            }
        }
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
        flags.clear();
        counters.clear();
        CompoundTag subTag = tag.getCompound("subTag");
        for (String key : subTag.getAllKeys()) {
            CompoundTag entryTag = subTag.getCompound(key);
            flags.put(key, entryTag.getInt("value"));
        }
        CompoundTag counterTag = tag.getCompound("counters");
        for (String key : counterTag.getAllKeys()) {
            counters.put(key, counterTag.getInt(key));
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
