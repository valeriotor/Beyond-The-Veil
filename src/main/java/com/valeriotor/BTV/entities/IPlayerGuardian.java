package com.valeriotor.BTV.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerGuardian {
	
	public EntityPlayer getMaster();
	public default void setTarget(EntityLivingBase e) {
		if(this instanceof EntityLiving) {
			EntityLiving ent = (EntityLiving)this;
			if(e != ent) ent.setAttackTarget(e);
		}
	}
}
