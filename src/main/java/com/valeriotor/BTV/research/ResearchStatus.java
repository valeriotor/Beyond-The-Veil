package com.valeriotor.BTV.research;

import java.util.HashMap;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.capabilities.ResearchProvider;
import com.valeriotor.BTV.research.Research.ResearchStage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ResearchStatus {
	
	public final Research res;
	private int stage = -1;
	private boolean learned = true; // TODO: Change to false when rewriting Dream System
	private boolean complete = false;
	
	public ResearchStatus(Research res) {
		this.res = res;
	}
	
	public void learn() {
		this.learned = true;
	}
	
	public boolean progressStage(EntityPlayer p) {
		return this.progressStage(p.getCapability(PlayerDataProvider.PLAYERDATA, null));
	}
	
	public boolean progressStage(IPlayerData data) {
		ResearchStage[] stages = this.res.getStages();
		if(this.stage >= 0 && this.stage < stages.length) {
			if(stages[stage].meetsRequirements(data)) {
				this.progressStage_internal();
				return true;
			}
		} else if(this.stage == -1) {
			this.progressStage_internal();
			return true;
		}
		return false;
	}
	
	private void progressStage_internal() {
		int maxStage = res.getStages().length - 1;
		if(this.stage < maxStage) {
			this.stage++;
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
		this.learned = nbt.getBoolean("learned");
		this.complete = nbt.getBoolean("complete");
		return this;
	}
	
	public int getStage() {
		return this.stage;
	}
}
