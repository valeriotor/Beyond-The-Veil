package com.valeriotor.BTV.util;

import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageCovenantData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PlayerTimer {
	public final EntityPlayer player;
	private int timer = 100;
	
	public PlayerTimer(EntityPlayer player) {
		this.player = player;
	}
	
	public PlayerTimer(EntityPlayer player, int timer) {
		this.player = player;
		this.timer = timer;
	}
	
	public boolean update() {
		if(timer > 0) {
			timer--;
			return false;
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
