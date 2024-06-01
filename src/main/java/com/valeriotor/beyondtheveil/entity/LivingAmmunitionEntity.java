package com.valeriotor.beyondtheveil.entity;

import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerDataProvider;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.entity.ai.goals.LivingAmmunitionGoal;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.surgery.arsenal.ArsenalEffect;
import com.valeriotor.beyondtheveil.surgery.arsenal.Burst;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LivingAmmunitionEntity extends PathfinderMob implements VillagerDataHolder, AnimatedEntity, AmmunitionEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityDataAccessor<VillagerData> DATA_VILLAGER_DATA = SynchedEntityData.defineId(LivingAmmunitionEntity.class, EntityDataSerializers.VILLAGER_DATA);
    private static final EntityDataAccessor<Boolean> DATA_BLEEDING = SynchedEntityData.defineId(LivingAmmunitionEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean wasBleeding = false;
    private int attackTimer = -1;
    private int deathTimer = -1;

    public LivingAmmunitionEntity(EntityType<? extends PathfinderMob> type, Level world) {
        super(type, world);
    }

    private Animation explodingAnimation;
    private Animation explodingAnimationBrokenBody;

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        //this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 12));
        //this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(0, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(2, new LivingAmmunitionGoal<>(this, 1.8D, false));
    }

    @Override
    public VillagerData getVillagerData() {
        return this.entityData.get(DATA_VILLAGER_DATA);
    }


    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        VillagerData.CODEC.encodeStart(NbtOps.INSTANCE, this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent((p_204072_) -> pCompound.put("VillagerData", p_204072_));
        pCompound.putInt("deathTimer", deathTimer);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("VillagerData", 10)) {
            DataResult<VillagerData> dataresult = VillagerData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, pCompound.get("VillagerData")));
            dataresult.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
        }
        if (pCompound.contains("deathTimer")) {
            deathTimer = pCompound.getInt("deathTimer");
        }

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
        this.entityData.define(DATA_BLEEDING, false);
    }

    public void setVillagerData(VillagerData p_34376_) {
        this.entityData.set(DATA_VILLAGER_DATA, p_34376_);
    }

    public boolean isExploding() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        // TODO lock in rotation and position
        if (level().isClientSide) {
            if (explodingAnimation != null && !explodingAnimation.isDone()) {
                explodingAnimation.update();
                explodingAnimationBrokenBody.update();
            }// else {
            //    explodingAnimation = new Animation(AnimationRegistry.ammunition_explode);
            //    explodingAnimationBrokenBody = new Animation(AnimationRegistry.ammunition_explode_body);
            //}
            //level().addParticle(BTVParticles.BLOODSPILL.get(), getX(), getY() + 2, getZ(), 5, 5, 0);
            if (entityData.get(DATA_BLEEDING)) {
                double xComponent = -Math.sin(Math.toRadians(getYRot()));
                double zComponent = Math.cos(Math.toRadians(getYRot()));
                if (!wasBleeding) {
                    wasBleeding = true;
                    for (int i = 0; i < 30; i++) {
                        double direction = Math.random() * 2 * Math.PI;
                        double xSpeed = -Math.sin(direction);
                        double zSpeed = Math.cos(direction);
                        for (int j = 0; j < 5; j++) {
                            level().addParticle(BTVParticles.BLOODSPILL.get(), getX(), getY() + 1, getZ(), xSpeed * (1 + Math.random()), 1.5, zSpeed * (1 + Math.random()));
                            level().addParticle(BTVParticles.BLOODSPILL.get(), getX(), getY() + 1, getZ(), xSpeed * (1 + Math.random()), 0, zSpeed * (1 + Math.random()));
                        }
                    }
                }
                for (int i = 0; i < 2; i++) {
                    level().addParticle(BTVParticles.BLOODSPILL.get(), getX() + xComponent / 2, getY() + 1, getZ() + zComponent / 2, xComponent * (2 + Math.random()), 1.5, zComponent * (2 + Math.random()));
                }

            }

        } else {
            if (deathTimer >= 0) {
                getNavigation().stop();
                deathTimer--;
                if (deathTimer == 0) {
                    remove(RemovalReason.KILLED);
                }
            } else if (attackTimer >= 0) {
                getNavigation().stop();
                attackTimer--;
                if (attackTimer == 0) {
                    TriggerData data = getTriggerData();
                    Burst burst = data.getBurst();
                    ArsenalEffect effect = data.getEffect();
                    if (burst != null) {
                        burst.createParticles(this);
                        List<LivingEntity> hitEntities = burst.getHitEntities(this);
                        for (LivingEntity hitEntity : hitEntities) {
                            if (hitEntity != this && effect != null) {
                                effect.process(this, hitEntity);
                            }
                        }
                    }
                    deathTimer = 15;
                    entityData.set(DATA_BLEEDING, true);
                }
            }
        }
    }

    public Animation getExplodingAnimation() {
        return explodingAnimation;
    }

    public Animation getExplodingAnimationBrokenBody() {
        return explodingAnimationBrokenBody;
    }

    @Override
    public void startAnimation(AnimationTemplate animationTemplate, int channel) {
        switch (channel) {
            case 0:
                explodingAnimation = new Animation(animationTemplate);
            case 1:
                explodingAnimationBrokenBody = new Animation(animationTemplate);
        }
    }

    @Override
    public TriggerData getTriggerData() {
        return getCapability(TriggerDataProvider.TRIGGER_DATA).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<GenericToClientPacket> getAnimationPackets() {
        List<GenericToClientPacket> animationPackets = new ArrayList<>();
        animationPackets.add(GenericToClientPacket.startAnimation(AnimationRegistry.ammunition_explode, getId(), 0));
        animationPackets.add(GenericToClientPacket.startAnimation(AnimationRegistry.ammunition_explode_body, getId(), 1));
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
}
