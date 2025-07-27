package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import com.valeriotor.beyondtheveil.entity.ai.attacks.AttackList;
import com.valeriotor.beyondtheveil.entity.ai.attacks.TelegraphedAttack;
import com.valeriotor.beyondtheveil.entity.ai.attacks.TelegraphedAttackTemplate;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;
import java.util.Optional;

public class TelegraphedAttackGoal<T extends PathfinderMob & AnimatedEntity> extends Goal {

    private final AttackList attacks;
    private final T mob;
    private TelegraphedAttack<T> attack;
    private final int animationChannel;

    private final double speedModifier;

    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private long lastCanUseCheck;
    private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;

    public TelegraphedAttackGoal(T attacker, double speedModifier, boolean followingTargetEvenIfNotSeen, AttackList attacks, int animationChannel) {
        this.mob = attacker;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        this.attacks = attacks;
        this.animationChannel = animationChannel;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        long i = this.mob.level().getGameTime();
        if (i - this.lastCanUseCheck < 20L) {
            return false;
        } else {
            this.lastCanUseCheck = i;
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                if (canPenalize) {
                    if (--this.ticksUntilNextPathRecalculation <= 0) {
                        this.path = this.mob.getNavigation().createPath(livingentity, 0);
                        this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                        return this.path != null;
                    } else {
                        return true;
                    }
                }
                this.path = this.mob.getNavigation().createPath(livingentity, 0);
                if (this.path != null) {
                    return true;
                } else {
                    return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                }
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity == null) {
            return false;
        } else if (false) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.mob.getNavigation().isDone();
        } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
            return false;
        } else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
        }
    }

    public void start() {
        this.mob.getNavigation().moveTo(this.path, this.speedModifier);
        this.mob.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
    }

    public void stop() {
        LivingEntity livingentity = this.mob.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.mob.setTarget((LivingEntity) null);
        }

        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null) {
            double d0 = this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity);
            if (attack == null) {
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                    this.pathedTargetX = livingentity.getX();
                    this.pathedTargetY = livingentity.getY();
                    this.pathedTargetZ = livingentity.getZ();
                    this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                    if (this.canPenalize) {
                        this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
                        if (this.mob.getNavigation().getPath() != null) {
                            net.minecraft.world.level.pathfinder.Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
                            if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                                failedPathFindingPenalty = 0;
                            else
                                failedPathFindingPenalty += 10;
                        } else {
                            failedPathFindingPenalty += 10;
                        }
                    }
                    if (d0 > 1024.0D) {
                        this.ticksUntilNextPathRecalculation += 10;
                    } else if (d0 > 256.0D) {
                        this.ticksUntilNextPathRecalculation += 5;
                    }

                    if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
                        this.ticksUntilNextPathRecalculation += 15;
                    }

                    this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
                }
            }

            this.checkAndPerformAttack(livingentity, d0);
        }
    }

    protected int getAttackInterval() {
        return this.adjustedTickDelay(20);
    }

    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + pAttackTarget.getBbWidth());
    }

    protected void checkAndPerformAttack(LivingEntity e, double p_190102_2_) {
        if (attack == null) {
            LivingEntity target = mob.getTarget();
            if (target == null) return;
            if ((mob.tickCount & 7) == 0) {
                double distance = mob.distanceTo(target);
                Optional<TelegraphedAttackTemplate> attack = attacks.getRandomAttack(mob.getRandom(), distance, mob, target);
                attack.ifPresent(template -> this.attack = new TelegraphedAttack<T>(template, mob, MathHelperBTV.angleBetween(mob, target), animationChannel));
            }
        } else {
            mob.getNavigation().stop();
            attack.update();
            if (attack.isDone()) {
                attack.applyPostAttackEffects();
                attack = null;
                if (mob.getTarget() != null) {
                    this.mob.getNavigation().moveTo(mob.getTarget(), this.speedModifier);
                }
            } else if (attack.isFollowupTime()) {
                tryFollowup(mob);
            }
        }
    }

    public boolean isAttacking() {
        return attack != null;
    }

    private void tryFollowup(LivingEntity e) {
        LivingEntity target = mob.getTarget();
        if (target == null || target.isDeadOrDying()) return;
        double distance = mob.distanceTo(target);
        Optional<TelegraphedAttackTemplate> followupAttack = attack.getFollowupAttack(mob.getRandom(), distance, mob, target);
        int initialRotationWeight = attack.getInitialRotationWeight();
        double newAngle = (MathHelperBTV.angleBetween(mob, target) + attack.getInitialRotation() * initialRotationWeight) / (1 + initialRotationWeight);
        followupAttack.ifPresent(template -> this.attack = new TelegraphedAttack<T>(template, mob, newAngle, animationChannel));
    }

}
