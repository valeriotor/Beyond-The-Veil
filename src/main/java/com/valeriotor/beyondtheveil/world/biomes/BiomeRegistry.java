package com.valeriotor.beyondtheveil.world.biomes;

import com.valeriotor.beyondtheveil.util.ConfigLib;
import com.valeriotor.beyondtheveil.world.biomes.arche.BiomeArcheCaves;
import com.valeriotor.beyondtheveil.world.biomes.arche.BiomeArchePlains;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeRegistry {
	public static Biome innsmouth = new BiomeInnsmouth();
	public static Biome arche_caves = new BiomeArcheCaves();
	public static Biome arche_plains = new BiomeArchePlains();

	public static final String ARCHE_CAVES_NAME = "arche_caves";
	public static final String ARCHE_PLAINS_NAME = "arche_plains";
	
	public static void initBiomes() {
		ForgeRegistries.BIOMES.register(innsmouth);
		ForgeRegistries.BIOMES.register(arche_caves);
		ForgeRegistries.BIOMES.register(arche_plains);
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(innsmouth, ConfigLib.innsmouthWeight));
		BiomeManager.addSpawnBiome(innsmouth);
		

		
		BiomeDictionary.addTypes(innsmouth, Type.SWAMP);
		BiomeDictionary.addTypes(arche_caves, Type.OCEAN, Type.SPOOKY);
		BiomeDictionary.addTypes(arche_plains, Type.OCEAN, Type.SPOOKY);
	}
	
	
}
