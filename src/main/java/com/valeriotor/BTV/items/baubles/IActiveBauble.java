package com.valeriotor.BTV.items.baubles;

import net.minecraft.entity.player.EntityPlayer;

public interface IActiveBauble {
	
	public boolean activate(EntityPlayer p);
	public int getCooldown();
}
