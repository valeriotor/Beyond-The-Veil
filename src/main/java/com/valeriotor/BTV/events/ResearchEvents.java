package com.valeriotor.BTV.events;

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
	
	@SubscribeEvent
	public void researchEvent(Research event) {
		switch(event.getResearchKey()) {
		case "!minecraft:water_bucket0":
			ThaumcraftApi.internalMethods.addKnowledge(event.getPlayer(), EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("BEYOND_THE_VEIL"), 16);
			break;
		case "f_AlienisDream":
			event.getPlayer().getCapability(PlayerDataProvider.PLAYERDATA, null).addString(PlayerDataLib.SEEKSKNOWLEDGE, false);
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient(PlayerDataLib.SEEKSKNOWLEDGE), (EntityPlayerMP)event.getPlayer());
			break;
		case "FISHINGHAMLET":
			if(ThaumcraftCapabilities.knowsResearchStrict(event.getPlayer(), "FISHINGHAMLET@1")) {
				if(event.getPlayer().getCapability(PlayerDataProvider.PLAYERDATA, null).getString("dialoguegratitude")) {
					event.getPlayer().getCapability(PlayerDataProvider.PLAYERDATA, null).addString("dialoguedreamer", false);
					BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient("dialoguedreamer"), (EntityPlayerMP)event.getPlayer());
				}	
			}
			break;
		case "IDOL":
			if(ThaumcraftCapabilities.knowsResearchStrict(event.getPlayer(), "IDOL@0"))
				unlockDialogue(event.getPlayer(), "newyou", "trustedbar");
			break;
		case "SLUGS":
			if(ThaumcraftCapabilities.knowsResearchStrict(event.getPlayer(), "IDOL@0"))
				unlockDialogue(event.getPlayer(), "impressed");			
			break;
		case "CANOE":
			if(ThaumcraftCapabilities.knowsResearchStrict(event.getPlayer(), "CANOE@1")) {
				if(event.getPlayer().getCapability(PlayerDataProvider.PLAYERDATA, null).getString("dialoguecanoe")) {
					unlockDialogue(event.getPlayer(), "ritual");
				}			
			}
			break;
				
		}	
	}
	
	private void unlockDialogue(EntityPlayer p, String... names) {
		for(String name : names) {
			p.getCapability(PlayerDataProvider.PLAYERDATA, null).addString("dialogue".concat(name), false);
			BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient("dialogue".concat(name)), (EntityPlayerMP)p);	
		}	
	}
	
}
