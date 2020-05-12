package com.valeriotor.beyondtheveil.research;

import java.util.HashMap;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capabilities.ResearchProvider;
import com.valeriotor.beyondtheveil.events.ResearchEvents;
import com.valeriotor.beyondtheveil.research.Research.SubResearch;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ResearchStatus {
	
	public final Research res;
	private int stage = -1;
	private boolean learned;
	private boolean complete = false;
	
	public ResearchStatus(Research res) {
		this.res = res;
		this.learned = !res.mustLearn();
	}
	
	public void learn() {
		this.learned = true;
	}
	
	public boolean canProgressStage(EntityPlayer p) {
		return this.canProgressStage(p.getCapability(PlayerDataProvider.PLAYERDATA, null));
	}
	
	public boolean canProgressStage(IPlayerData data) {
		return !complete && this.res.getStages()[stage].meetsRequirements(data);
	}

	public boolean progressStage(EntityPlayer p) {
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		SubResearch[] stages = this.res.getStages();
		if(this.stage >= 0 && this.stage < stages.length) {
			if(stages[stage].meetsRequirements(data)) {
				this.progressStage_internal(p);
				return true;
			}
		} else if(this.stage == -1) {
			this.progressStage_internal(p);
			return true;
		}
		return false;
	}
	
	private void progressStage_internal(EntityPlayer p) {
		int maxStage = res.getStages().length - 1;
		if(this.stage < maxStage) {
			this.stage++;
			ResearchEvents.progressResearchEvent(p, res.getKey(), this.stage);
			if(this.stage == maxStage)
				this.complete = true;
		}
		
	}
	
	public boolean isComplete() {
		return this.complete;
	}
	
	public boolean isHidden(HashMap<String, ResearchStatus> map, IPlayerData data) {
		for(String key : this.res.getHiders()) {
			if((!map.containsKey(key) || !map.get(key).complete) && !data.getString(key)) return true;
		}
		return false;
	}
	
	public boolean isHidden(EntityPlayer p) {
		return this.isHidden(p.getCapability(ResearchProvider.RESEARCH, null).getResearches(), p.getCapability(PlayerDataProvider.PLAYERDATA, null));
	}
	
	public boolean isVisible(HashMap<String, ResearchStatus> map, IPlayerData data) {
		return learned && !isHidden(map, data);
	}
	
	public boolean isVisible(EntityPlayer p) {
		return learned && !isHidden(p);
	}
	
	public boolean isKnown(EntityPlayer p) {
		HashMap<String, ResearchStatus> map = p.getCapability(ResearchProvider.RESEARCH, null).getResearches();
		return complete || stage > -1 || (isVisible(map, p.getCapability(PlayerDataProvider.PLAYERDATA, null)) && parentsComplete(map));
	}
	
	public boolean isKnown(HashMap<String, ResearchStatus> map, IPlayerData data) {
		return complete || stage > -1 || (isVisible(map, data) && parentsComplete((map)));
	}
	
	public boolean parentsComplete(HashMap<String, ResearchStatus> map) {
		for(String s: this.res.getParents()) {
			if(!map.containsKey(s) || !map.get(s).complete) return false;
		}
		return true;
	}
	
	public boolean parentsComplete(EntityPlayer p) {
		return this.parentsComplete(p.getCapability(ResearchProvider.RESEARCH, null).getResearches());
	}
	
	public boolean isLearnable(EntityPlayer p) {
		return this.isLearnable(p.getCapability(ResearchProvider.RESEARCH, null).getResearches(), p.getCapability(PlayerDataProvider.PLAYERDATA, null));
	}
	
	public boolean isLearnable(HashMap<String, ResearchStatus> map, IPlayerData data) {
		return !learned && !isHidden(map, data) && parentsComplete(map);
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("stage", stage);
		nbt.setBoolean("learned", learned);
		nbt.setBoolean("complete", complete);
		return nbt;
	}
	
	public ResearchStatus readFromNBT(NBTTagCompound nbt) {
		this.stage = nbt.getInteger("stage");
		this.learned = this.learned || nbt.getBoolean("learned");
		this.complete = nbt.getBoolean("complete");
		return this;
	}
	
	public int getStage() {
		return this.stage;
	}
	
	public void unlearn() {
		this.stage = -1;
		this.complete = false;
		this.learned = !res.mustLearn();
	}
	
	public void complete(EntityPlayer p) {
		this.complete(p.getCapability(ResearchProvider.RESEARCH, null).getResearches(), p.getCapability(PlayerDataProvider.PLAYERDATA, null));
	}
	
	public boolean complete(HashMap<String, ResearchStatus> map, IPlayerData data) {
		for(String key : this.res.getHiders()) {
			if(!map.containsKey(key))
				return false;
			if(!map.get(key).complete)
				if(!map.get(key).complete(map, data))
					return false;
			if(!data.getString(key)) {
				data.addString(key, false);
			}
		}
		for(String key : this.res.getParents()) {
			if(!map.containsKey(key))
				return false;
			if(!map.get(key).complete)
				if(!map.get(key).complete(map, data))
					return false;
		}
		this.stage = res.getStages().length - 1;
		this.complete = true;
		this.learned = true;
		return true;
	}
}
