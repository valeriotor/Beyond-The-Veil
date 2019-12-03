package com.valeriotor.BTV.entities;

import java.util.UUID;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerGuardian {
	
	public EntityPlayer getMaster();
	public UUID getMasterID();
	public void setMaster(EntityPlayer p);
	public default void setTarget(EntityLivingBase e) {
		if(this instanceof EntityLiving) {
			EntityLiving ent = (EntityLiving)this;
			if(e != ent) ent.setAttackTarget(e);
		}
	}
}
