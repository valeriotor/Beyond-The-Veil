package com.valeriotor.beyondtheveil.entities.AI.attacks;

import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

import java.util.List;

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
        if(template.isDamageTime(counter)) {
            List<EntityLivingBase> victims = template.getAttackArea().getVictims(attacker, initialRotation);
            DamageSource source = DamageSource.causeMobDamage(attacker);
            victims.forEach(e -> {
                e.attackEntityFrom(source, template.getDamage());
                if(template.getKnockback() > 0)
                    e.knockBack(attacker, (float)template.getKnockback() * 0.5F, (double) MathHelper.sin((float) (initialRotation * 0.017453292F)), (double)(-MathHelper.cos((float)initialRotation * 0.017453292F)));
            });
        }
    }

    public boolean isDone() {
        return template.isDone(counter);
    }
}
