package com.valeriotor.beyondtheveil.entities.AI.attacks;

import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TelegraphedAttack {

    private final TelegraphedAttackTemplate template;
    private final EntityCreature attacker;
    private final double initialRotation;
    private int counter = 0;

    public TelegraphedAttack(TelegraphedAttackTemplate template, EntityCreature attacker, double initialRotation) {
        this.template = template;
        this.attacker = attacker;
        this.initialRotation = initialRotation;
        template.startAnimation((IAnimatedAttacker)attacker);
    }

    public void update() {
        counter++;
        attacker.setRotationYawHead((float) initialRotation);
        if(template.isDamageTime(counter)) {
            List<EntityLivingBase> victims = template.getAttackArea().getVictims(attacker, initialRotation);
            DamageSource source = DamageSource.causeMobDamage(attacker);
            victims.forEach(e -> {
                e.attackEntityFrom(source, template.getDamage());
                if(template.getKnockback() > 0)
                    e.knockBack(attacker, (float)template.getKnockback() * 0.5F, (double) MathHelper.sin((float) (initialRotation * 0.017453292F)), (double)(-MathHelper.cos((float)initialRotation * 0.017453292F)));
            });
            SoundEvent damageSound = template.getDamageSound();
            if(damageSound != null && victims.size() > 0)
                attacker.world.playSound(null, attacker.getPosition(), damageSound, SoundCategory.HOSTILE, 1, 1);
            SoundEvent attackSound = template.getAttackSound();
            if(attackSound != null)
                attacker.world.playSound(null, attacker.getPosition(), attackSound, SoundCategory.HOSTILE, 1, 1);
            template.spawnParticles(attacker);
        }
    }

    public boolean isDone() {
        return template.isDone(counter);
    }

    public boolean isFollowupTime() {
        return counter == template.getFollowupTime();
    }

    public Optional<TelegraphedAttackTemplate> getFollowupAttack(Random rand, double distance) {
        template.getFollowups();
        return template.getFollowups().getRandomAttack(rand, distance);
    }

    public double getInitialRotation() {
        return initialRotation;
    }

    public int getInitialRotationWeight() {
        return template.getInitialRotationWeight();
    }

}
