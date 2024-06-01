package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.entity.AmmunitionEntity;
import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class LivingAmmunitionGoal<T extends PathfinderMob & AnimatedEntity & AmmunitionEntity> extends MeleeAttackGoal {

    private final T entity;
    private boolean attackStarted = false;

    public LivingAmmunitionGoal(T pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity = pMob;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        double d0 = this.getAttackReachSqr(pEnemy);
        if (pDistToEnemySqr <= d0 && !attackStarted) {
            attackStarted = true;
            GenericToClientPacket genericToClientPacket1 = GenericToClientPacket.startAnimation(AnimationRegistry.ammunition_explode, entity.getId(), 0);
            GenericToClientPacket genericToClientPacket2 = GenericToClientPacket.startAnimation(AnimationRegistry.ammunition_explode_body, entity.getId(), 1);
            Messages.sendToTracking(genericToClientPacket1, entity);
            Messages.sendToTracking(genericToClientPacket2, entity);
        } else {
            //attackStarted = false;
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        // TODO depend on burst
        return super.getAttackReachSqr(pAttackTarget);
    }
}
