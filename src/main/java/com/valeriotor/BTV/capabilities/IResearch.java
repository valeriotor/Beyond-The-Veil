package com.valeriotor.BTV.capabilities;

import java.util.HashMap;

import com.valeriotor.BTV.research.ResearchStatus;

public interface IResearch {
	
	public void addResearchStatus(ResearchStatus status);	
	public HashMap<String, ResearchStatus> getResearches();
	public ResearchStatus getResearch(String key);
}
