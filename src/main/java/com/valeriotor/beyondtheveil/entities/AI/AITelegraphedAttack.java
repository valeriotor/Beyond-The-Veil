package com.valeriotor.beyondtheveil.entities.AI;

import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackList;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttack;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttackTemplate;
import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;

import java.util.Optional;

public class AITelegraphedAttack extends EntityAIAttackMelee {

    private final AttackList attacks;
    private TelegraphedAttack attack;
    private final double speedIn;

    public <T extends EntityCreature & IAnimatedAttacker> AITelegraphedAttack(T creature, double speedIn, boolean useLongMemory, AttackList attacks) {
        super(creature, speedIn, useLongMemory);
        this.attacks = attacks;
        this.speedIn = speedIn;
    }

    @Override
    protected void checkAndPerformAttack(EntityLivingBase e, double p_190102_2_) {
        //super.checkAndPerformAttack(e, p_190102_2_);
        if(attack == null) {
            if((e.ticksExisted & 7) == 0) {
                EntityLivingBase target = attacker.getAttackTarget();
                double distance = attacker.getDistance(target);
                Optional<TelegraphedAttackTemplate> attack = attacks.getRandomAttack(e.world.rand, distance);
                attack.ifPresent(template -> this.attack = new TelegraphedAttack(template, attacker, MathHelperBTV.angleBetween(attacker, target)));
            }
        } else {
            attacker.getNavigator().clearPath();
            attack.update();
            if(attack.isDone()) {
                attack = null;
                this.attacker.getNavigator().tryMoveToEntityLiving(attacker.getAttackTarget(), this.speedIn);
            } else if(attack.isFollowupTime()) {
                tryFollowup(e);
            }
        }
    }

    private void tryFollowup(EntityLivingBase e) {
        EntityLivingBase target = attacker.getAttackTarget();
        double distance = attacker.getDistance(target);
        Optional<TelegraphedAttackTemplate> followupAttack = attack.getFollowupAttack(e.world.rand, distance);
        followupAttack.ifPresent(template -> this.attack = new TelegraphedAttack(template, attacker, MathHelperBTV.angleBetween(attacker, target)));
    }
}
