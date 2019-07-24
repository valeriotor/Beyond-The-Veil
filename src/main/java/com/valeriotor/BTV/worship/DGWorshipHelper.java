package com.valeriotor.BTV.worship;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.util.SyncUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class DGWorshipHelper {
	
	public static final int MAX_LEVEL = 5; // Will of course change
	
	public static void levelUp(EntityPlayer p) {
		int lvl = Deities.GREATDREAMER.cap(p).getLevel();
		int slugs = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.SLUGS, 0, false);
		if(slugs >= getRequiredSlugs(lvl) && hasRequiredQuest(p, lvl)) {
			Deities.GREATDREAMER.cap(p).addLevel();
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient("level", Deities.GREATDREAMER.cap(p).getLevel()), (EntityPlayerMP)p);
			p.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(PlayerDataLib.SLUGS, 0, false);
			SyncUtil.syncPlayerData(p);
		}
	}
	
	public static int getRequiredSlugs(int lvl) {
		return (int) Math.ceil(90*(1 - Math.pow(1.5, -lvl)));
	}
	
	public static boolean hasRequiredQuest(EntityPlayer p, int lvl) {
		if(lvl >= MAX_LEVEL) return false;
		return p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(getRequiredQuest(lvl));
	}
	
	public static String getRequiredQuest(int lvl) {
		switch(lvl) {
			case 2: return PlayerDataLib.FISHQUEST;
			case 3: return PlayerDataLib.RITUALQUEST;
			default: return null;
		}
	}
	
	public static boolean canTransform(EntityPlayer p) {
		return true; // will later use research to track progress
	}
	
	// Remove old keys from pData
}
