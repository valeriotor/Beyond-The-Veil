package com.valeriotor.beyondtheveil.entities.AI.attacks;

import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.animations.AnimationTemplate;
import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;

public class TelegraphedAttackTemplate {
    private final AnimationTemplate animation;
    private final int duration;
    private final int damageTime;
    private final float damage;
    private final AttackArea attackArea;
    private final double triggerDistance;
    private final double knockback;

    public TelegraphedAttackTemplate(AnimationTemplate animation, int duration, int damageTime, float damage, AttackArea attackArea, double triggerDistance) {
        this(animation, duration, damageTime, damage, attackArea, triggerDistance, 0);
    }

    public TelegraphedAttackTemplate(AnimationTemplate animation, int duration, int damageTime, float damage, AttackArea attackArea, double triggerDistance, double knockback) {
        this.animation = animation;
        this.duration = duration;
        this.damageTime = damageTime;
        this.attackArea = attackArea;
        this.triggerDistance = triggerDistance;
        this.damage = damage;
        this.knockback = knockback;
    }

    public void startAnimation(IAnimatedAttacker attacker) {
        attacker.sendAnimation(AnimationRegistry.getIdFromAnimation(animation));
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
}
