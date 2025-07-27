package com.valeriotor.beyondtheveil.entity.ai.attacks;

import com.google.common.collect.ImmutableList;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

public class TelegraphedAttackTemplate {
    private final int duration;
    private final int damageTime;
    private final float damage;
    private final AttackArea attackArea;
    private final double triggerDistance;
    private final double knockback;
    private final AttackList followups;
    private final int followupTime;
    private final SoundEvent damageSound;
    private final SoundEvent attackSound;
    private final List<TelegraphedAttackTemplateBuilder.Particle> particles;
    private final int initialRotationWeight;
    private final BiPredicate<LivingEntity, LivingEntity> predicate;
    private final List<BiConsumer<LivingEntity, LivingEntity>> postHitEffects;
    private final List<Consumer<LivingEntity>> postAttackEffects;
    private final AnimationTemplate animationTemplate;

    public static TelegraphedAttackTemplate of(AnimationTemplate animation, int duration, int damageTime, float damage, AttackArea attackArea, double triggerDistance, double knockback) {
        return new TelegraphedAttackTemplate.TelegraphedAttackTemplateBuilder(animation, duration, damageTime, damage, attackArea, triggerDistance).setKnockback(knockback).build();
    }

    private TelegraphedAttackTemplate(TelegraphedAttackTemplateBuilder builder) {
        animationTemplate = builder.animation;
        this.duration = builder.duration;
        this.damageTime = builder.damageTime;
        this.attackArea = builder.attackArea;
        this.triggerDistance = builder.triggerDistance;
        this.damage = builder.damage;
        this.knockback = builder.knockback;
        this.followups = AttackList.immutableAttackListOf(builder.followups);
        this.followupTime = builder.followupTime;
        this.damageSound = builder.damageSound;
        this.attackSound = builder.attackSound;
        this.particles = ImmutableList.copyOf(builder.particleList);
        this.initialRotationWeight = builder.initialRotationWeight;
        this.predicate = builder.predicate;
        this.postHitEffects = ImmutableList.copyOf(builder.postHitEffects);
        this.postAttackEffects = ImmutableList.copyOf(builder.postAttackEffects);
    }

    public <T extends Mob & AnimatedEntity> void startAnimation(T attacker, int channel) {
        attacker.sendAnimation(animationTemplate, channel);
    }

    public boolean isDone(int ticks) {
        return ticks >= duration;
    }

    public boolean isDamageTime(int ticks) {
        return ticks == damageTime;
    }

    public float getDamage() {
        return damage;
    }

    public AttackArea getAttackArea() {
        return attackArea;
    }

    public double getTriggerDistance() {
        return triggerDistance;
    }

    public double getKnockback() {
        return knockback;
    }

    public AttackList getFollowups() {
        return followups;
    }

    public int getFollowupTime() {
        return followupTime;
    }

    public SoundEvent getDamageSound() {
        return damageSound;
    }

    public SoundEvent getAttackSound() {
        return attackSound;
    }

    public void spawnParticles(Mob attacker) {
        for (TelegraphedAttackTemplateBuilder.Particle p : particles) {
            p.spawnParticle(attacker);
        }
    }

    public int getInitialRotationWeight() {
        return initialRotationWeight;
    }

    public boolean canUseAttack(LivingEntity attacker, LivingEntity target) {
        return predicate.test(attacker, target);
    }

    public void applyPostHitEffects(LivingEntity attacker, LivingEntity target) {
        postHitEffects.forEach(e -> e.accept(attacker, target));
    }

    public void applyPostAttackEffects(LivingEntity attacker) {
        postAttackEffects.forEach(e -> e.accept(attacker));
    }

    public static class TelegraphedAttackTemplateBuilder {
        private AnimationTemplate animation;
        private int duration;
        private int damageTime;
        private float damage;
        private AttackArea attackArea;
        private double triggerDistance;
        private double knockback;
        private final AttackList followups = new AttackList();
        private int followupTime = -1;
        private SoundEvent damageSound;
        private SoundEvent attackSound;
        private final List<Particle> particleList = new ArrayList<>();
        private int initialRotationWeight = 0;
        private BiPredicate<LivingEntity, LivingEntity> predicate = (e1, e2) -> true;
        private List<BiConsumer<LivingEntity, LivingEntity>> postHitEffects = new ArrayList<>();
        private List<Consumer<LivingEntity>> postAttackEffects = new ArrayList<>();

        public TelegraphedAttackTemplateBuilder(AnimationTemplate animation, int duration, int damageTime, float damage, AttackArea attackArea, double triggerDistance) {
            this.animation = animation;
            this.duration = duration;
            this.damageTime = damageTime;
            this.attackArea = attackArea;
            this.triggerDistance = triggerDistance;
            this.damage = damage;
        }

        public TelegraphedAttackTemplateBuilder setAnimation(AnimationTemplate animation) {
            this.animation = animation;
            return this;
        }

        public TelegraphedAttackTemplateBuilder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public TelegraphedAttackTemplateBuilder setDamageTime(int damageTime) {
            this.damageTime = damageTime;
            return this;
        }

        public TelegraphedAttackTemplateBuilder setDamage(float damage) {
            this.damage = damage;
            return this;
        }

