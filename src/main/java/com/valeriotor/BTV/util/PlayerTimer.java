package com.valeriotor.BTV.util;

import java.util.function.Consumer;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerTimer {
	public final EntityPlayer player;
	private int timer;
	private final Consumer<EntityPlayer> action;
	
	public PlayerTimer(EntityPlayer player) {
		this(player, null, 100);
	}
	
	public PlayerTimer(EntityPlayer player, Consumer<EntityPlayer> action) {
		this(player, null, 100);
	}
	
	public PlayerTimer(EntityPlayer player, Consumer<EntityPlayer> action, int timer) {
		this.player = player;
		this.timer = timer;
		this.action = action;
	}
	
	public boolean update() {
		if(timer > 0) {
			timer--;
			return false;
		} else if(timer == 0) {
			timer--;
			if(action != null) action.accept(player);
		}
		return true;
		
	}
	
	public boolean isDone() {
		return timer <= 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof PlayerTimer)) return false;
		PlayerTimer cov = (PlayerTimer)obj;
		if(cov == this) return true;
		if(cov.player.equals(this.player)) return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return player.hashCode();
	}
}
