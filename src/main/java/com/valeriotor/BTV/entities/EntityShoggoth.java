package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.animations.Animation;
import com.valeriotor.BTV.animations.AnimationRegistry;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShoggoth extends EntityLiving{
	
	private int animTicks = 0;
	private Animation openMouthAnim = null;
	private Animation eyeTentacleAnim = null;
	
	public EntityShoggoth(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if(world.isRemote) {
			if(openMouthAnim != null) {
				openMouthAnim.update();
				if(openMouthAnim.isDone()) openMouthAnim = null;
			}
			if(eyeTentacleAnim != null) {
				eyeTentacleAnim.update();
				if(eyeTentacleAnim.isDone()) eyeTentacleAnim = null;
			}
			animTicks++;
			animTicks%=1000;
			if(animTicks == 999 && openMouthAnim == null) this.openMouthAnim = new Animation(AnimationRegistry.shoggoth_open_mouth);
			if(animTicks%400 == 0 && animTicks != 0 && eyeTentacleAnim == null) this.eyeTentacleAnim = new Animation(AnimationRegistry.shoggoth_eye_tentacle);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getAnimTicks() {
		return animTicks;
	}
	
	@SideOnly(Side.CLIENT)
	public Animation getOpenMouthAnim() {
		return this.openMouthAnim;
	}
	
	@SideOnly(Side.CLIENT)
	public Animation getEyeTentacleAnim() {
		return this.eyeTentacleAnim;
	}
	
}
