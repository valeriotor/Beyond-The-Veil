package com.valeriotor.beyondtheveil.dweller;

import com.valeriotor.beyondtheveil.gui.DialogueRequirement;

public enum Dialogues {
	SEEYA3("scholar", 1, 1),
	BREATH("scholar", 1, 4),
	SHOGGOTH("scholar", 1, 1),
	SEEYA2("scholar", 1, 1),
	WEEPER("scholar", 4, 1),
	SEEYA("scholar", 1, 1),
	EASYJOB("carpenter", 2, 2),
	CANOECAR("carpenter", 1, 0),
	TRUSTEDCAR("carpenter", 1, 0),
	ENJOY("bartender", 1, 1),
	RUM("bartender", 1, 1),
	TRUSTEDBAR("bartender", 2, 0),
	END2("lhkeeper", 1, 1),
	END("lhkeeper", 6, 0),
	PAST("lhkeeper", 1, 2),
	OLDTRUTH("lhkeeper", 4, 2),
	IKNOW("lhkeeper", 1, 0),
	FRIEND("lhkeeper", 1, 1),
	RITUAL("lhkeeper", 7, 1),
	RITUALINTRO("lhkeeper", 1, 1),
	CANOE("lhkeeper", 1, 1),
	IMPRESSED("lhkeeper", 3, 0),
	NEWYOU("lhkeeper", 2, 0),
	OCEAN("lhkeeper", 1, 1),
	GREATDREAMER("lhkeeper", 2, 2),
	DREAMER("lhkeeper", 3, 2),
	GRATITUDE("lhkeeper", 1, 2),
	LECTURE2("lhkeeper", 1, 3),
	LECTURE("lhkeeper", 2, 1),
	HASKNOWLEDGE("lhkeeper", 1, 1),
	LACKKNOWLEDGE("lhkeeper", 2, 5),
	FIRST("any", 0, 0);
	
	private int talkCount;
	private String prof;
	private int unlocks;
	
	private Dialogues(String prof, int num, int unlocks) {
		this.talkCount = num;
		this.prof = prof;
		this.unlocks = unlocks;
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
	
	public boolean canUnlock(String dialogue, String branch, int opt, int TC) {			
		for(int i = 0; i < this.unlocks; i++) {
			String name = this.getLowerCaseName().concat(String.valueOf(i));
			if(DialogueRequirement.getMap().containsKey(name)) {
				if(DialogueRequirement.getMap().get(name).canUnlock(dialogue, branch, opt, TC)) return true;
			}else {
				System.out.println("Warning: Dialogue Requirement not found. Report this to valeriotor.");
			}
		}
		return false;
	}
}
