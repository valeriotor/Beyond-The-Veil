package com.valeriotor.beyondtheveil.entity.ictya;

import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class SeaSnakeEntity extends IctyaEntity implements AnimatedEntity {

    public SeaSnakeEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.25D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D);
    }

    @Override
    public void startAnimation(AnimationTemplate animationTemplate, int channel) {

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
        return 150;
    }

    @Override
    public double getFoodPer32Ticks() {
        return 2.0;
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (super.doHurtTarget(pEntity)) {
            if (pEntity instanceof LivingEntity e) {
                int i = 0;
                if (this.level().getDifficulty() == Difficulty.NORMAL) {
                    i = 7;
                } else if (this.level().getDifficulty() == Difficulty.HARD) {
                    i = 15;
                }

                if (i > 0) {
                    e.addEffect(new MobEffectInstance(MobEffects.POISON, i * 20, 0), this);
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
