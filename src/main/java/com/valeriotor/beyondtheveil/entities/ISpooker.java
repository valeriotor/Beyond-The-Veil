package com.valeriotor.beyondtheveil.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISpooker {
	
	public void setSpooking(boolean spook);
	public SoundEvent getSound();
	public void spookSelf();
	public int getSpookCooldown();
	public default boolean shouldSpook() {
		if(this instanceof EntityLiving) {
			EntityLiving ent = (EntityLiving)this;
			if(ent.getAttackTarget() == null) return false;
			if(this.getSpookCooldown() > 0) return false;
			float dist = ent.getAttackTarget().getDistance(ent);
			if(dist < 3 || dist > 20) return false;
			return true;
		}
		return false;
	}
	public default BlockPos getPosition() {
		if(this instanceof EntityLiving)
			return ((EntityLiving)this).getPosition();
		return null;
	}
	public default float getDistance(EntityLivingBase e) {
		if(this instanceof EntityLiving)
			return ((EntityLiving)this).getDistance(e);
		return 0;
	}
	public default World getWorld() {
		if(this instanceof EntityLiving)
			return ((EntityLiving)this).world;
		return null;
	}
}
