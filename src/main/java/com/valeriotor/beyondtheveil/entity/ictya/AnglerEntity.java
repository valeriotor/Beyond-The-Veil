package com.valeriotor.beyondtheveil.entity.ictya;

import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import com.valeriotor.beyondtheveil.entity.ai.goals.TelegraphedAttackGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class AnglerEntity extends IctyaEntity implements AnimatedEntity {

    public AnglerEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new AnglerAttackGoal(this, 1.8D, true));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.5D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D);
    }


    @Override
    public IctyaSize getSize() {
        return IctyaSize.SMALL;
    }

    @Override
    public double getFoodValue() {
        return 100;
    }

    @Override
    public double getMaxFood() {
        return 100;
    }

    @Override
    public double getFoodPer32Ticks() {
        return getCurrentFoodRatio() < 0.5 ? 0.5 : 1;
    }

    @Override
    public void startAnimation(AnimationTemplate animationTemplate, int channel) {

    }

    private static class AnglerAttackGoal extends MeleeAttackGoal {

        private boolean shouldMove = false;

        public AnglerAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
            super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        }

        @Override
        public void start() {
            lure();
        }

        private void lure() {
            LivingEntity target = mob.getTarget();
            if (target instanceof IctyaEntity i && i.getSize().ordinal() <= IctyaSize.SMALL.ordinal()) {
                i.getNavigation().moveTo(mob, 0.5);
            }
        }

        @Override
        public void tick() {
            if (shouldMove) {
                super.tick();
            } else {
                lure();
                if (mob.getTarget() != null && mob.getTarget().distanceToSqr(mob) < 100) {
                    shouldMove = true;
                }
            }
        }

        @Override
        public void stop() {
            super.stop();
            shouldMove = false;
        }
    }
}
