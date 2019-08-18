package com.valeriotor.BTV.events;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchEvent.Research;

public class ResearchEvents {
	
	@SubscribeEvent
	public void wakeUpEvent(PlayerWakeUpEvent event) {
		EntityPlayer p = event.getEntityPlayer();
		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(p);
		if(p.world.getWorldTime() < 23900) return;
		if(!k.isResearchComplete("FIRSTDREAMS")) {
			ThaumcraftApi.internalMethods.progressResearch(p, "didDream");
		}
	}
	
	private Set<String> dialogueResearches = ImmutableSet.of(
			"f_AlienisDream",
			"FISHINGHAMLET",
			"IDOL",
			"SLUGS",
			"CANOE");
	
	public void checkResearches(EntityPlayer p) {
		for(String s : dialogueResearches) {
			if(ThaumcraftCapabilities.knowsResearchStrict(p, s)) dialogueResearches(p, s);
		}
	}
	
	@SubscribeEvent
	public void researchEvent(Research event) {
		switch(event.getResearchKey()) {
		case "!minecraft:water_bucket0":
			ThaumcraftApi.internalMethods.addKnowledge(event.getPlayer(), EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("BEYOND_THE_VEIL"), 16);
			break;
		default: dialogueResearches(event.getPlayer(), event.getResearchKey());
				
		}	
	}
	
	public void dialogueResearches(EntityPlayer p, String s) {
		switch(s) {
		case "f_AlienisDream":
			p.getCapability(PlayerDataProvider.PLAYERDATA, null).addString(PlayerDataLib.SEEKSKNOWLEDGE, false);
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient(PlayerDataLib.SEEKSKNOWLEDGE), (EntityPlayerMP)p);
			break;
		case "FISHINGHAMLET":
			if(ThaumcraftCapabilities.knowsResearchStrict(p, "FISHINGHAMLET@1")) {
				if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("dialoguegratitude")) {
					p.getCapability(PlayerDataProvider.PLAYERDATA, null).addString("dialoguedreamer", false);
					BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient("dialoguedreamer"), (EntityPlayerMP)p);
				}	
			}
			break;
		case "IDOL":
			if(ThaumcraftCapabilities.knowsResearchStrict(p, "IDOL@0"))
				unlockDialogue(p, "newyou", "trustedbar", "trustedcar");
			break;
		case "SLUGS":
			if(ThaumcraftCapabilities.knowsResearchStrict(p, "SLUGS@0"))
				unlockDialogue(p, "impressed", "canoecar");			
			break;
		case "CANOE":
			if(ThaumcraftCapabilities.knowsResearchStrict(p, "CANOE@1")) {
				if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("dialoguecanoe")) { // Otherwise handled by normal dialogue unlocking
					unlockDialogue(p, "ritual");
				}			
			}
			break;
		}
	}
	
	private void unlockDialogue(EntityPlayer p, String... names) {
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		for(String name : names) {
			if(!data.getString(name)) {
				data.addString("dialogue".concat(name), false);
				BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient("dialogue".concat(name)), (EntityPlayerMP)p);	
			}
		}	
	}
	
}
