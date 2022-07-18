package com.valeriotor.beyondtheveil.research;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.research.Research.SubResearch;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ResearchRegistry {

    private static ResearchContainer container;
    public static Map<String, Research> researches = new HashMap<>();
    //public static Map<String, List<IRecipe>> recipes = new HashMap<>();
    private static final boolean DEBUG_PRINTS = false;

    //public static final List<ResearchConnection> connections = new ArrayList<>();

    public static void registerResearchesFirst() {
        try {
            String file = Resources.toString(BeyondTheVeil.class.getResource("/data/beyondtheveil/research/btvresearch.json"), Charsets.UTF_8);
            container = BeyondTheVeil.GSON.fromJson(file, ResearchContainer.class);

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public static void registerResearchesSecond() {
        container.makeResearches(researches);
        container = null;
    }

    // TODO MOVE TO CLIENT ONLY
    public static void registerConnectionsAndRecipes() {
        //HashSet<String> recipeSet = new HashSet<>();
        //for (Map.Entry<String, Research> entry : researches.entrySet()) {
        //    for (String s : entry.getValue().getParents()) {
        //        if (researches.containsKey(s))
        //            connections.add(new ResearchConnection(researches.get(s), entry.getValue()));
        //    }
        //    recipeSet.addAll(entry.getValue().getRecipes());
        //}
        //for (IRecipe r : ForgeRegistries.RECIPES) {
        //    String s = r.getRecipeOutput().getItem().getRegistryName().toString();
        //    if (recipeSet.contains(s)) {
        //        if (!recipes.containsKey(s))
        //            recipes.put(s, new ArrayList<>());
        //        recipes.get(s).add(r);
        //    }
        //}
    }

    public static class ResearchContainer {
        ResearchTemp[] entries;

        public void makeResearches(Map<String, Research> map) {
            for (ResearchTemp temp : entries) {
                map.put(temp.key, temp.getResearch());
                if (DEBUG_PRINTS) System.out.println(map.get(temp.key) + "\n\n");
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
        public SubResearch[] stages;
        public SubResearch[] addenda;

        public Research getResearch() {
            return new Research(this);
        }
    }
}
