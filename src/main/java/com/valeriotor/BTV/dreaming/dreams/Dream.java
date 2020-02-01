package com.valeriotor.BTV.dreaming.dreams;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class Dream{
	
	public final int priority;
	public final String name;
	
	public Dream(String name, int priority) {
		this.priority = priority;
		this.name = name;
	}
	
	public abstract boolean activate(EntityPlayer p, World w);
	
	public String getName() {
		return this.name;
	}
	
	public boolean activateBottle(EntityPlayer p, World w) {
		return this.activate(p, w);
	}
	
}
