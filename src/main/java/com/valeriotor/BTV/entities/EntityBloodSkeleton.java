package com.valeriotor.BTV.entities;

import java.util.UUID;

import com.valeriotor.BTV.animations.Animation;
import com.valeriotor.BTV.animations.AnimationRegistry;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBloodSkeleton extends EntityMob implements IPlayerGuardian{
	
	private int animCounter = -1;
	private Animation idleAnimation;
	
	public EntityBloodSkeleton(World worldIn) {
		super(worldIn);
	}

	@Override
	public EntityPlayer getMaster() {
		return null;
	}

	@Override
	public UUID getMasterID() {
		return null;
	}

	@Override
	public void setMaster(EntityPlayer p) {
		
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(world.isRemote) {
			if(animCounter > 0) {
				animCounter--;
				if(this.idleAnimation != null) {
					this.idleAnimation.update();
					if(this.idleAnimation.isDone()) this.idleAnimation = null;
				}
			} else {
				if(this.animCounter == 0 && Math.abs(this.motionX) < 0.005 && Math.abs(this.motionZ) < 0.005 ) this.idleAnimation = new Animation(AnimationRegistry.blood_skeleton_idle);
				animCounter = world.rand.nextInt(15)*2 + 120;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getAnimCounter() {
		return this.animCounter;
	}
	
	public Animation getIdleAnimation() {
		return this.idleAnimation;
	}

}
