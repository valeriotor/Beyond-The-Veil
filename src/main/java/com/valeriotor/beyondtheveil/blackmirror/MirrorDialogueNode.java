package com.valeriotor.beyondtheveil.blackmirror;

import java.util.List;

public class MirrorDialogueNode {
	private String id;
	private List<MirrorDialogueBranch> dialogueOptions;
	
	public String getNodeID() {
		return this.id;
	}
	
	public List<MirrorDialogueBranch> getDialogueOptions() {
		return this.dialogueOptions;
	}
}
