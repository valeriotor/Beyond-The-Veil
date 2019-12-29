package com.valeriotor.BTV.network.research;

import com.valeriotor.BTV.capabilities.ResearchProvider;
import com.valeriotor.BTV.research.ResearchRegistry;
import com.valeriotor.BTV.research.ResearchStatus;
import com.valeriotor.BTV.research.ResearchUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ResearchSyncer {
	
	public String key;
	public boolean learn = false;
	public boolean progress = false;
	public boolean complete = false;
	public boolean unlearn = false;
	public ResearchStatus status;
	
	public ResearchSyncer() {}
	
	public ResearchSyncer(String key) {
		this.key = key;
	}
	
	public ResearchSyncer setLearn(boolean set) {
		this.learn = set;
		return this;
	}
	
	public ResearchSyncer setProgress(boolean set) {
		this.progress = set;
		return this;
	}
	
	public ResearchSyncer setUnlearn(boolean set) {
		this.unlearn = set;
		return this;
	}
	
	public ResearchSyncer setComplete(boolean set) {
		this.complete = set;
		return this;
	}
	
	public ResearchSyncer setStatus(ResearchStatus status) {
		this.status = status;
		return this;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setString("key", key);
		nbt.setBoolean("learn", learn);
		nbt.setBoolean("progress", progress);
		nbt.setBoolean("unlearn", unlearn);
		nbt.setBoolean("complete", complete);
		if(status != null) {
			nbt.setTag("res", this.status.writeToNBT(new NBTTagCompound()));
		}
		return nbt;
	}
	
	public ResearchSyncer readFromNBT(NBTTagCompound nbt) {
		this.key = nbt.getString("key");
		this.learn = nbt.getBoolean("learn");
		this.progress = nbt.getBoolean("progress");
		this.unlearn = nbt.getBoolean("unlearn");
		this.complete = nbt.getBoolean("complete");
		if(nbt.hasKey("res")) {
			this.status = new ResearchStatus(ResearchRegistry.researches.get(this.key)).readFromNBT((NBTTagCompound) nbt.getTag("res"));
		}
		return this;
	}
	
	@SideOnly(Side.CLIENT)
	public void processClient() {
		EntityPlayer p = Minecraft.getMinecraft().player;
		if(this.progress) ResearchUtil.progressResearch(p, this.key);
		if(this.learn) ResearchUtil.learnResearch(p, this.key);
		if(this.status != null) p.getCapability(ResearchProvider.RESEARCH, null).addResearchStatus(this.status);
		if(this.complete) ResearchUtil.getResearch(p, this.key).complete(p);
		if(this.unlearn) ResearchUtil.getResearch(p, this.key).unlearn();
	}
	
}
