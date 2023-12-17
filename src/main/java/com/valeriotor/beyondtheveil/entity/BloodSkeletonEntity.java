package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.client.ClientSetup;
import com.valeriotor.beyondtheveil.client.animation.Animation;
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

public class BloodSkeletonEntity extends Monster {

    private Animation attackAnimation;

    public BloodSkeletonEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 12));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.8D, false));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 70.0D)
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
}
