package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.animations.Animation;
import com.valeriotor.BTV.animations.AnimationRegistry;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBloodZombie extends EntityMob{
	
	
	private Animation idle_animation;
	private int anim_counter = 0;
	
	public EntityBloodZombie(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void onUpdate() {
		if(world.isRemote) {
			if(anim_counter > 0) {
				anim_counter--;
				if(this.idle_animation != null) {
					this.idle_animation.update();
					if(this.idle_animation.isDone()) this.idle_animation = null;
				}	
			} else {
				this.idle_animation = new Animation(AnimationRegistry.blood_zombie_idle);
				anim_counter = world.rand.nextInt(3000) + 800;
			}
		}
		super.onUpdate();
	}
	
	@SideOnly(Side.CLIENT)
	public int getAnimCounter() {
		return this.anim_counter;
	}
	
	@SideOnly(Side.CLIENT)
	public Animation getIdleAnimation() {
		return this.idle_animation;
	}

}
