package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.entity.ai.goals.LivingAmmunitionGoal;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.surgery.arsenal.ArsenalEffect;
import com.valeriotor.beyondtheveil.surgery.arsenal.Burst;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class WeeperEntity extends PathfinderMob implements AnimatedEntity, AmmunitionEntity{

    private static final EntityDataAccessor<Integer> DATA_BLEEDING = SynchedEntityData.defineId(WeeperEntity.class, EntityDataSerializers.INT);
    private Animation explodingAnimation;
    private boolean wasBleeding = false;
    private int attackTimer = -1;
    private int deathTimer = -1;


    public WeeperEntity(EntityType<? extends PathfinderMob> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        //this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 12));
        //this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(0, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(2, new LivingAmmunitionGoal<>(this, 1.8D, false));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BLEEDING, -1);
    }

    @Override
    public List<GenericToClientPacket> getAnimationPackets() {
        List<GenericToClientPacket> animationPackets = new ArrayList<>();
        animationPackets.add(GenericToClientPacket.startAnimation(AnimationRegistry.weeper_explode, getId(), 0));
        return animationPackets;
    }

    @Override
    public void startBurst() {
        attackTimer = 30;

    }

    @Override
    public boolean bursting() {
        return attackTimer >= 0;
    }

    @Override
    public void startAnimation(AnimationTemplate animationTemplate, int channel) {
        if (channel == 0) {
            explodingAnimation = new Animation(animationTemplate);
        }
    }

    public Animation getExplodingAnimation() {
        return explodingAnimation;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (explodingAnimation != null && !explodingAnimation.isDone()) {
                explodingAnimation.update();
            }
            int bleeding = entityData.get(DATA_BLEEDING);
            if (bleeding >= 0) {
                double xComponent = -Math.sin(Math.toRadians(getYRot()));
                double zComponent = Math.cos(Math.toRadians(getYRot()));
                if (!wasBleeding) {
                    wasBleeding = true;
                    for (int i = 0; i < 30; i++) {
                        double direction = Math.random() * 2 * Math.PI;
                        double xSpeed = -Math.sin(direction);
                        double zSpeed = Math.cos(direction);
                        for (int j = 0; j < 5 * (1 + bleeding); j++) {
                            level().addAlwaysVisibleParticle(BTVParticles.TEARSPILL.get(), getX(), getY() + 1.5, getZ(), xSpeed * (1.5 + bleeding / 3D) * (1 + Math.random()), 1.5, zSpeed * (1.5 + bleeding / 3D) * (1 + Math.random()));
                            level().addAlwaysVisibleParticle(BTVParticles.TEARSPILL.get(), getX(), getY() + 1.5, getZ(), xSpeed * (1.5 + bleeding / 3D) * (1 + Math.random()), 0, zSpeed * (1.5 + bleeding / 3D) * (1 + Math.random()));
                        }
                    }
                }
                //for (int i = 0; i < 2; i++) {
                //    level().addParticle(BTVParticles.TEARSPILL.get(), getX() + xComponent / 2, getY() + 1, getZ() + zComponent / 2, xComponent * (2 + Math.random()), 1.5, zComponent * (2 + Math.random()));
                //}

            }

        } else {
            if (deathTimer >= 0) {
                getNavigation().stop();
                //yBodyRot = fixedLookAngle;
                LivingEntity target = getTarget();
                if (target != null) {
                    getLookControl().setLookAt(target);
                }
                deathTimer--;
                if (deathTimer == 0) {
                    remove(RemovalReason.KILLED);
                }
            } else if (attackTimer >= 0) {
                getNavigation().stop();
                //yBodyRot = fixedLookAngle;
                LivingEntity target = getTarget();
                if (target != null) {
                    getLookControl().setLookAt(target);
                }
                attackTimer--;
                if (attackTimer == 0) {
                    TriggerData data = getTriggerData();
                    Burst burst = data.getBurst();
                    ArsenalEffect effect = data.getEffect();
                    if (burst != null) {
                        List<LivingEntity> hitEntities = burst.getHitEntities(this);
                        for (LivingEntity hitEntity : hitEntities) {
                            if (hitEntity != this && effect != null) {
                                effect.process(this, hitEntity);
                            }
                        }
                        entityData.set(DATA_BLEEDING, burst.getExtension());
                    } else {
                        entityData.set(DATA_BLEEDING, 2);
                    }
                    deathTimer = 15;
                }
            }
        }
    }
}
