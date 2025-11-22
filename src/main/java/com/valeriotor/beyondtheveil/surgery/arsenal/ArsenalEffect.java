package com.valeriotor.beyondtheveil.surgery.arsenal;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import java.util.HashSet;
import java.util.Set;

public class ArsenalEffect {

    private final ArsenalEffectType effectType;
    private final Set<String> amplifiers;
    private final Set<String> durations;
    private final boolean hideParticles;

    public ArsenalEffect(CompoundTag tag) {
        effectType = ArsenalEffectRegistry.REGISTRY.get(tag.getString("type"));
        CompoundTag amplifiers = tag.getCompound("amplifiers");
        CompoundTag durations = tag.getCompound("durations");
        this.amplifiers = new HashSet<>(amplifiers.getAllKeys());
        this.durations = new HashSet<>(durations.getAllKeys());
        hideParticles = tag.getBoolean("hideParticles");
    }

    public ArsenalEffect(ArsenalEffectType effectType, Set<String> amplifiers, Set<String> durations, boolean hideParticles) {
        this.effectType = effectType;
        this.amplifiers = new HashSet<>(amplifiers);
        this.durations = new HashSet<>(durations);
        this.hideParticles = hideParticles;
    }

    public void process(LivingEntity attacker, LivingEntity target) {
        if (effectType != null) {
            effectType.doEffect(attacker, target, durations.size(), amplifiers.size(), hideParticles);
        }
    }

    public ArsenalEffectType getEffectType() {
        return effectType;
    }

    public int getAmplifier() {
        return amplifiers.size();
    }

    public Set<String> getAmplifiers() {
        return amplifiers;
    }

    public int getDuration() {
        return durations.size();
    }

    public Set<String> getDurations() {
        return durations;
    }

    public CompoundTag writeToNBT(CompoundTag tag) {
        if (effectType != null) {
            tag.putString("type", effectType.getName());
        }
        CompoundTag amplifiers = new CompoundTag();
        CompoundTag durations = new CompoundTag();
        this.amplifiers.forEach(s -> amplifiers.putBoolean(s, true));
        this.durations.forEach(s -> durations.putBoolean(s, true));
        tag.put("amplifiers", amplifiers);
        tag.put("durations", durations);
        tag.putBoolean("hideParticles", hideParticles);
        return tag;
    }

}
