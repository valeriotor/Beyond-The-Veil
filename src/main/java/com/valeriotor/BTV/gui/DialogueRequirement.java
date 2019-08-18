package com.valeriotor.BTV.gui;

import java.util.HashMap;
import java.util.function.Predicate;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.gui.DialogueHandler.Branches;
import com.valeriotor.BTV.lib.PlayerDataLib;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

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
			if((opt == this.reqOpt && opt != -1) || (opt == -1 && TC == this.reqTC)) {
				if(this.pred != null)
					if(!this.pred.test(Minecraft.getMinecraft().player)) return false;
				if(this.reqData.length == 0)
					return true;
				for(String s : this.reqData) {
					System.out.println(s);
					if(Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(s)) return true;
				}
			}	
		}
		
		return false;
	}
	
	private static HashMap<String, DialogueRequirement> map = new HashMap<>();
	
	public static void registerRequirements() {
		//map.put("met0", new DialogueRequirement("first", Branches.GENOCIDEDISAGREE.getName(), 0, 0));
		//map.put("met1", new DialogueRequirement("first", Branches.GENOCIDEDISAGREE.getName(), 1, 0));
		map.put("lackknowledge0", new DialogueRequirement("first", Branches.HAMLETARCHITECTURE.getName(), -1, 2, null));
		map.put("lackknowledge1", new DialogueRequirement("first", Branches.HAMLETARTIFACTS.getName(), -1, 1, null));
		map.put("lackknowledge2", new DialogueRequirement("first", Branches.HAMLETSLUGS.getName(), -1, 4, null));
		map.put("lackknowledge3", new DialogueRequirement("first", Branches.HAMLETDWELLERS.getName(), -1, 1, null));
		map.put("lackknowledge4", new DialogueRequirement("first", Branches.HAMLETIDOL.getName(), -1, 3, null));
		map.put("hasknowledge0", new DialogueRequirement("lackknowledge", "", -1, 1, null, "seeksKnowledge"));
		map.put("lecture0", new DialogueRequirement("hasknowledge", "", -1, 1, null));
		map.put("lecture20", new DialogueRequirement("lecture", Branches.INHUMAN.getName(), -1, 3, null));
		map.put("lecture21", new DialogueRequirement("lecture", Branches.PREJUDICE.getName(), -1, 2, null));
		map.put("lecture22", new DialogueRequirement("lecture", Branches.TELLME.getName(), -1, 2, null));
		map.put("gratitude0", new DialogueRequirement("lecture2", Branches.FRIENDSLECTURE.getName(), -1, 1, null));
		map.put("gratitude1", new DialogueRequirement("lecture2", Branches.THANKS.getName(), -1, 1, null));
		map.put("dreamer0", new DialogueRequirement("lecture2", Branches.FRIENDSLECTURE.getName(), -1, 1, p -> ThaumcraftCapabilities.getKnowledge(p).isResearchKnown("FISHINGHAMLET@2")));
		map.put("dreamer1", new DialogueRequirement("lecture2", Branches.THANKS.getName(), -1, 1, p -> ThaumcraftCapabilities.getKnowledge(p).isResearchKnown("FISHINGHAMLET@2")));
		map.put("greatdreamer0", new DialogueRequirement("dreamer", "", 0, 0, null));
		map.put("greatdreamer1", new DialogueRequirement("dreamer", Branches.LIES.getName(), -1, 1, null));
		map.put("ocean0", new DialogueRequirement("greatdreamer", "", -1, 2, null));
		map.put("canoe0", new DialogueRequirement("impressed", "", -1, 3, null));
		map.put("ritualintro0", new DialogueRequirement("impressed", "", -1, 3, p -> ThaumcraftCapabilities.getKnowledge(p).isResearchComplete("CANOE")));
		map.put("rum0", new DialogueRequirement("trustedbar", "", 0, 0, null));
		map.put("enjoy0", new DialogueRequirement("rum", "", -1, 1, null));
		map.put("easyjob0", new DialogueRequirement("canoecar", Branches.DONTFISH.getName(), -1, 1, null));
		map.put("easyjob1", new DialogueRequirement("canoecar", Branches.CANOESFOR.getName(), -1, 1, null));
		
	}
	
	public static HashMap<String, DialogueRequirement> getMap(){
		return map;
	}
	
	
	
}
