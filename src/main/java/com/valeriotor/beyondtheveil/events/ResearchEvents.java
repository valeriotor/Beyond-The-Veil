package com.valeriotor.beyondtheveil.events;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.SyncUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ResearchEvents {
	
	@SubscribeEvent
	public void wakeUpEvent(PlayerWakeUpEvent event) {
		EntityPlayer p = event.getEntityPlayer();
		if(p.world.getWorldTime() < 23900) return;
		if(p.world.isRemote) return;
		if(ResearchUtil.getResearchStage(p, "FIRSTDREAMS") == 0) {
			SyncUtil.addStringDataOnServer(p, false, "didDream");
		}
	}
	
	public static void gearBenchCraftEvent(EntityPlayer p, ItemStack stack) {
		String s = stack.getItem().getRegistryName().getResourcePath();
		s = s.replace("beyondtheveil:", "");
		SyncUtil.addStringDataOnServer(p, false, "crafted" + s);
	}
	
	public static void progressResearchEvent(EntityPlayer p, String key, int newStage) {
		if(p.world.isRemote) return;
		updateDialogues(p, key, newStage);
		MemoryUnlocks.researchUnlock(p, key, newStage);
		if(key.equals("IDOL") && newStage == 1) {
			SyncUtil.addStringDataOnServer(p, false, "baubleresearch");
		} else if(key.equals("STATUE") && newStage == 2) {
			SyncUtil.addStringDataOnServer(p, false, "dreambottlestatue");
		} else if(key.equals("WEEPERS") && newStage == 2) {
			SyncUtil.addStringDataOnServer(p, false, "gotweepers");
		}
	}
	
	public static void updateDialogues(EntityPlayer p, String resKey, int stage) {
		if(p.world.isRemote) return;
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		switch(resKey) {
		case "FIRSTCONTACT":
			if(stage == 1) SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.SEEKSKNOWLEDGE);
			break;
		case "FISHINGHAMLET":
			if(stage == 1)
				if(data.getString("dialoguegratitude"))
					unlockDialogue(p, "dreamer");
			break;
		case "IDOL":
			if(stage == 0)
				unlockDialogue(p, "newyou", "trustedbar", "trustedcar");
			break;
		case "SLUGS":
			if(stage == 1)
				unlockDialogue(p, "impressed");	
			break;
		case "CANOE":
			if(stage == 0)
				unlockDialogue(p, "canoecar");	
			else if(stage == 2)
				if(data.getString("dialoguecanoe"))
					unlockDialogue(p, "ritual");	
			break;
		case "WEEPERS":
			if(stage == 1 && data.getString("dialogueseeya")) 
				unlockDialogue(p, "weeper");
			break;
		case "SHOGGOTH":
			if(data.getString("dialogueseeya2"))
				unlockDialogue(p, "shoggoth");	
			break;
		}
	}
	
	private static void unlockDialogue(EntityPlayer p, String... names) {
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		for(String name : names) {
			if(!data.getString(name)) {
				SyncUtil.addStringDataOnServer(p, false, "dialogue".concat(name));
			}
		}	
	}
	
}
