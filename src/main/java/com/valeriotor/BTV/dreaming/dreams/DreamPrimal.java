package com.valeriotor.BTV.dreaming.dreams;

import com.valeriotor.BTV.dreaming.DreamHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.research.ResearchCategories;

public class DreamPrimal extends AbstractDream{
	
	private int[] knowledge;
	
	public DreamPrimal(String name, int priority, int[] knowledge) {
		super(name, priority);
		this.knowledge = knowledge;
	}

	@Override
	public boolean activate(EntityPlayer p, World w, IPlayerKnowledge k) {
		int lvl = DreamHandler.getDreamingGodLevel(p);
		if(lvl >= 1) {
			int max = 0;
			int maxPos = 0;
			for(int i = 0; i < 6; i++) {
				if(max < knowledge[i]) {
					max = knowledge[i];
					maxPos = i;
				}
			}
			knowledge[maxPos] += 8;
			if(p.world.rand.nextInt(50 - 4 * lvl) == 0)
				k.addKnowledge(EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory(getCategoryName(maxPos)), 16);
		}
		int r = p.world.rand.nextInt(/*getDreamingGodLevel(p) >= 1 ? 32 : */40);
		boolean didUse = true;
		int sum = 0;
		for(int i = 0; i < 6; i++) {
			sum += knowledge[i];
			if(r < sum) {
				k.addKnowledge(EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory(getCategoryName(i)), getKnowledgeByLevel(lvl));
				break;
			}
		}
		if(r >= sum) didUse = false;
		return didUse;
	}
	
	private String getCategoryName(int index) {
		switch(index) {
			case 0: return "BASICS";
			case 1: return "AlCHEMY";
			case 2: return "INFUSION";
			case 3: return "ARTIFICE";
			case 4: return "GOLEMANCY";
			default: return "AUROMANCY";
		}
	}
	
	private int getKnowledgeByLevel(int lvl) {
		return 10 + 2 * lvl;
	}

}
