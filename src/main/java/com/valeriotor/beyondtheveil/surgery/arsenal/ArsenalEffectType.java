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

    public static class ArsenalStatusEffectType extends ArsenalEffectType {

        private final MobEffect effect;
        private final DurationArray durationArray;


        public ArsenalStatusEffectType(String name, MobEffect effect) {
            this(name, effect, DurationArray.DEFAULT);
        }

        public ArsenalStatusEffectType(String name, MobEffect effect, DurationArray durationArray) {
            super(name);
            this.effect = effect;
            this.durationArray = durationArray;
        }

        @Override
        public void doEffect(Mob attacker, LivingEntity target, int duration, int amplifier, boolean hideParticles) {
            amplifier = effect == MobEffects.DAMAGE_RESISTANCE ? Math.min(3, amplifier) : amplifier;
            target.addEffect(new MobEffectInstance(effect, durationArray.durationArray[duration] * 20, amplifier, false, true));
        }

        public enum DurationArray {
            DEFAULT(10, 20, 35, 70),
            SMALL_DESCENDING(16, 8, 4, 2);

            final int[] durationArray;

            DurationArray(int i, int i1, int i2, int i3) {
                durationArray = new int[]{i, i1, i2, i3};
            }
        }
    }


}
