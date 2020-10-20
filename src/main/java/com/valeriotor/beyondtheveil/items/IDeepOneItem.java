package com.valeriotor.beyondtheveil.items;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;

import net.minecraft.entity.player.EntityPlayer;

public interface IDeepOneItem {
	public default boolean canHold(EntityPlayer p, IPlayerData data) {
		return true;
	}
}
