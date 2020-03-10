package com.valeriotor.beyondtheveil.items;

import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.util.SyncUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public interface IArtifactItem {
	public default void unlockData(EntityPlayer p) {
		if(this instanceof Item) {
			String s = "artifact".concat(((Item)this).getRegistryName().getResourcePath());
			if(!p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(s)) {
				SyncUtil.addStringDataOnServer(p, false, s);
				SyncUtil.addStringDataOnServer(p, false, "baubleresearch");
			}
		}
	}
}
