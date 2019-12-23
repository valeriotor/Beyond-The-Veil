package com.valeriotor.BTV.research;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.research.ResearchRegistry.ResearchTemp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Research {
	
	private String key;
	private String name;
	private String[] icons;
	private ItemStack[] iconsTex;
	private int[] location;
	private String[] parents;
	private String[] hiders;
	private boolean learn;
	private ResearchStage[] stages;
	
	
	public Research(ResearchTemp res) {
		this.key = res.key;
		this.name = res.name;
		this.icons = res.icons;
		this.location = res.location;
		this.parents = res.parents;
		this.hiders = res.hiders;
		this.learn = res.learn;
		this.stages = res.stages;
		List<ItemStack> stacks = new ArrayList<>();
		for(String icon : icons) {
			String[] split = icon.split(";");
			int amount = split.length > 1 ? Integer.valueOf(split[1]) : 1;
			int meta = split.length > 2 ? Integer.valueOf(split[2]) : 0;
			stacks.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(split[0])), amount, meta));
		}
		iconsTex = new ItemStack[stacks.size()];
		for(int i = 0; i < stacks.size(); i++) {
			iconsTex[i] = stacks.get(i);
		}
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String[] getHiders() {
		if(this.hiders == null)
			this.hiders = new String[0];
		return this.hiders;
	}
	
	public String[] getParents() {
		if(this.parents == null)
			this.parents = new String[0];
		return this.parents;
	}
	
	public ResearchStage[] getStages() {
		if(this.stages == null)
			this.stages = new ResearchStage[0];
		return this.stages;
	}
	
	public int getX() {
		return this.location[0];
	}
	
	public int getY() {
		return this.location[1];
	}
	
	public ItemStack[] getIconStacks() {
		return this.iconsTex;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(key).append("\n").append(name).append("\nIcons: ");
		for(String s : icons) {
			sb.append(s + "; ");
		}
		sb.append("\n");		
		sb.append("Location: " + location[0] + " " + location[1] + "\nParents: ");
		for(String s : parents) {
			sb.append(s + "; ");
		}
		sb.append("\nHiders: ");
		if(hiders != null)
			for(String s : hiders) {
				sb.append(s + "; ");
			}
		if(stages != null)
			sb.append("\nStages: ");
			for(ResearchStage stage : stages) {
				sb.append(stage.toString());
			}
		return sb.toString();
	}
	
	
	public static class ResearchStage {
		String text;
		String[] required_research;
		String[] recipes;
		
		public boolean meetsRequirements(EntityPlayer p) {
			return this.meetsRequirements(p.getCapability(PlayerDataProvider.PLAYERDATA, null));
		}
		
		public boolean meetsRequirements(IPlayerData data) {
			for(String s : this.getRequirements()) {
				if(!data.getString(s)) return false;
			}
			return true;
		}
		
		public String[] getRequirements() {
			if(this.required_research == null)
				this.required_research = new String[0];
			return this.required_research;
		}
		
		public String getTextKey() {
			return this.text;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("\n").append(" " + text).append("\n").append(" Required Research: ");
			if(required_research != null)
				for(String s : required_research) {
					sb.append(s + "; ");
				}
			return sb.toString();
		}
		
		
	}
	
}
