package com.valeriotor.beyondtheveil.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeRegistry {
	public static Biome innsmouth = new BiomeInnsmouth();
	
	public BiomeRegistry() {
		initBiomes();
	}
	
	public static void initBiomes() {
		ForgeRegistries.BIOMES.register(innsmouth);
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(innsmouth, 4));
		BiomeManager.addSpawnBiome(innsmouth);
		
		BiomeDictionary.addTypes(innsmouth, Type.SWAMP);
	}
	
	
}