        public TelegraphedAttackTemplateBuilder setAttackArea(AttackArea attackArea) {
            this.attackArea = attackArea;
            return this;
        }

        public TelegraphedAttackTemplateBuilder setTriggerDistance(double triggerDistance) {
            this.triggerDistance = triggerDistance;
            return this;
        }

        public TelegraphedAttackTemplateBuilder setKnockback(double knockback) {
            this.knockback = knockback;
            return this;
        }

        public TelegraphedAttackTemplateBuilder addFollowup(TelegraphedAttackTemplate followup, int weight) {
            followups.addAttack(followup, weight);
            return this;
        }

        public TelegraphedAttackTemplateBuilder addFollowup(Supplier<TelegraphedAttackTemplate> attackTemplateSupplier, int weight) {
            followups.addAttack(attackTemplateSupplier, weight);
            return this;
        }

        public TelegraphedAttackTemplateBuilder setNoFollowupAttackWeight(int weight) {
            followups.setNoAttackWeight(weight);
            return this;
        }

        public TelegraphedAttackTemplateBuilder removeNoFollowupAttackWeight() {
            followups.removeNoAttackWeight();
            return this;
        }

        public TelegraphedAttackTemplateBuilder setFollowupTime(int followupTime) {
            this.followupTime = followupTime;
            return this;
        }

        public TelegraphedAttackTemplateBuilder setDamageSound(SoundEvent damageSound) {
            this.damageSound = damageSound;
            return this;
        }

        public TelegraphedAttackTemplateBuilder setAttackSound(SoundEvent attackSound) {
            this.attackSound = attackSound;
            return this;
        }

        public TelegraphedAttackTemplateBuilder addParticle(ParticleOptions type, double xCoord, double yCoord, double zCoord, int numberOfParticles, double xOffset, double yOffset, double zOffset, double particleSpeed, int... parameters) {
            particleList.add(new Particle(type, xCoord, yCoord, zCoord, numberOfParticles, xOffset, yOffset, zOffset, particleSpeed, parameters));
            return this;
        }

        public TelegraphedAttackTemplateBuilder moveForward(IntToDoubleFunction ticksToMotion) {
            //TODO
            return this;
        }

        public TelegraphedAttackTemplateBuilder setInitialRotationWeight(int initialRotationWeight) {
            this.initialRotationWeight = initialRotationWeight;
            return this;
        }

        public TelegraphedAttackTemplateBuilder setPredicate(BiPredicate<LivingEntity, LivingEntity> predicate) {
            this.predicate = predicate;
            return this;
        }

        public TelegraphedAttackTemplateBuilder addPostHitEffect(BiConsumer<LivingEntity, LivingEntity> postHitEffect) {
            postHitEffects.add(postHitEffect);
            return this;
        }

        public TelegraphedAttackTemplateBuilder addPostAttackEffect(Consumer<LivingEntity> postAttackEffect) {
            postAttackEffects.add(postAttackEffect);
            return this;
        }

        public TelegraphedAttackTemplate build() {
            return new TelegraphedAttackTemplate(this);

        }

        public static class AttackSupplier {
            private TelegraphedAttackTemplate attack;

            public void setAttack(TelegraphedAttackTemplate attack) {
                this.attack = attack;
            }

            public TelegraphedAttackTemplate getAttack() {
                return attack;
            }
        }

        private static class Particle {
            private final ParticleOptions type;
            private final double xCoord;
            private final double yCoord;
            private final double zCoord;
            private final int numberOfParticles;
            private final double xOffset;
            private final double yOffset;
            private final double zOffset;
            private final double particleSpeed;
            private final int[] parameters;

            private Particle(ParticleOptions type, double xCoord, double yCoord, double zCoord, int numberOfParticles, double xOffset, double yOffset, double zOffset, double particleSpeed, int... parameters) {
                this.type = type;
                this.xCoord = xCoord;
                this.yCoord = yCoord;
                this.zCoord = zCoord;
                this.numberOfParticles = numberOfParticles;
                this.xOffset = xOffset;
                this.yOffset = yOffset;
                this.zOffset = zOffset;
                this.particleSpeed = particleSpeed;
                this.parameters = parameters;
            }

            private double getX(LivingEntity attacker) {
                return attacker.position().x + xCoord;
            }

            private double getY(LivingEntity attacker) {
                return attacker.position().y + yCoord;
            }

            private double getZ(LivingEntity attacker) {
                return attacker.position().z + zCoord;
            }

            public void spawnParticle(LivingEntity attacker) {
                ((ServerLevel) attacker.level()).sendParticles(type, getX(attacker), getY(attacker), getZ(attacker), numberOfParticles, xOffset, yOffset, zOffset, 1);
            }

            @Override
            public String toString() {
                return "Particle{" +
                        "type=" + type +
                        ", xCoord=" + xCoord +
                        ", yCoord=" + yCoord +
                        ", zCoord=" + zCoord +
                        ", numberOfParticles=" + numberOfParticles +
                        ", xOffset=" + xOffset +
                        ", yOffset=" + yOffset +
                        ", zOffset=" + zOffset +
                        ", particleSpeed=" + particleSpeed +
                        ", parameters=" + Arrays.toString(parameters) +
                        '}';
            }
        }


    }

}