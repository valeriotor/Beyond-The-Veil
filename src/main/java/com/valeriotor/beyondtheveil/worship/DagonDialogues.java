package com.valeriotor.beyondtheveil.worship;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.gui.GuiDagon;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.research.ResearchUtil;

import net.minecraft.entity.player.EntityPlayer;

public enum DagonDialogues {
	GREETING(9),
	JUDGEMENT(4),
	SEABORN(7),
	KILLKEEPER(4),
	FINAL(5);
	
	public final int talkCount;
	private DagonDialogues(int talkCount) {
		this.talkCount = talkCount;
	}
	
	
	public static GuiDagon getGui(EntityPlayer p) {
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		if(ResearchUtil.getResearchStage(p, "ALLIANCE") == 1) {
			if(!data.getString(PlayerDataLib.DAGON_DIALOGUE.apply(0))) return new GuiDagon(GREETING);
		} else if(ResearchUtil.getResearchStage(p, "METAMORPHOSIS") == 0) {
			if(!data.getString(PlayerDataLib.DAGON_DIALOGUE.apply(1))) return new GuiDagon(JUDGEMENT);
		} else if(ResearchUtil.getResearchStage(p, "WATERWALKING") == 0) {
			if(!data.getString(PlayerDataLib.DAGON_DIALOGUE.apply(2))) return new GuiDagon(SEABORN);
		} else if(ResearchUtil.getResearchStage(p, "FINALQUEST") == 0) {
			if(!data.getString(PlayerDataLib.DAGON_DIALOGUE.apply(3))) return new GuiDagon(KILLKEEPER);
		}
		return null;
	}
	
	
	
	
	
	
}
