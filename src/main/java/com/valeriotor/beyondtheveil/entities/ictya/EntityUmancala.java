package com.valeriotor.beyondtheveil.entities.ictya;

import com.valeriotor.beyondtheveil.entities.projectile.EntityUmancalaBall;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityUmancala extends EntityIctya implements IRangedAttackMob {

    public EntityUmancala(World worldIn) {
        super(worldIn);
        setSize(3, 1.5F);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        tasks.addTask(0, new EntityAIAttackRanged(this, 0.27D, 18, 25));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIWander(this, 0.4D, 100));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(0);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.4D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
//        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(18D);
    }

    @Override
    public IctyaSize getSize() {
        return IctyaSize.MEDIUM;
    }

    @Override
    public double getFoodValue() {
        return 280;
    }

    @Override
    public double getMaxFood() {
        return 250;
    }

    @Override
    public double getFoodPer32Ticks() {
        return 2.4;
    }

    public void feed(double amount) {
        currentFood = Math.min(getMaxFood(), currentFood + amount);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        double d1 = 4.0D;
        Vec3d vec3d = getLook(1.0F);
        double d2 = target.posX - this.posX;
        double d3 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (0.5D + this.posY);
        double d4 = target.posZ - this.posZ;
//        world.playEvent((EntityPlayer)null, 1016, new BlockPos(this), 0);
        EntityUmancalaBall projectile = new EntityUmancalaBall(world, this, d2, d3, d4);
        projectile.posX = this.posX;
        projectile.posY = this.posY;
        projectile.posZ = this.posZ;
        world.spawnEntity(projectile);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }
}
