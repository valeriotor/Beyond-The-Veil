package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.client.ClientSetup;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.entity.ai.goals.MinionDefendMasterTargetGoal;
import com.valeriotor.beyondtheveil.entity.ai.goals.MinionHelpMasterTargetGoal;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class BloodSkeletonEntity extends Monster implements PlayerMinion {

    private Animation attackAnimation;
    private UUID masterId;

    public BloodSkeletonEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 12));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.targetSelector.addGoal(2, (new MinionDefendMasterTargetGoal<>(this, false)));
        this.targetSelector.addGoal(1, (new MinionHelpMasterTargetGoal<>(this, false)));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.8D, false));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, BloodPoolEntityType.BLOOD_SKELETON.getMaxHealth())
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 18.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 2);
    }

    public Animation getAttackAnimation() {
        return attackAnimation;
    }

    @Override
    public void tick() {
        super.tick();
        if(level().isClientSide()) {
            if (attackAnimation != null) {
                attackAnimation.update();
                if (attackAnimation.isDone()) {
                    attackAnimation = null;
                }
            } else {
                //attackAnimation = new Animation(AnimationRegistry.blood_skeleton_swing);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (masterId != null) {
            pCompound.putString("master", masterId.toString());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("master")) {
            masterId = UUID.fromString(pCompound.getString("master"));
        }
    }

    @Override
    public UUID getMasterID() {
        return masterId;
    }

    @Override
    public void setMasterID(UUID uuid) {
        masterId = uuid;
    }
}
