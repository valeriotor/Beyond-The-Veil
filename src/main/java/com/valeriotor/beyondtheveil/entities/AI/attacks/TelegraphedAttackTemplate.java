package com.valeriotor.beyondtheveil.entities.AI.attacks;

import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.animations.AnimationTemplate;
import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
import net.minecraft.util.SoundEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class TelegraphedAttackTemplate {
    private final int animationID;
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

    public TelegraphedAttackTemplate(AnimationTemplate animation, int duration, int damageTime, float damage, AttackArea attackArea, double triggerDistance) {
        this(animation, duration, damageTime, damage, attackArea, triggerDistance, 0);
    }

    public TelegraphedAttackTemplate(AnimationTemplate animation, int duration, int damageTime, float damage, AttackArea attackArea, double triggerDistance, double knockback) {
        this(animation, duration, damageTime, damage, attackArea, triggerDistance, knockback, AttackList.EMPTY, -1, null, null);
    }

    private TelegraphedAttackTemplate(AnimationTemplate animation, int duration, int damageTime, float damage, AttackArea attackArea, double triggerDistance, double knockback, AttackList followups, int followupTime, SoundEvent damageSound, SoundEvent attackSound) {
        this.animationID = AnimationRegistry.getIdFromAnimation(animation);
        this.duration = duration;
        this.damageTime = damageTime;
        this.attackArea = attackArea;
        this.triggerDistance = triggerDistance;
        this.damage = damage;
        this.knockback = knockback;
        this.followups = AttackList.immutableAttackListOf(followups);
        this.followupTime = followupTime;
        this.damageSound = damageSound;
        this.attackSound = attackSound;
    }

    public void startAnimation(IAnimatedAttacker attacker) {
        attacker.sendAnimation(AnimationRegistry.getIdFromAnimation(AnimationRegistry.getAnimationFromId(animationID)));
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

    public static class TelegraphedAttackTemplateBuilder {
        private AnimationTemplate animation;
        private int duration;
        private int damageTime;
        private float damage;
        private AttackArea attackArea;
        private double triggerDistance;
        private double knockback;
        private AttackList followups = new AttackList();
        private int followupTime = -1;
        private SoundEvent damageSound;
        private SoundEvent attackSound;

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

        public TelegraphedAttackTemplate build() {
            return new TelegraphedAttackTemplate(animation, duration, damageTime, damage, attackArea, triggerDistance, knockback, followups, followupTime, damageSound, attackSound);
        }

    }

}
