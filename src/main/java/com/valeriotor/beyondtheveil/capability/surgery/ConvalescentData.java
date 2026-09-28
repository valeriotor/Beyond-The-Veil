package com.valeriotor.beyondtheveil.capability.surgery;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.entity.WeeperEntity;
import com.valeriotor.beyondtheveil.item.BlackjackItem;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.surgery.OperationRegistry;
import com.valeriotor.beyondtheveil.surgery.PatientCondition;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.*;

public class ConvalescentData {

    public static ConvalescentData of(PatientStatus status) {
        ConvalescentData data = new ConvalescentData(status.getPatientType() == PatientType.WEEPER);
        data.setCondition(status.getCondition());
        data.setFlags(status.getPersistentFlags());
        data.setTriggerData(status.getTriggerData());
        data.setCapacity(status.getLeftoverCapacity());
        data.setUsedAbominationCapacity(status.getUsedAbominationCapacity());
        data.chestEffects.putAll(status.makeChestEffects());
        return data;
    }

    private PatientCondition condition = PatientCondition.STABLE;
    private final Map<String, Integer> flags = new HashMap<>(); // the int value stands for how many times it was applied in the procedure
    private final Map<String, Integer> counters = new HashMap<>(); // populated lazily. Keys are the flags, values are any integer counter that may be of use
    private final Map<MobEffect, Integer> chestEffects = new HashMap<>(); //TODO
    private TriggerData triggerData;
    private int capacity;
    private int usedAbominationCapacity;
    private int collectedXP = 0;
    private BlockPos chestPos;
    private ItemStack heldStack = ItemStack.EMPTY;

    public ConvalescentData() {

    }

    public ConvalescentData(boolean weeper) {
        if (weeper) {
            capacity = 15; // only on first creation
        }
    }

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

    public void setUsedAbominationCapacity(int usedAbominationCapacity) {
        this.usedAbominationCapacity = usedAbominationCapacity;
    }

    public int getUsedAbominationCapacity() {
        return usedAbominationCapacity;
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


    public void tick(LivingEntity entity) { // only called server side
        counters.replaceAll((k, v) -> Math.max(0, v - 1));
        if (counters.containsKey("spreading_iron") && counters.get("spreading_iron") == 0) {
            counters.remove("spreading_iron");
            flags.put(OperationRegistry.IRON_SPINE, 1);
            if (entity instanceof Villager villager) {
                BlackjackItem.knockDownVillager(villager);
            }
        }

        if (entity.tickCount % 20 == 0) {
            for (Map.Entry<MobEffect, Integer> e : chestEffects.entrySet()) {
                String name = OperationRegistry.getNameForChestEffectInjection(e.getKey());
                if (name != null && !counters.containsKey(name)) {
                    counters.put(name, e.getKey() == MobEffects.HARM || e.getKey() == MobEffects.HEAL ? 20 * 20 : 20 * 1200);
                    flags.remove(name);
                }
            }
        }
        for (Iterator<Map.Entry<String, Integer>> iterator = counters.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Integer> e = iterator.next();
            MobEffect effect = OperationRegistry.getChestEffectInjection(e.getKey());
            if (e.getValue() % 20 == 1) {
                if (effect != null) {
                    entity.addEffect(new MobEffectInstance(effect, 15 * 20, chestEffects.getOrDefault(effect, 0)));
                }
            } else if (e.getValue() == 0) {
                if (effect != null) {
                    iterator.remove();
                }
            }
        }

        if (entity.tickCount % 400 == 0 && flags.getOrDefault(OperationRegistry.MEMORY_HORMONES_CHEST_BACK.getName(), 0) > 0) {
            if (entity instanceof ServerPlayer sp) {
                sp.giveExperiencePoints(1);
            } else {
                collectedXP++;
            }
        }
        if (entity.tickCount % 200 == 0 && flags.getOrDefault(OperationRegistry.PHEROMONES_CHEST.getName(), 0) > 0) {
            List<Entity> entities = entity.level().getEntities((Entity) null, AABB.ofSize(entity.position(), 25, 20, 25), e -> e instanceof Monster);
            if (!entities.isEmpty()) {
                for (int i = 0; i < 5; i++) {
                    Entity monster = entities.get(entity.getRandom().nextInt(entities.size()));
                    if (monster instanceof Monster m) {
                        m.setTarget(entity);
                    }
                }

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
    private static final UUID GREAT_HEART_HEALTH = UUID.fromString("8c498269-ccf0-4e93-9fb8-c4a5eda417f8");
    private static final UUID LIVING_IRON_SLOW = UUID.fromString("8c498269-ccf0-4e93-9fb8-c4a5eda417f9");
    private static final UUID GREAT_SPINE_KNOCKBACK_RESISTANCE = UUID.fromString("8c498269-ccf0-4e93-9fb8-c4a5eda417fa");

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
                if (c.getFlags().getOrDefault(OperationRegistry.GREAT_SPINE.getName(), 0) > 0) {
                    AttributeMap attributes = e.getAttributes();
                    if (!attributes.hasModifier(Attributes.KNOCKBACK_RESISTANCE, GREAT_SPINE_KNOCKBACK_RESISTANCE)) {
                        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
                        map.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(GREAT_SPINE_KNOCKBACK_RESISTANCE, "great_spine_knockback_resistance", 1, AttributeModifier.Operation.ADDITION));
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
        tag.putInt("usedCapacity", usedAbominationCapacity);
        if (chestPos != null) {
            tag.putLong("chestPos", chestPos.asLong());
        }
        tag.put("heldStack", heldStack.save(new CompoundTag()));
        CompoundTag chestEffectsTag = new CompoundTag();
        for (Map.Entry<MobEffect, Integer> entry : chestEffects.entrySet()) {
            chestEffectsTag.putInt(OperationRegistry.getNameForChestEffectInjection(entry.getKey()), entry.getValue());
        }
        tag.put("chestEffects", chestEffectsTag);
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
        if (tag.contains("capacity")) { // if check so that it doesn't overwrite weeper base capacity with 0
            capacity = tag.getInt("capacity");
        }
        usedAbominationCapacity = tag.getInt("usedCapacity");
        if (tag.contains("chestPos")) {
            chestPos = BlockPos.of(tag.getLong("chestPos"));
        }
        if (tag.contains("heldStack")) {
            heldStack = ItemStack.of(tag.getCompound("heldStack"));
        }
        chestEffects.clear();
        if (tag.contains("chestEffects", Tag.TAG_COMPOUND)) {
            CompoundTag chestEffects1 = tag.getCompound("chestEffects");
            for (String effectKey : chestEffects1.getAllKeys()) {
                chestEffects.put(OperationRegistry.getChestEffectInjection(effectKey), chestEffects1.getInt(effectKey));
            }
        }
    }
}
