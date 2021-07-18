package com.valeriotor.beyondtheveil.entities.ictya;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
import com.valeriotor.beyondtheveil.world.DimensionRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySandflatter extends EntityIctya implements IAnimatedAttacker {

    private boolean mustBeRemoved = false;
    private Animation attackAnimation;

    public EntitySandflatter(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void initEntityAI() {
//        super.initEntityAI();
    }

    @Override
    public IctyaSize getSize() {
        return IctyaSize.LARGE;
    }

    @Override
    public double getFoodValue() {
        return 400;
    }

    @Override
    public double getMaxFood() {
        return 500;
    }

    @Override
    public double getFoodPer32Ticks() {
        return 0.1;
    }

    /*@Override
    public boolean getCanSpawnHere() {
        return this.posY < (double)this.world.getSeaLevel()-7
                && world.getBlockState(getPosition()).getBlock() == Blocks.WATER
                && (world.getBlockState(getPosition().down()).getBlock() == BlockRegistry.DarkSand
                 || world.getBlockState(getPosition().down().down()).getBlock() == BlockRegistry.DarkSand);
    }*/

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        if(dimension == DimensionRegistry.ARCHE.getId()) {
            boolean success = false;
            for (int i = 0; i < posY-5; i++) {
                if(world.getBlockState(new BlockPos(posX, posY-i, posZ)).getBlock() == BlockRegistry.DarkSand) {
                    boolean goodSpot = true;
                    for (int j = -2; j <= 2; j++) {
                        for (int k = -2; k <= 2; k++) {
                            if(world.getBlockState(new BlockPos(posX+j, posY-i, posZ+k)).getBlock() != BlockRegistry.DarkSand) {
                                goodSpot = false;
                                break;
                            }
                            if(!goodSpot) break;
                        }
                    }
                    if(!goodSpot) break;
                    setPosition(posX, posY-i+1, posZ);
                    if(!world.getEntities(EntitySandflatter.class, e -> e != null && (this.getDistance(e) < 20 && e != this && e.isAddedToWorld())).isEmpty()) {
                        mustBeRemoved = true;
                        break;
                    }
                    success = true;
                    break;
                }
            }
            if(!success) {
                mustBeRemoved = true;
            }
            return livingdata;
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if(mustBeRemoved)
            world.removeEntity(this);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(world.isRemote) {
            if(attackAnimation != null) {
                attackAnimation.update();
                if(attackAnimation.isDone())
                    attackAnimation = null;
            } else {
                attackAnimation = new Animation(AnimationRegistry.sandflatter_ambush);
            }
        }
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
