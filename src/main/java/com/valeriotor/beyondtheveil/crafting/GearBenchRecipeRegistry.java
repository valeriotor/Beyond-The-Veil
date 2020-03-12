package com.valeriotor.beyondtheveil.crafting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.tileEntities.TileGearBench;

import net.minecraft.item.Item;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class GearBenchRecipeRegistry {
	
	public static HashMap<Item, HashMap<Item, List<GearBenchRecipe>>> recipes = new HashMap<>();
	public static HashMap<String, GearBenchRecipe> recipesFromKeys = new HashMap<>();
	
	public static void registerGearBenchRecipes() {
		registerGBRecipe("statue");
		registerGBRecipe("lacrymatory");
		registerGBRecipe("revelation_ring");
		registerGBRecipe("sleep_chamber");
		registerGBRecipe("slug_bait");
		registerGBRecipe("watery_cradle");
		registerGBRecipe("blackjack");
		registerGBRecipe("sleep_chamber_advanced");
		registerGBRecipe("bleeding_belt");
		registerGBRecipe("blood_covenant");
		registerGBRecipe("blood_crown");
		registerGBRecipe("bone_tiara");
		registerGBRecipe("azacno_charm");
		registerGBRecipe("penitence_statue");
		registerGBRecipe("sacrifice_statue");
		registerGBRecipe("sacrifice_altar");
		registerGBRecipe("dream_bottle");
		registerGBRecipe("sacrificial_knife");
		registerGBRecipe("city_mapper");
		registerGBRecipe("elder_stone_bricks");
		registerGBRecipe("slug_catcher");
		registerGBRecipe("surgery_tools");
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
			recipesFromKeys.put(recipe.getOutputName().split(";")[0], recipe);
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
