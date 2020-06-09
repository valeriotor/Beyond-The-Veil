package com.valeriotor.beyondtheveil.world.biomes.arche;

import java.util.Random;

import com.valeriotor.beyondtheveil.world.arche.ChunkGeneratorArche;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeArche extends Biome{

	public BiomeArche(String name, BiomeProperties properties) {
		super(properties);
		this.setRegistryName(name);
		this.spawnableWaterCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableMonsterList.clear();
	}
	
	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer primer, int x, int z, double noiseVal) {
		x&=15;
		z&=15;
		int y = ChunkGeneratorArche.seaLevel();
		primer.setBlockState(x, y+2, z, ChunkGeneratorArche.BEDROCK);
		primer.setBlockState(x, y+1, z, ChunkGeneratorArche.DARKSAND);
		primer.setBlockState(x, y, z, ChunkGeneratorArche.DARKSAND);
		for(int i = 0; i < 2; i++) {
			if(i < rand.nextInt(3)) {
				primer.setBlockState(x, y-i-1, z, ChunkGeneratorArche.DARKSAND);
			} else break;
		}
	}

}
