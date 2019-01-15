package com.valeriotor.BTV.events;

import com.valeriotor.BTV.research.BTVTab;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.internal.DummyInternalMethodHandler;
import thaumcraft.api.internal.IInternalMethodHandler;
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
		}
	}
	
	
}
