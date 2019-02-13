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
			if(opt == this.reqOpt || (opt == -1 && TC == this.reqTC))
				return true;
		}
		
		return false;
	}
	
	private static HashMap<String, DialogueRequirement> map = new HashMap<>();
	
	public static void registerRequirements() {
		map.put("met0", new DialogueRequirement("first", Branches.GENOCIDEDISAGREE.getName(), 0, 0));
		map.put("met1", new DialogueRequirement("first", Branches.GENOCIDEDISAGREE.getName(), 1, 0));
	}
	
	public static HashMap<String, DialogueRequirement> getMap(){
		return map;
	}
	
	
	
}
