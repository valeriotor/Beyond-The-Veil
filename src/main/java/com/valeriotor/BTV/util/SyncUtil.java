package com.valeriotor.BTV.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageRemoveStringToClient;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.network.MessageSyncTransformedPlayer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class SyncUtil {
	
	
	public static void syncPlayerData(EntityPlayer p) {
		syncCapabilityData(p);
		syncTransformData(p);
	}
	
	public static void syncCapabilityData(EntityPlayer p) {
		List<String> strings = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getStrings(false);
		HashMap<String, Integer> ints = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getInts(false);
		BTVPacketHandler.INSTANCE.sendTo(new MessageRemoveStringToClient(PlayerDataLib.ALL), (EntityPlayerMP)p);
		for(String string : strings) {
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient(string), (EntityPlayerMP)p);
		}
			
		for(Entry<String, Integer> entry : ints.entrySet()) {
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient(entry.getKey(), entry.getValue()), (EntityPlayerMP)p);	
		}
	}
	
	public static void syncTransformData(EntityPlayer p) {
		BTVPacketHandler.INSTANCE.sendTo(new MessageSyncTransformedPlayer(true), (EntityPlayerMP)p);
		for(EntityPlayer player : p.getServer().getPlayerList().getPlayers()) {
			if(player.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) {
				BTVPacketHandler.INSTANCE.sendTo(new MessageSyncTransformedPlayer(false, player.getPersistentID(), true), (EntityPlayerMP)p);
			}
		}
	}
	
}
