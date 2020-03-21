package com.valeriotor.beyondtheveil.dweller;

public enum Branches {
	ENLIGHTEN("scholar", 2, "shoggoth", "", 1),
	SORRY("scholar", 3, "shoggoth", "", 0),
	CANOESFOR("carpenter", 1, "canoecar", "", 1),
	DONTFISH("carpenter", 1, "canoecar", "", 0),
	GENOCIDEDISAGREE("lhkeeper", 1, "oldtruth", "", 0),
	GENOCIDEAGREE("lhkeeper", 1, "oldtruth", "", 1),
	DRUNK("lhkeeper", 1, "iknow", "", 0),
	VICTIMS("lhkeeper", 1, "iknow", "", 1),
	LIES("lhkeeper", 1, "dreamer", "", 1),
	THANKS("lhkeeper", 1, "lecture2", "lecture", 1),
	FRIENDSLECTURE("lhkeeper", 1, "lecture2", "lecture", 0),
	LECTURE("lhkeeper", 10, "lecture2", "", 0),
	TELLME("lhkeeper", 2, "lecture", "", 0),
	FIEND("lhkeeper", 2, "lecture", "", 1),
	INHUMAN("lhkeeper", 3, "lecture", "fiend", 0),
	PREJUDICE("lhkeeper", 2, "lecture", "fiend", 1),
	HAMLETLIKE("lhkeeper", 1, "first", "", 0),
	HAMLETARCHITECTURE("lhkeeper", 2, "first", "hamletlike", 0),
	HAMLETLOOT("lhkeeper", 1, "first", "hamletlike", 1),
	HAMLETARTIFACTS("lhkeeper", 1, "first", "hamletloot", 0),
	HAMLETSLUGS("lhkeeper", 4, "first", "hamletloot", 1),
	HAMLETDISLIKE("lhkeeper", 1, "first", "", 1),
	HAMLETDWELLERS("lhkeeper", 1, "first", "hamletdislike", 0),
	HAMLETIDOL("lhkeeper", 3, "first", "hamletdislike", 1);
	
	private int talkCount;
	private String prof;
	private String reqDialogue;
	private String reqBranch;
	private int reqOpt;
	
	private Branches(String prof, int num, String reqDialogue, String reqBranch, int reqOpt) {
		this.talkCount = num;
		this.prof = prof;
		this.reqDialogue = reqDialogue;
		this.reqBranch = reqBranch;
		this.reqOpt = reqOpt;
	}
	
	public int getTalkCount() {
		return this.talkCount;
	}

	public String getProf() {
		return this.prof;
	}
	
	public String getLowerCaseName() {
		return this.name().toLowerCase();
	}
	
	public boolean isCorrectBranch(String dialogue, String profession, String previousBranch, int option) {
		if(dialogue.equals(this.reqDialogue) && profession.equals(this.prof) && previousBranch.equals(this.reqBranch) && option == this.reqOpt) {
			return true;
		}
		return false;
	}
}
