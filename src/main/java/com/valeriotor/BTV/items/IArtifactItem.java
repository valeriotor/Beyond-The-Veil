package com.valeriotor.BTV.items;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.util.SyncUtil;

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
