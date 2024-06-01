package com.valeriotor.beyondtheveil.surgery.arsenal;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class ArsenalEffect {

    private final ArsenalEffectType effectType;
    private final int amplifier;
    private final int duration;
    private final boolean hideParticles;

    public ArsenalEffect(CompoundTag tag) {
        effectType = ArsenalEffectRegistry.REGISTRY.get(tag.getString("type"));
        amplifier = tag.getInt("amplifier");
        duration = tag.getInt("duration");
        hideParticles = tag.getBoolean("hideParticles");
    }

    public ArsenalEffect(ArsenalEffectType effectType, int amplifier, int duration, boolean hideParticles) {
        this.effectType = effectType;
        this.amplifier = amplifier;
        this.duration = duration;
        this.hideParticles = hideParticles;
    }

    public void process(Mob attacker, LivingEntity target) {
        effectType.doEffect(attacker, target, duration, amplifier, hideParticles);
    }

    public ArsenalEffectType getEffectType() {
        return effectType;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public CompoundTag writeToNBT(CompoundTag tag) {
        tag.putString("type", effectType.toString());
        tag.putInt("amplifier", amplifier);
        tag.putInt("duration", duration);
        tag.putBoolean("hideParticles", hideParticles);
        return tag;
    }

}
