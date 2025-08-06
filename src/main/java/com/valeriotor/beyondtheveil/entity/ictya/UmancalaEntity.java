package com.valeriotor.beyondtheveil.entity.ictya;

import com.valeriotor.beyondtheveil.entity.projectile.UmancalaFireball;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class UmancalaEntity extends IctyaEntity{

    public UmancalaEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(2, new UmancalaAttackGoal(this));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.115D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5);
    }

    @Override
    public IctyaSize getSize() {
        return IctyaSize.MEDIUM;
    }

    @Override
    public double getFoodValue() {
        return 180;
    }

    @Override
    public double getMaxFood() {
        return 250;
    }

    @Override
    public double getFoodPer32Ticks() {
        return 2.4;
    }

    /** Copied from Blaze.BlazeAttackGoal
     */
    private static class UmancalaAttackGoal extends Goal {
        private final UmancalaEntity umancala;
        private int attackStep;
        private int attackTime;
        private int lastSeen;

        public UmancalaAttackGoal(UmancalaEntity umancala) {
            this.umancala = umancala;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.umancala.getTarget();
            return livingentity != null && livingentity.isAlive() && this.umancala.canAttack(livingentity);
        }

        public void start() {
            this.attackStep = 0;
        }

        public void stop() {
            this.lastSeen = 0;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            --this.attackTime;
            LivingEntity livingentity = this.umancala.getTarget();
            if (livingentity != null) {
                boolean flag = this.umancala.getSensing().hasLineOfSight(livingentity);
                if (flag) {
                    this.lastSeen = 0;
                } else {
                    ++this.lastSeen;
                }

                double d0 = this.umancala.distanceToSqr(livingentity);
                if (d0 < 4.0D) {
                    if (!flag) {
                        return;
                    }

                    if (this.attackTime <= 0) {
                        this.attackTime = 20;
                        this.umancala.doHurtTarget(livingentity);
                    }

                    this.umancala.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0D);
                } else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
                    double d1 = livingentity.getX() - this.umancala.getX();
                    double d2 = livingentity.getY(0.5D) - this.umancala.getY(0.5D);
                    double d3 = livingentity.getZ() - this.umancala.getZ();
                    if (this.attackTime <= 0) {
                        ++this.attackStep;
                        if (this.attackStep == 1) {
                            this.attackTime = 60;
                        } else if (this.attackStep <= 4) {
                            this.attackTime = 6;
                        } else {
                            this.attackTime = 80;
                            this.attackStep = 0;
                        }

                        if (this.attackStep > 1) {
                            double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;
                            if (!this.umancala.isSilent()) {
                                this.umancala.level().levelEvent((Player)null, 1018, this.umancala.blockPosition(), 0);
                            }

                            for(int i = 0; i < 1; ++i) {
                                UmancalaFireball fireball = new UmancalaFireball(BTVEntities.UMANCALA_FIREBALL.get(), this.umancala, this.umancala.getRandom().triangle(d1, 2.297D * d4), d2, this.umancala.getRandom().triangle(d3, 2.297D * d4), this.umancala.level());
                                fireball.setPos(fireball.getX(), this.umancala.getY(0.5D) + 0.5D, fireball.getZ());
                                this.umancala.level().addFreshEntity(fireball);
                            }
                        }
                    }

                    this.umancala.getLookControl().setLookAt(livingentity, 10.0F, 10.0F);
                } else if (this.lastSeen < 5) {
                    this.umancala.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0D);
                }

                super.tick();
            }
        }

        private double getFollowDistance() {
            return this.umancala.getAttributeValue(Attributes.FOLLOW_RANGE);
        }
    }
}
