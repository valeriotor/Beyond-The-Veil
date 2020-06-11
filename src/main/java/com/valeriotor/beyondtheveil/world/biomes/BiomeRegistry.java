package com.valeriotor.beyondtheveil.world.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.valeriotor.beyondtheveil.util.ConfigLib;
import com.valeriotor.beyondtheveil.world.biomes.arche.BiomeArcheAlgaeForest;
import com.valeriotor.beyondtheveil.world.biomes.arche.BiomeArcheCaves;
import com.valeriotor.beyondtheveil.world.biomes.arche.BiomeArchePlains;

import net.minecraft.util.WeightedRandom;
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
	public static Biome arche_algae_forest = new BiomeArcheAlgaeForest();
	
	public static final List<BiomeEntry> ARCHE_BIOMES;
	static {
		List<BiomeEntry> temp = new ArrayList<>();
		temp.add(new BiomeEntry(arche_caves, 30));
		temp.add(new BiomeEntry(arche_plains, 30));
		temp.add(new BiomeEntry(arche_algae_forest, 1));
		ARCHE_BIOMES = ImmutableList.copyOf(temp);
	}
	
	public static final String ARCHE_CAVES_NAME = "arche_caves";
	public static final String ARCHE_PLAINS_NAME = "arche_plains";
	public static final String ARCHE_ALGAE_FOREST_NAME = "arche_algae_forest";
	
	public static void initBiomes() {
		ForgeRegistries.BIOMES.register(innsmouth);
		ForgeRegistries.BIOMES.register(arche_caves);
		ForgeRegistries.BIOMES.register(arche_plains);
		ForgeRegistries.BIOMES.register(arche_algae_forest);
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(innsmouth, ConfigLib.innsmouthWeight));
		BiomeManager.addSpawnBiome(innsmouth);
		

		
		BiomeDictionary.addTypes(innsmouth, Type.SWAMP);
		BiomeDictionary.addTypes(arche_caves, Type.OCEAN, Type.SPOOKY);
		BiomeDictionary.addTypes(arche_plains, Type.OCEAN, Type.SPOOKY);
		BiomeDictionary.addTypes(arche_algae_forest, Type.OCEAN, Type.SPOOKY);
	}
	
	
}
