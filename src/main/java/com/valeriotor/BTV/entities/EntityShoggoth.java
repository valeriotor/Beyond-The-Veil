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
			animTicks++;
			animTicks%=1000;
			if(animTicks == 999 && openMouthAnim == null) this.openMouthAnim = new Animation(AnimationRegistry.shoggoth_open_mouth);
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
	
}
