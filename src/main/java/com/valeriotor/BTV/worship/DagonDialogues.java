package com.valeriotor.BTV.worship;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.gui.GuiDagon;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.research.ResearchUtil;

import net.minecraft.entity.player.EntityPlayer;

public enum DagonDialogues {
	GREETING(9),
	JUDGEMENT(4);
	
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
		}
		return null;
	}
	
	
	
	
	
	
}
