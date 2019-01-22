package com.valeriotor.BTV.research;

import com.valeriotor.BTV.crafting.Recipes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import thaumcraft.api.research.ScanBlock;

public class TCRegistries {
	
	public static void register() {
		registerCards();
		registerScanResearch();
		Recipes.initArcaneRecipes();
    	Recipes.initCrucibleRecipes();
    	Recipes.initInfusionRecipes();
	}
	
	public static void registerCards() {
		thaumcraft.api.research.theorycraft.TheorycraftManager.registerCard(CardStars.class);
		thaumcraft.api.research.theorycraft.TheorycraftManager.registerCard(CardTablet.class);
	}
	
	public static void registerScanResearch() {
	}
	
}
