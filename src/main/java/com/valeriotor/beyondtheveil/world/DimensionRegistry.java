package com.valeriotor.beyondtheveil.world;

import com.valeriotor.beyondtheveil.util.ConfigLib;
import com.valeriotor.beyondtheveil.world.arche.WorldProviderArche;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	
	public static DimensionType ARCHE;
	
	public static void registerDimensions() {
		ARCHE = DimensionType.register("Arche", "_arche", ConfigLib.archeId, WorldProviderArche.class, false);
		DimensionManager.registerDimension(ConfigLib.archeId, ARCHE);
		
	}
	
}
