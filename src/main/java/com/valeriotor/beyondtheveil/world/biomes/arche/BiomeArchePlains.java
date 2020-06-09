package com.valeriotor.beyondtheveil.world.biomes.arche;

import com.valeriotor.beyondtheveil.world.biomes.BiomeRegistry;

import net.minecraft.world.biome.Biome.BiomeProperties;

public class BiomeArchePlains extends BiomeArche{

	public BiomeArchePlains() {
		super(BiomeRegistry.ARCHE_PLAINS_NAME, new BiomeProperties(BiomeRegistry.ARCHE_PLAINS_NAME).setHeightVariation(0.04F).setBaseHeight(-1.8F).setTemperature(0.03F).setRainfall(0.0F).setRainDisabled().setWaterColor(0));
	}

}
