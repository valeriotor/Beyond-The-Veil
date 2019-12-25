package com.valeriotor.BTV.dreaming.dreams;

import java.util.HashMap;
import java.util.Map.Entry;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.capabilities.ResearchProvider;
import com.valeriotor.BTV.research.ResearchStatus;
import com.valeriotor.BTV.research.ResearchUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class DreamLearning extends AbstractDream{
	
	public DreamLearning(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w) {
		HashMap<String, ResearchStatus> stati = p.getCapability(ResearchProvider.RESEARCH, null).getResearches();
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		for(Entry<String, ResearchStatus> entry : stati.entrySet()) {
			if(entry.getValue().isLearnable(stati, data)) {
				ResearchUtil.learnResearchServer(p, entry.getKey());
				return true;
			}
		}
		return false;
	}

}
