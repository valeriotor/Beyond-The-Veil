package com.valeriotor.beyondtheveil.entity.ictya;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class OctidEntity extends IctyaEntity {

    public OctidEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.6D, 0.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, IctyaEntity.class, 12.0F, 1.2F, 1.2F, this::shouldFlee));
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.65F;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.75D);
    }

    @Override
    public IctyaSize getSize() {
        return IctyaSize.TINY;
    }

    @Override
    public double getFoodValue() {
        return 15;
    }

    @Override
    public double getMaxFood() {
        return 10;
    }

    @Override
    public double getFoodPer32Ticks() {
        return 0;
    }
}
