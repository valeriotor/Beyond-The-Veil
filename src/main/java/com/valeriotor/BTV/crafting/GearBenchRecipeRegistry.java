package com.valeriotor.BTV.crafting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.tileEntities.TileGearBench;

import net.minecraft.item.Item;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class GearBenchRecipeRegistry {
	
	public static HashMap<Item, HashMap<Item, List<GearBenchRecipe>>> recipes = new HashMap<>();
	public static HashMap<String, GearBenchRecipe> recipesFromKeys = new HashMap<>();
	
	public static void registerGearBenchRecipes() {
		registerGBRecipe("statue");
		registerGBRecipe("lacrymatory");
	}
	
	private static void registerGBRecipe(String fileName) {
		try {
			String file = Resources.toString(BeyondTheVeil.class.getResource("/assets/beyondtheveil/benchrecipes/" + fileName + ".json"), Charsets.UTF_8);
			GearBenchRecipe recipe = GearBenchRecipe.getRecipe(file);
			Item zerozero = recipe.getStackInSlot(0).getItem();
			Item oneone = recipe.getStackInRowColumn(1, 1).getItem();
			if(!recipes.containsKey(zerozero)) {
				recipes.put(zerozero, new HashMap<>());
			}
			HashMap<Item, List<GearBenchRecipe>> map = recipes.get(zerozero);
			if(!map.containsKey(oneone)) {
				map.put(oneone, new ArrayList<>());
			}
			List<GearBenchRecipe> list = map.get(oneone);
			list.add(recipe);
			recipesFromKeys.put(recipe.getOutputName(), recipe);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static GearBenchRecipe getRecipe(IItemHandler handler) {
		if(recipes.containsKey(handler.getStackInSlot(0).getItem())) {
			HashMap<Item, List<GearBenchRecipe>> map = recipes.get(handler.getStackInSlot(0).getItem());
			if(map.containsKey(handler.getStackInSlot(5).getItem())) {
				List<GearBenchRecipe> list = map.get(handler.getStackInSlot(5).getItem());
				for(GearBenchRecipe gbr : list) {
					if(gbr.checksOut(handler))
						return gbr;
				}
			}
			return null;
		}
		return null;
	}
	
}
