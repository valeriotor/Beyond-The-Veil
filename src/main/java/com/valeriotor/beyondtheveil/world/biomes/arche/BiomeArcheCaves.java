package com.valeriotor.beyondtheveil.world.biomes.arche;

import java.util.Random;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.entities.ictya.EntityMuray;
import com.valeriotor.beyondtheveil.world.arche.ChunkGeneratorArche;
import com.valeriotor.beyondtheveil.world.biomes.BiomeRegistry;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeArcheCaves extends BiomeArche{

	public BiomeArcheCaves() {
		super(BiomeRegistry.ARCHE_CAVES_NAME, new BiomeProperties(BiomeRegistry.ARCHE_CAVES_NAME).setHeightVariation(2.5F).setBaseHeight(1.5F).setTemperature(0.03F).setRainfall(0.0F).setRainDisabled().setWaterColor(0));
		this.topBlock = BlockRegistry.DarkSand.getDefaultState();
		this.fillerBlock = BlockRegistry.DarkSand.getDefaultState();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityMuray.class, 60, 1, 1));
	}
	
	
	

}
