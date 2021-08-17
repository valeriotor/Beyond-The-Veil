package com.valeriotor.beyondtheveil.entities.ictya;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.entities.EntityBloodZombie;
import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityAdeline extends EntityIctya implements IAnimatedAttacker {
    private Animation attackAnimation;

    public EntityAdeline(World worldIn) {
        super(worldIn);
//        setSize(1.5F, 1.2F);
        ignoreFrustumCheck = true;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new AIAttackMeleeAgainstLargeCreatures(this, 1.1, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIWander(this, 0.4D, 100));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(175.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(17D);
    }

    @Override
    public IctyaSize getSize() {
        return IctyaSize.MEDIUM;
    }

    @Override
    public double getFoodValue() {
        return 150;
    }

    @Override
    public double getMaxFood() {
        return 200;
    }

    @Override
    public double getFoodPer32Ticks() {
        return 1.4;
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (world.isRemote) {
            if (attackAnimation != null) {
                attackAnimation.update();
                if (attackAnimation.isDone()) {
                    attackAnimation = null;
                }
            }
        }
    }

    @Override
    public void swingArm(EnumHand hand) {
        super.swingArm(hand);
        if(!this.world.isRemote)
            this.sendAnimation(AnimationRegistry.getIdFromAnimation(AnimationRegistry.adeline_bite));
    }

    @Override
    public void setAttackAnimation(int id) {
        attackAnimation = new Animation(AnimationRegistry.getAnimationFromId(id));
    }

    @Override
    public Animation getAttackAnimation() {
        return attackAnimation;
    }
}
