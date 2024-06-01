package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.AmmunitionEntity;
import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;

public class LivingAmmunitionGoal<T extends PathfinderMob & AnimatedEntity & AmmunitionEntity> extends MeleeAttackGoal {

    private final T entity;

    public LivingAmmunitionGoal(T pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity = pMob;
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity pEnemy, double pDistToEnemySqr) {
        double d0 = this.getAttackReachSqr(pEnemy);
        if (pDistToEnemySqr <= d0 && !entity.bursting()) {
            entity.startBurst();
            for (GenericToClientPacket animationPacket : entity.getAnimationPackets()) {
                Messages.sendToTracking(animationPacket, entity);
            }

        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        // TODO depend on burst
        return super.getAttackReachSqr(pAttackTarget);
    }
}
