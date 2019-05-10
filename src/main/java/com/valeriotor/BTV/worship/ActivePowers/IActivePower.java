package com.valeriotor.BTV.worship.ActivePowers;

import com.valeriotor.BTV.worship.Deities;

import net.minecraft.entity.player.EntityPlayer;

public interface IActivePower {
	
	public boolean activatePower(EntityPlayer p);
	public int getCooldownTicks();
	public Deities getDeity();
	
	
}
