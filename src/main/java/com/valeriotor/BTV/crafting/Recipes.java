package com.valeriotor.BTV.crafting;

import com.valeriotor.BTV.items.ItemRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.ShapedArcaneRecipe;

public class Recipes {
	static ResourceLocation defaultGroup = new ResourceLocation("");
	
	public static void initArcaneRecipes() {
		ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("beyondtheveil:testitem"), new ShapedArcaneRecipe(defaultGroup, "FIRSTDREAMS", 40, new AspectList().add(Aspect.ENTROPY,1), ItemRegistry.testItem, 
				new Object[] {" A ", " A ", " A ", 'A', "dustRedstone"}));
	}
}
