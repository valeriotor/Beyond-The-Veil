package com.valeriotor.beyondtheveil.capabilities;

import java.util.HashMap;

import com.valeriotor.beyondtheveil.research.ResearchStatus;

public interface IResearch {
	
	public void addResearchStatus(ResearchStatus status);	
	public HashMap<String, ResearchStatus> getResearches();
	public ResearchStatus getResearch(String key);
	public void populate();
	public void putResearches(HashMap<String, ResearchStatus> researches);
}
