package com.valeriotor.BTV.entities;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityCrazedWeeper extends EntityMob{

	private int animationTicks = 0;
	public EntityCrazedWeeper(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void onLivingUpdate() {
		if(this.world.isRemote) {
			this.animationTicks++;
			this.animationTicks%=200;
		}
		super.onLivingUpdate();
	}
	
	public int getAnimationTicks() {
		return this.animationTicks;
	}

}
