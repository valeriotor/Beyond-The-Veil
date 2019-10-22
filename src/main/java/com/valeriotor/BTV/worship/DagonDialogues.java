package com.valeriotor.BTV.worship;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.gui.GuiDagon;
import com.valeriotor.BTV.lib.PlayerDataLib;

import net.minecraft.entity.player.EntityPlayer;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

public enum DagonDialogues {
	GREETING(9);
	
	public final int talkCount;
	private DagonDialogues(int talkCount) {
		this.talkCount = talkCount;
	}
	
	
	public static GuiDagon getGui(EntityPlayer p) {
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(p);
		switch(data.getOrSetInteger(PlayerDataLib.DAGON_DIALOGUE, 0, false)) {
			case 0: if(k.isResearchKnown("ALLIANCE@1")) return new GuiDagon(GREETING);
			break;
		}
		return null;
	}
	
	
	
	
	
	
}
