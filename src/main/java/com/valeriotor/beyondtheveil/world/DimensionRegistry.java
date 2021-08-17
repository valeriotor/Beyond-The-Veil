package com.valeriotor.beyondtheveil.world;

import com.valeriotor.beyondtheveil.world.arche.WorldProviderArche;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	
	public static final DimensionType ARCHE = DimensionType.register("Arche", "_arche", 2, WorldProviderArche.class, false);
	
	public static void registerDimensions() {
		
		DimensionManager.registerDimension(2, ARCHE);
		
	}
	
}
