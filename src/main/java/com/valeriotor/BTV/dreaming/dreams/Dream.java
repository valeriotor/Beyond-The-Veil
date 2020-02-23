package com.valeriotor.BTV.dreaming.dreams;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class Dream{
	
	public final int priority;
	public final String name;
	
	public Dream(String name, int priority) {
		this.priority = priority;
		this.name = name;
	}
	
	public boolean activate(EntityPlayer p, World w) {
		return this.activatePos(p, w, p.getPosition());
	}
	public boolean activatePlayer(EntityPlayer caster, EntityPlayer target, World w) {
		return this.activatePos(caster, w, target.getPosition());
	}
	public abstract boolean activatePos(EntityPlayer p, World w, BlockPos pos);
	
	public String getName() {
		return this.name;
	}
		
}
