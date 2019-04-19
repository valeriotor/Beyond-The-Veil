package com.valeriotor.BTV.util;

import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.capabilities.PlayerDataHandler;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DGWorshipHandler {
	
	public static final int MAX_LEVEL = 5; // Will of course change
	
	public static void levelUp(EntityPlayer p) {
		int lvl = p.getCapability(DGProvider.LEVEL_CAP, null).getLevel();
		int slugs = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.SLUGS, 0, false);
		if(slugs >= getRequiredSlugs(lvl) && hasRequiredQuest(p, lvl)) {
			p.getCapability(DGProvider.LEVEL_CAP, null).addLevel();
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient("level", p.getCapability(DGProvider.LEVEL_CAP, null).getLevel()), (EntityPlayerMP)p);
			p.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(PlayerDataLib.SLUGS, 0, false);
			PlayerDataHandler.syncPlayerData(p);
		}
	}
	
	public static int getRequiredSlugs(int lvl) {
		return 30 + lvl*10;
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
	
	// Remove old keys from pData
}
