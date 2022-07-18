package com.valeriotor.beyondtheveil.capability.research;

import com.valeriotor.beyondtheveil.research.Research;
import com.valeriotor.beyondtheveil.research.ResearchRegistry;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public class ResearchData {

    private Map<String, ResearchStatus> researches = new HashMap<>();

    public void addResearchStatus(ResearchStatus status) {
        researches.put(status.res.getKey(), status);
    }

    public Map<String, ResearchStatus> getResearches() {
        return this.researches;
    }

    public ResearchStatus getResearch(String key) {
        if (this.researches.isEmpty()) {
            for(Map.Entry<String, Research> entry : ResearchRegistry.researches.entrySet()) {
                addResearchStatus(new ResearchStatus(entry.getValue()));
            }
        }
        if (!this.researches.containsKey(key) && ResearchRegistry.researches.containsKey(key)) {
            addResearchStatus(new ResearchStatus(ResearchRegistry.researches.get(key)));
        }
        return this.researches.get(key);
    }

    public void populate() {
        for(Map.Entry<String, Research> entry : ResearchRegistry.researches.entrySet()) {
            if(!researches.containsKey(entry.getKey()))
                researches.put(entry.getKey(), new ResearchStatus(entry.getValue()));
        }
    }

    public void putResearches(HashMap<String, ResearchStatus> researches) {
        this.researches = researches;
    }

    public void saveToNBT(CompoundTag tag) {
        Map<String, ResearchStatus> researches = getResearches();
        for(Map.Entry<String, ResearchStatus> entry : researches.entrySet()) {
            tag.put(entry.getKey(), entry.getValue().writeToNBT(new CompoundTag()));
        }
    }

    public void loadFromNBT(CompoundTag tag) {
        for(Map.Entry<String, Research> entry : ResearchRegistry.researches.entrySet()) {
            if(tag.contains(entry.getKey()))
                addResearchStatus(new ResearchStatus(entry.getValue()).readFromNBT(tag.getCompound(entry.getKey())));
            else
                addResearchStatus(new ResearchStatus(entry.getValue()));
        }
    }

    public void copyToNewStore(ResearchData newDataStore) {
        newDataStore.researches.putAll(researches);
    }
}
