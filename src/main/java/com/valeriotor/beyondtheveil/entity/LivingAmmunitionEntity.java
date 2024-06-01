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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Set;

public class LivingAmmunitionEntity extends PathfinderMob implements VillagerDataHolder, AnimatedEntity, AmmunitionEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityDataAccessor<VillagerData> DATA_VILLAGER_DATA = SynchedEntityData.defineId(CrawlerEntity.class, EntityDataSerializers.VILLAGER_DATA);

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
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("VillagerData", 10)) {
            DataResult<VillagerData> dataresult = VillagerData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, pCompound.get("VillagerData")));
            dataresult.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
        }

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
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
        if (level().isClientSide) {
            if (explodingAnimation != null && !explodingAnimation.isDone()) {
                explodingAnimation.update();
                explodingAnimationBrokenBody.update();
            }// else {
            //    explodingAnimation = new Animation(AnimationRegistry.ammunition_explode);
            //    explodingAnimationBrokenBody = new Animation(AnimationRegistry.ammunition_explode_body);
            //}
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
}
