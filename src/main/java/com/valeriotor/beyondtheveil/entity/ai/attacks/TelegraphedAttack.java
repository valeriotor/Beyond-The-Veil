package com.valeriotor.beyondtheveil.entity.ai.attacks;

import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TelegraphedAttack<T extends Mob & AnimatedEntity> {

    private final TelegraphedAttackTemplate template;
    private final T attacker;
    private final double initialRotation;
    private int counter = 0;
    private ResourceKey<DamageType> damageType;

    public TelegraphedAttack(TelegraphedAttackTemplate template, T attacker, double initialRotation, int animationChannel) { // TODO divide in initialYaw and initialPitch
        this.template = template;
        this.attacker = attacker;
        this.initialRotation = initialRotation;
        template.startAnimation(attacker, animationChannel);
    }

    public void setDamageType(ResourceKey<DamageType> damageType) {
        this.damageType = damageType;
    }

    public void update() {
        counter++;
        Vec3 posToLookAt = attacker.position().add(Vec3.directionFromRotation(0, (float) initialRotation));
        attacker.getLookControl().setLookAt(posToLookAt.x, posToLookAt.y + attacker.getEyeHeight(), posToLookAt.z, 50, 30);
        if(template.isDamageTime(counter)) {
            List<LivingEntity> victims = template.getAttackArea().getVictims(attacker, initialRotation);
            damageType = DamageTypes.MOB_ATTACK;
            victims.forEach(e -> {
                e.hurt(attacker.damageSources().mobAttack(attacker), template.getDamage());
                if (template.getKnockback() > 0) {
                    e.knockback(template.getKnockback() * 0.5, (double) Mth.sin((float) (initialRotation * 0.017453292F)), (double) (-Mth.cos((float) initialRotation * 0.017453292F)));
                }
                template.applyPostHitEffects(attacker, e);
            });
            SoundEvent damageSound = template.getDamageSound();
            if(damageSound != null && victims.size() > 0) {
                attacker.level().playSound(null, attacker.getOnPos(), damageSound, SoundSource.HOSTILE, 8, 1);
            }
            SoundEvent attackSound = template.getAttackSound();
            if(attackSound != null) {
                attacker.level().playSound(null, attacker.getOnPos(), attackSound, SoundSource.HOSTILE, 8, 1);
            }
            template.spawnParticles(attacker);
        }
    }

    public boolean canContinueMoving() {
        return template.canContinueMoving();
    }

    public boolean isDone() {
        return template.isDone(counter);
    }

    public boolean isFollowupTime() {
        return counter == template.getFollowupTime();
    }

    public Optional<TelegraphedAttackTemplate> getFollowupAttack(RandomSource rand, double distance, LivingEntity attacker, LivingEntity target) {
        return template.getFollowups().getRandomAttack(rand, distance, attacker, target);
    }

    public double getInitialRotation() {
        return initialRotation;
    }

    public int getInitialRotationWeight() {
        return template.getInitialRotationWeight();
    }

    public void applyPostAttackEffects() {
        template.applyPostAttackEffects(attacker);
    }

}
