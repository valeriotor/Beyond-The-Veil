package com.valeriotor.beyondtheveil.gui;

import java.util.HashMap;
import java.util.function.Predicate;

import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.dweller.Branches;
import com.valeriotor.beyondtheveil.research.ResearchUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class DialogueRequirement {
	
	
	private String reqDialogue;
	private String reqBranch;
	private int reqOpt;
	private int reqTC;
	private Predicate<EntityPlayer> pred;
	private String[] reqData;
	
	public DialogueRequirement(String reqDialogue, String reqBranch, int reqOpt, int reqTC, Predicate<EntityPlayer> pred, String... reqData) {
		this.reqDialogue = reqDialogue;
		this.reqBranch = reqBranch;
		this.reqOpt = reqOpt;
		this.reqTC = reqTC;
		this.pred = pred;
		this.reqData = reqData;
	}
	
	public boolean canUnlock(String dialogue, String branch, int opt, int TC) {
		if(dialogue.equals(this.reqDialogue) && branch.equals(this.reqBranch)) {
			if((opt == this.reqOpt && opt != -1) || (TC == this.reqTC)) {
				if(this.pred != null)
					if(!this.pred.test(Minecraft.getMinecraft().player)) return false;
				if(this.reqData.length == 0)
					return true;
				for(String s : this.reqData) {
					if(!Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(s)) return false;
				return true;
				}
			}	
		}
		
		return false;
	}
	
	private static HashMap<String, DialogueRequirement> map = new HashMap<>();
	
	public static void registerRequirements() {
		//map.put("met0", new DialogueRequirement("first", Branches.GENOCIDEDISAGREE.getLowerCaseName(), 0, 0));
		//map.put("met1", new DialogueRequirement("first", Branches.GENOCIDEDISAGREE.getLowerCaseName(), 1, 0));
		map.put("lackknowledge0", new DialogueRequirement("first", Branches.HAMLETARCHITECTURE.getLowerCaseName(), -1, 2, null));
		map.put("lackknowledge1", new DialogueRequirement("first", Branches.HAMLETARTIFACTS.getLowerCaseName(), -1, 1, null));
		map.put("lackknowledge2", new DialogueRequirement("first", Branches.HAMLETSLUGS.getLowerCaseName(), -1, 4, null));
		map.put("lackknowledge3", new DialogueRequirement("first", Branches.HAMLETDWELLERS.getLowerCaseName(), -1, 1, null));
		map.put("lackknowledge4", new DialogueRequirement("first", Branches.HAMLETIDOL.getLowerCaseName(), -1, 3, null));
		map.put("hasknowledge0", new DialogueRequirement("lackknowledge", "", -1, 1, null, "seeksKnowledge"));
		map.put("lecture0", new DialogueRequirement("hasknowledge", "", -1, 1, null));
		map.put("lecture20", new DialogueRequirement("lecture", Branches.INHUMAN.getLowerCaseName(), -1, 3, null));
		map.put("lecture21", new DialogueRequirement("lecture", Branches.PREJUDICE.getLowerCaseName(), -1, 2, null));
		map.put("lecture22", new DialogueRequirement("lecture", Branches.TELLME.getLowerCaseName(), -1, 2, null));
		map.put("gratitude0", new DialogueRequirement("lecture2", Branches.FRIENDSLECTURE.getLowerCaseName(), -1, 1, null));
		map.put("gratitude1", new DialogueRequirement("lecture2", Branches.THANKS.getLowerCaseName(), -1, 1, null));
		map.put("dreamer0", new DialogueRequirement("lecture2", Branches.FRIENDSLECTURE.getLowerCaseName(), -1, 1, p -> ResearchUtil.getResearchStage(p, "FISHINGHAMLET") == 1));
		map.put("dreamer1", new DialogueRequirement("lecture2", Branches.THANKS.getLowerCaseName(), -1, 1, p -> ResearchUtil.getResearchStage(p, "FISHINGHAMLET") == 1));
		map.put("greatdreamer0", new DialogueRequirement("dreamer", "", 0, 0, null));
		map.put("greatdreamer1", new DialogueRequirement("dreamer", Branches.LIES.getLowerCaseName(), -1, 1, null));
		map.put("ocean0", new DialogueRequirement("greatdreamer", "", -1, 2, null));
		map.put("canoe0", new DialogueRequirement("impressed", "", -1, 3, null));
		map.put("ritualintro0", new DialogueRequirement("impressed", "", -1, 3, p -> ResearchUtil.isResearchComplete(p, "CANOE")));
		map.put("ritual0", new DialogueRequirement("ritualintro", "", -1, 1, null));
		map.put("friend0", new DialogueRequirement("ritual", "", -1, 7, null));
		map.put("oldtruth0", new DialogueRequirement("iknow", Branches.DRUNK.getLowerCaseName(), -1, 1, null));
		map.put("oldtruth1", new DialogueRequirement("iknow", Branches.VICTIMS.getLowerCaseName(), -1, 1, null));
		map.put("past0", new DialogueRequirement("oldtruth", Branches.GENOCIDEDISAGREE.getLowerCaseName(), -1, 1, null));
		map.put("past1", new DialogueRequirement("oldtruth", Branches.GENOCIDEAGREE.getLowerCaseName(), -1, 1, null));
		map.put("rum0", new DialogueRequirement("trustedbar", "", 0, 0, null));
		map.put("enjoy0", new DialogueRequirement("rum", "", -1, 1, null));
		map.put("easyjob0", new DialogueRequirement("canoecar", Branches.DONTFISH.getLowerCaseName(), -1, 1, null));
		map.put("easyjob1", new DialogueRequirement("canoecar", Branches.CANOESFOR.getLowerCaseName(), -1, 1, null));
		map.put("end20", new DialogueRequirement("end", "", -1, 6, null));
		map.put("seeya0", new DialogueRequirement("first", "", -1, 3, null));
		map.put("weeper0", new DialogueRequirement("seeya", "", -1, 0, null, "filledtears"));
		map.put("seeya20", new DialogueRequirement("weeper", "", -1, 4, null));
		map.put("shoggoth0", new DialogueRequirement("seeya2", "", -1, 0, null, "shoggothsecret"));
		map.put("breath0", new DialogueRequirement("shoggoth", "sorry", 0, 0, null));
		map.put("breath1", new DialogueRequirement("shoggoth", "sorry", 1, 0, null));
		map.put("breath2", new DialogueRequirement("shoggoth", "enlighten", 0, 0, null));
		map.put("breath3", new DialogueRequirement("shoggoth", "enlighten", 1, 0, null));
		map.put("seeya30", new DialogueRequirement("breath", "", -1, 1, null));
	}
	
	public static HashMap<String, DialogueRequirement> getMap(){
		return map;
	}
	
	
	
}
