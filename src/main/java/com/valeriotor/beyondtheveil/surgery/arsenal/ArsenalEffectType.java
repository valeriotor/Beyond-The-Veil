package com.valeriotor.beyondtheveil.surgery.arsenal;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public abstract class ArsenalEffectType {

    private final String name;

    public ArsenalEffectType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void doEffect(Mob attacker, LivingEntity target, int duration, int amplifier, boolean hideParticles);

    public static class VanillaArsenalEffectType extends ArsenalEffectType {

        private final MobEffect effect;

        public VanillaArsenalEffectType(String name, MobEffect effect) {
            super(name);
            this.effect = effect;
        }

        @Override
        public void doEffect(Mob attacker, LivingEntity target, int duration, int amplifier, boolean hideParticles) {
            amplifier = effect == MobEffects.DAMAGE_RESISTANCE ? Math.min(3, amplifier) : amplifier;
            target.addEffect(new MobEffectInstance(effect, duration, amplifier, false, !hideParticles));
        }
    }


}
