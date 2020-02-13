package com.valeriotor.BTV.sacrifice;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.sacrifice.SacrificeRecipe.ItemFunction;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class SacrificeRecipeRegistry {
	private static final Map<String, SacrificeRecipe> recipes = new HashMap<>();
	
	public static void registerSacrificeRecipes() {
		recipes.put("sacrifice;bricks", bricks);
		recipes.put("sacrifice;staff", staff);
	}
	
	public static ItemStack getItemStack(ItemStack input) {
		for(Entry<String, SacrificeRecipe> entry : recipes.entrySet()) {
			ItemStack s = entry.getValue().getItemStack(input);
			if(s != null)
				return s;
		}
		return null;
	}
	
	public static SacrificeRecipe getRecipe(String key) {
		return recipes.get(key);
	}
	
	public static final SacrificeRecipe bricks = new SacrificeRecipe(
			new ItemFunction(Blocks.STONEBRICK, new ItemStack(BlockRegistry.BlockBloodBrick), true),
			new ItemFunction(Blocks.STONE_BRICK_STAIRS, new ItemStack(BlockRegistry.BlockBloodBrickStairs), true),
			new ItemFunction(Blocks.STONE_SLAB, new ItemStack(BlockRegistry.SlabBloodHalf), true)
			);
	
	public static final SacrificeRecipe staff = new SacrificeRecipe(
			new ItemFunction(Blocks.PRISMARINE, new ItemStack(ItemRegistry.coral_staff), false)
			);
	
}
