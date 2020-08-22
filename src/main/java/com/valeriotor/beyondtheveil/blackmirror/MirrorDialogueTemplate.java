package com.valeriotor.beyondtheveil.blackmirror;

import java.util.Map;

public class MirrorDialogueTemplate {
	private String id;
	private MirrorDialogueBranch startingBranch;
	private Map<String,MirrorDialogueNode> dialogueNodes;
	private String unlocksAtEnd;
	
	public String getID() {
		return this.id;
	}
	
	public MirrorDialogueBranch getStartingBranch() {
		return this.startingBranch;
	}
	
	public MirrorDialogueNode getNodeByID(String id) {
		return dialogueNodes.get(id);
	}
	
	public String getDefaultDialogueUnlockedWhenFinished() {
		return unlocksAtEnd;
	}
	
}
