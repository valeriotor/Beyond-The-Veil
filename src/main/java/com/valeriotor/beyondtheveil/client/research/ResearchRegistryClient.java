package com.valeriotor.beyondtheveil.client.research;

import com.valeriotor.beyondtheveil.research.Research;
import com.valeriotor.beyondtheveil.research.ResearchRegistry;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class ResearchRegistryClient {

    public static final List<ResearchConnection> connections = new ArrayList<>();

    public static void registerConnectionsAndRecipes() {
        HashSet<String> recipeSet = new HashSet<>();
        for(Map.Entry<String, Research> entry : ResearchRegistry.researches.entrySet()) {
            for(String s : entry.getValue().getParents()) {
                if(ResearchRegistry.researches.containsKey(s))
                    connections.add(new ResearchConnection(ResearchRegistry.researches.get(s), entry.getValue()));
            }
            recipeSet.addAll(entry.getValue().getRecipes());
        }
    }

}
