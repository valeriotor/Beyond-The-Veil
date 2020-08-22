package com.valeriotor.beyondtheveil.blackmirror;

public class MirrorDialogueBranch {
	private String id;
	private int branchLength;
	private String endingNodeID;
	private boolean endsDialogue = false;
	
	public String getBranchID() {
		return this.id;
	}
	
	public int getLength() {
		return this.branchLength;
	}
	
	public String getEndingNodeID() {
		return this.endingNodeID;
	}
	
	public boolean endsDialogue() {
		return this.endsDialogue;
	}
	
}
