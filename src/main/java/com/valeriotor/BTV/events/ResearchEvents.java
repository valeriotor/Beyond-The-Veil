package com.valeriotor.BTV.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.internal.DummyInternalMethodHandler;
import thaumcraft.api.internal.IInternalMethodHandler;

public class ResearchEvents {
	
	@SubscribeEvent
	public void wakeUpEvent(PlayerWakeUpEvent event) {
		EntityPlayer p = event.getEntityPlayer();
		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(p);
		if(p.world.getWorldTime() < 23900) return;
		if(!k.isResearchComplete("FIRSTDREAMS")) {
			ThaumcraftApi.internalMethods.progressResearch(p, "m_didDream");
		}
	}
	
	
}
