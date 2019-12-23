package com.valeriotor.BTV.research;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.research.Research.ResearchStage;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ResearchRegistry {
	
	private static ResearchContainer container;
	public static HashMap<String, Research> researches = new HashMap<>();
	private static final boolean DEBUG_PRINTS = false;
	
	public static final List<ResearchConnection> connections = new ArrayList<>();
	
	public static void registerResearchesFirst() {
		try {
			String file = Resources.toString(BeyondTheVeil.class.getResource("/assets/beyondtheveil/research/btvresearch.json"), Charsets.UTF_8);
			container = BeyondTheVeil.gson.fromJson(file, ResearchContainer.class);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public static void registerResearchesSecond() {
		container.makeResearches(researches);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerConnections() {
		for(Entry<String, Research> entry : researches.entrySet()) {
			for(String s : entry.getValue().getParents()) {
				if(researches.containsKey(s))
					connections.add(new ResearchConnection(researches.get(s), entry.getValue()));
			}
		}
	}
	
	public static class ResearchContainer {
		ResearchTemp[] entries;
		
		public void makeResearches(HashMap<String, Research> map) {
			for(ResearchTemp temp : entries) {
				map.put(temp.key, temp.getResearch());
				if(DEBUG_PRINTS) System.out.println(map.get(temp.key) + "\n\n");
			}
		}
	}
	
	public static class ResearchTemp {
		public String key;
		public String name;
		public String[] icons;
		public int[] location;
		public String[] parents;
		public String[] hiders;
		public boolean learn;
		public ResearchStage[] stages;
		
		public Research getResearch() {
			return new Research(this);
		}
	}
	
}