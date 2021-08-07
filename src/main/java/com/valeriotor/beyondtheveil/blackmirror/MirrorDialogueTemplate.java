package com.valeriotor.beyondtheveil.blackmirror;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MirrorDialogueTemplate {
	private String id;
	private MirrorDialogueBranch startingBranch;
	private Map<String,MirrorDialogueNode> dialogueNodes = new HashMap<>();
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

	public Set<String> getUnlockableData() {
		Set<String> unlockableData = new HashSet<>();
		if (dialogueNodes != null) {
			for (MirrorDialogueNode node : dialogueNodes.values()) {
				for (MirrorDialogueBranch branch : node.getDialogueOptions()) {
					unlockableData.addAll(branch.getUnlockedData());
				}
			}
		}
		return unlockableData;
	}
	
}
