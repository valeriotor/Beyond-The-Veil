package com.valeriotor.beyondtheveil.blackmirror;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class MirrorDialogueBranch {
	private String id;
	private int branchLength;
	private String endingNodeID;
	private List<String> unlockedData = new ArrayList<>();
	private List<String> mustHaveData = new ArrayList<>();
	private List<String> mustNotHaveData = new ArrayList<>();
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

	public List<String> getUnlockedData() {
		return ImmutableList.copyOf(unlockedData);
	}

	public List<String> getMustHaveData() {
		return ImmutableList.copyOf(mustHaveData);
	}

	public List<String> getMustNotHaveData() {
		return ImmutableList.copyOf(mustNotHaveData);
	}
}
