package com.valeriotor.BTV.crafting;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.items.ItemRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.ShapedArcaneRecipe;

public class Recipes {
	static ResourceLocation defaultGroup = new ResourceLocation("");
	
	
	//These recipes will temporarily not work, due to (I BELIEVE) an error in the TC API, in ThaumcraftInvHelper
	public static void initArcaneRecipes() {
		ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("beyondtheveil", "fume_spreader"), new ShapedArcaneRecipe(new ResourceLocation("beyondtheveil"), "FIRSTDREAMS", 25, new AspectList().add(Aspect.ENTROPY,1), new ItemStack(BlockRegistry.FumeSpreader), 
				new Object[] {"AGA", "G G", "AAA", 'A', new ItemStack(Items.GOLD_INGOT), 'G', new ItemStack(Blocks.GLASS)}));
		
		ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("beyondtheveil:sleep_chamber"), new ShapedArcaneRecipe(defaultGroup, "FIRSTDREAMS", 100, new AspectList().add(Aspect.ENTROPY,1).add(Aspect.ORDER, 1).add(Aspect.WATER, 1).add(Aspect.EARTH, 1), new ItemStack(BlockRegistry.SleepChamber), 
				new Object[] {"GGG", "ABA", "AAA", 'A', new ItemStack(thaumcraft.api.items.ItemsTC.ingots, 2), 'G', Blocks.GLASS, 'B', Blocks.BED}));
		
	}
}
