package com.valeriotor.BTV.events;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.util.SyncUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ResearchEvents {
	
	@SubscribeEvent
	public void wakeUpEvent(PlayerWakeUpEvent event) {
		EntityPlayer p = event.getEntityPlayer();
		if(p.world.getWorldTime() < 23900) return;
		if(ResearchUtil.getResearchStage(p, "FIRSTDREAMS") == 0) {
			SyncUtil.addStringDataOnServer(p, false, "didDream");
		}
	}
	
	public static void progressResearchEvent(EntityPlayer p, String key, int newStage) {
		updateDialogues(p, key, newStage);
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
			if(stage == 0)
				unlockDialogue(p, "impressed");	
			break;
		case "CANOE":
			if(stage == 0)
				unlockDialogue(p, "canoecar");	
			else if(stage == 1)
				if(data.getString("dialoguecanoe"))
					unlockDialogue(p, "ritual");	
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
