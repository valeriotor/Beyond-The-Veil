package com.valeriotor.beyondtheveil.entity.ictya;

import com.valeriotor.beyondtheveil.entity.DeepOneEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JellyEntity extends Monster {

    private int timeToDie = 320;
    private ManOWarEntity master;

    public JellyEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 45, 10, 0.7F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0, false));

    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.165D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5);
    }

    public void setMaster(ManOWarEntity master) {
        this.master = master;
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity instanceof LivingEntity e && random.nextInt(10) < 3) {
            e.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * 5, 2));
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            timeToDie--;
            if (timeToDie <= 0 || master == null || master.isDeadOrDying()) {
                discard();
            } else {
                setTarget(master.getTarget());
            }
        }
    }

    @Override
    public boolean killedEntity(ServerLevel pLevel, LivingEntity e) {
        boolean flag = super.killedEntity(pLevel, e);
        if (master != null) {
            flag &= master.killedEntity(pLevel, e);
        }
        return flag;
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
    public void travel(@NotNull Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(pTravelVector);
        }

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("timeToDie")) {
            timeToDie = pCompound.getInt("timeToDie");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("timeToDie", timeToDie);
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

}
