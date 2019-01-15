package com.valeriotor.BTV.research;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import thaumcraft.api.research.ScanBlock;

public class TCRegistries {
	
	public static void register() {
		registerCards();
		registerScanResearch();
	}
	
	public static void registerCards() {
		thaumcraft.api.research.theorycraft.TheorycraftManager.registerCard(CardStars.class);
	}
	
	public static void registerScanResearch() {
	}
	
}
