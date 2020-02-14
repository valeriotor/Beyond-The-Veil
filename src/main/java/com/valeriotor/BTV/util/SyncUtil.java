package com.valeriotor.BTV.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.capabilities.ResearchProvider;
import com.valeriotor.BTV.events.ServerTickEvents;
import com.valeriotor.BTV.events.special.AzacnoParasiteEvents;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageRemoveStringToClient;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.network.MessageSyncIntDataToServer;
import com.valeriotor.BTV.network.MessageSyncParasitePlayer;
import com.valeriotor.BTV.network.MessageSyncStringDataToServer;
import com.valeriotor.BTV.network.MessageSyncTransformedPlayer;
import com.valeriotor.BTV.network.research.MessageSyncResearchToClient;
import com.valeriotor.BTV.network.research.ResearchSyncer;
import com.valeriotor.BTV.research.ResearchStatus;
import com.valeriotor.BTV.worship.AzacnoParasite;
import com.valeriotor.BTV.worship.ActivePowers.TransformDeepOne;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SyncUtil {
	
	
	public static void syncPlayerData(EntityPlayer p) {
		syncCapabilityData(p);
		syncTransformData(p);
		syncParasiteData(p);
		syncResearchData(p);
		if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) {
			TransformDeepOne.applyAttributes(p);
		}
	}
	
	public static void syncCapabilityData(EntityPlayer p) {
		Set<String> strings = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getStrings(false);
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
		if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED))
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncTransformedPlayer(p.getPersistentID(), true), (EntityPlayerMP)p);
	}
	
	public static void syncParasiteData(EntityPlayer p) {
		AzacnoParasite ap = AzacnoParasiteEvents.parasites.get(p.getPersistentID());
		if(ap != null && ap.renderParasite()) {
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncParasitePlayer(p.getPersistentID(), true), (EntityPlayerMP)p);
		}
	}
	
	public static void syncResearchData(EntityPlayer p) {
		HashMap<String, ResearchStatus> map = p.getCapability(ResearchProvider.RESEARCH, null).getResearches();
		if(map.isEmpty()) p.getCapability(ResearchProvider.RESEARCH, null).populate();
		for(Entry<String, ResearchStatus> entry : map.entrySet()) {
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncResearchToClient(new ResearchSyncer(entry.getKey()).setStatus(entry.getValue())), (EntityPlayerMP)p);
		}
	}
	
	public static void addStringDataOnServer(EntityPlayer p, boolean temporary, String string) {
		p.getCapability(PlayerDataProvider.PLAYERDATA, null).addString(string, temporary);
		BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient(string), ((EntityPlayerMP)p));
	}
	
	public static void removeStringDataOnServer(EntityPlayer p, String string) {
		p.getCapability(PlayerDataProvider.PLAYERDATA, null).removeString(string);
		BTVPacketHandler.INSTANCE.sendTo(new MessageRemoveStringToClient(string), ((EntityPlayerMP)p));
	}
	
	public static void addIntDataOnServer(EntityPlayer p, boolean temporary, String key, int val) {
		p.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(key, val, temporary);
		BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient(key, val), (EntityPlayerMP)p);
	}
	
	public static int getOrSetIntDataOnServer(EntityPlayer p, boolean temporary, String key, int val) {
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		if(data.getInts(temporary).containsKey(key)) {
			return data.getInteger(key);
		} else {
			addIntDataOnServer(p, temporary, key, val);
			return val;
		}
	}
	
	/*public static void removeIntDataOnServer(EntityPlayer p, String key) {
		p.getCapability(PlayerDataProvider.PLAYERDATA, null).removeInteger(key);
		BTVPacketHandler.INSTANCE.sendTo(new MessageRemoveIntToClient(key), ((EntityPlayerMP)p));
	}*/
	
	public static int incrementIntDataOnServer(EntityPlayer p, boolean temporary, String key, int amount, int defaultVal) {
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		int val = data.incrementOrSetInteger(key, amount, defaultVal, temporary);
		BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient(key, data.getInteger(key) + amount), (EntityPlayerMP) p);
		return val;
	}
	
	@SideOnly(Side.CLIENT)
	public static void addStringDataOnClient(EntityPlayer p, boolean temporary, String string) {
		p.getCapability(PlayerDataProvider.PLAYERDATA, null).addString(string, temporary);
		BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncStringDataToServer(string));
	}
	
	/*@SideOnly(Side.CLIENT)
	public static void removeStringDataOnClient(EntityPlayer p, String string) {
		p.getCapability(PlayerDataProvider.PLAYERDATA, null).removeString(string);
		BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncStringDataToServer(true, string));
	}*/
	
	@SideOnly(Side.CLIENT)
	public static void addIntDataOnClient(EntityPlayer p, boolean temporary, String key, int val) {
		p.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(key, val, temporary);
		BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncIntDataToServer(key, val));
	}
	
	
}
