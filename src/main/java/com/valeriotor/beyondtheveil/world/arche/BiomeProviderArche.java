package com.valeriotor.beyondtheveil.world.arche;

import java.util.List;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.world.biomes.BiomeRegistry;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.storage.WorldInfo;

public class BiomeProviderArche extends BiomeProvider{
	
	
	public BiomeProviderArche(WorldInfo worldInfo) {
		super(worldInfo);
	}

	@Override
	public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
		GenLayer layer = new GenLayerIsland(1L);
		layer = new GenLayerBiomeArche(2500L, layer);
		layer = new GenLayerZoom(2501L, layer);
		layer = new GenLayerZoom(2502L, layer);
		layer = new GenLayerZoom(2503L, layer);
		layer = new GenLayerZoom(2504L, layer);
		layer = new GenLayerZoom(2505L, layer);
		GenLayer layer2 = new GenLayerVoronoiZoom(10L, layer);
		layer2.initWorldGenSeed(seed);
		return super.getModdedBiomeGenerators(worldType, seed, new GenLayer[] {layer, layer2});
	}
	
	
	
}
