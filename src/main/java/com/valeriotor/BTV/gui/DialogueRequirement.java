package com.valeriotor.BTV.gui;

import java.util.HashMap;

import com.valeriotor.BTV.gui.DialogueHandler.Branches;

public class DialogueRequirement {
	
	
	private String reqDialogue;
	private String reqBranch;
	private int reqOpt;
	private int reqTC;
	
	public DialogueRequirement(String reqDialogue, String reqBranch, int reqOpt, int reqTC) {
		this.reqDialogue = reqDialogue;
		this.reqBranch = reqBranch;
		this.reqOpt = reqOpt;
		this.reqTC = reqTC;
	}
	
	public boolean canUnlock(String dialogue, String branch, int opt, int TC) {
		if(dialogue.equals(this.reqDialogue) && branch.equals(this.reqBranch)) {
			if((opt == this.reqOpt && opt != -1) || (opt == -1 && TC == this.reqTC))
				return true;
		}
		
		return false;
	}
	
	private static HashMap<String, DialogueRequirement> map = new HashMap<>();
	
	public static void registerRequirements() {
		//map.put("met0", new DialogueRequirement("first", Branches.GENOCIDEDISAGREE.getName(), 0, 0));
		//map.put("met1", new DialogueRequirement("first", Branches.GENOCIDEDISAGREE.getName(), 1, 0));
		map.put("lecture0", new DialogueRequirement("first", Branches.HAMLETARCHITECTURE.getName(), -1, 2));
		map.put("lecture1", new DialogueRequirement("first", Branches.HAMLETARTIFACTS.getName(), -1, 1));
		map.put("lecture2", new DialogueRequirement("first", Branches.HAMLETSLUGS.getName(), -1, 4));
		map.put("lecture3", new DialogueRequirement("first", Branches.HAMLETDWELLERS.getName(), -1, 1));
		map.put("lecture4", new DialogueRequirement("first", Branches.HAMLETIDOL.getName(), -1, 3));
		map.put("lecture20", new DialogueRequirement("lecture", Branches.INHUMAN.getName(), -1, 3));
		map.put("lecture21", new DialogueRequirement("lecture", Branches.PREJUDICE.getName(), -1, 2));
		map.put("lecture22", new DialogueRequirement("lecture", Branches.TELLME.getName(), -1, 2));
		
		
	}
	
	public static HashMap<String, DialogueRequirement> getMap(){
		return map;
	}
	
	
	
}
