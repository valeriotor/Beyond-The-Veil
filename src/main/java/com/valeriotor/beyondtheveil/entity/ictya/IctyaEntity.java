package com.valeriotor.beyondtheveil.entity.ictya;

import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import com.valeriotor.beyondtheveil.entity.DamageCapper;
import com.valeriotor.beyondtheveil.entity.DeepOneEntity;
import com.valeriotor.beyondtheveil.entity.ai.goals.DeepOneContact1Goal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class IctyaEntity extends Monster implements AnimatedEntity, DamageCapper {
    protected double currentFood = getMaxFood() / 5;

    protected IctyaEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        //this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.7F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        //this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.8D, false));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this) {
            @Override
            protected boolean canAttack(@Nullable LivingEntity pPotentialTarget, @NotNull TargetingConditions pTargetPredicate) {
                return super.canAttack(pPotentialTarget, pTargetPredicate) && shouldDefend(pPotentialTarget);
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, false, false, this::shouldAttack));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, DeepOneEntity.class, 10, false, false, this::shouldAttack));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IctyaEntity.class, 10, false, false, this::shouldAttack));
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return false;
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }

    @Override
    public float getDamageCap() {
        return 30;
    }

    public int getSizeInt() {
        return getSize().ordinal();
    }

    public abstract IctyaSize getSize();

    public abstract double getFoodValue();

    public abstract double getMaxFood();

    public abstract double getFoodPer32Ticks();

    public double getCurrentFoodRatio() {
        return currentFood / getMaxFood();
    }

    @Override
    public void tick() {
        super.tick();
        if ((tickCount & 31) == 0) {
            on32Ticks();
        }
    }

    protected void on32Ticks() {
        if (!level().isClientSide && getSize() != IctyaSize.TINY) {
            if (getCurrentFoodRatio() > 0.67)
                heal(Math.max(3, Math.min(getMaxHealth() / 20, 12)));

            if (currentFood >= getFoodPer32Ticks()) {
                currentFood -= getFoodPer32Ticks();
            } else {
                hurt(damageSources().starve(), 3);
            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("food")) {
            currentFood = pCompound.getDouble("food");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putDouble("food", currentFood);
    }

    public int compareSizeTo(LivingEntity entity) {
        if (entity instanceof Player || entity instanceof DeepOneEntity)
            return getSizeInt() - IctyaSize.MEDIUM.ordinal();
        else if (entity instanceof IctyaEntity other)
            return getSizeInt() - other.getSizeInt();
        return getSizeInt();
    }

    protected boolean shouldDefend(LivingEntity attacker) {
        if (getSize() == IctyaSize.TINY) return false;
        int diff = this.compareSizeTo(attacker);
        return diff >= -1;
    }

    protected boolean shouldAttack(LivingEntity attacked) {
        if (getSize() == IctyaSize.TINY) return false;
        int diff = this.compareSizeTo(attacked);
        if (diff == 3) {
            return getCurrentFoodRatio() < 0.4;
        } else if (diff == 2) {
            return getCurrentFoodRatio() < 0.85;
        } else if (diff == 1) {
            return getCurrentFoodRatio() < 0.67;
        } else if (diff == 0) {
            return getCurrentFoodRatio() < 0.5;
        } else if (diff == -1) {
            return getCurrentFoodRatio() < 0.1 && attacked.getHealth() / attacked.getMaxHealth() < 0.3F;
        }
        return false;
    }

    protected boolean shouldFlee(LivingEntity spooker) {
        // TODO if(spooker instanceof EntityAdeline) return false;
        int sizeCompare = this.compareSizeTo(spooker);
        return sizeCompare <= -2 || (this.getSize() == IctyaSize.TINY && sizeCompare == -1);
    }

    @Override
    public void travel(@NotNull Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            //if (this.getTarget() == null) {
            //    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            //}
        } else {
            super.travel(pTravelVector);
        }

    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

}
