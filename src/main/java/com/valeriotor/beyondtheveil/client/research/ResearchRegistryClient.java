package com.valeriotor.beyondtheveil.client.research;

import com.valeriotor.beyondtheveil.research.Research;
import com.valeriotor.beyondtheveil.research.ResearchRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class ResearchRegistryClient {

    public static final List<ResearchConnection> connections = new ArrayList<>();

    public static void registerConnectionsAndRecipes() {
        HashSet<String> recipeSet = new HashSet<>();
        HashSet<String> craftingRecipes = new HashSet<>();
        for(Map.Entry<String, Research> entry : ResearchRegistry.researches.entrySet()) {
            for(String s : entry.getValue().getParentKeys()) {
                if(ResearchRegistry.researches.containsKey(s))
                    connections.add(new ResearchConnection(ResearchRegistry.researches.get(s), entry.getValue()));
            }
            List<String> recipes = entry.getValue().getRecipes();
            for (String recipe : recipes) {
                String itemKey = recipe.split(";")[0];
                craftingRecipes.add(itemKey);
            }
        }
        //for (String craftingRecipe : craftingRecipes) {
        //    NonNullList<Ingredient> ingredients = Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(craftingRecipe)).get().getIngredients();
        //    System.out.println(ingredients);
        //}
    }

}
