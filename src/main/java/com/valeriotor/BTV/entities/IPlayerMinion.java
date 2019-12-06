package com.valeriotor.BTV.entities;

import java.util.UUID;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerMinion {
	public EntityPlayer getMaster();
	public UUID getMasterID();
	public void setMaster(EntityPlayer p);
}
