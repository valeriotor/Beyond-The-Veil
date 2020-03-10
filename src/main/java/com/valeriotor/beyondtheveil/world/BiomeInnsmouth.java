package com.valeriotor.beyondtheveil.world;

import java.util.Random;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;

import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMushroomIsland;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeInnsmouth extends Biome{

	public BiomeInnsmouth() {
		super(new BiomeProperties("Voided").setBaseHeight(-0.083F).setTemperature(0.5F).setHeightVariation(0.07F).setWaterColor(28));
		this.setRegistryName("biome_innsmouth");
		this.topBlock = BlockRegistry.DarkSand.getDefaultState();
		this.fillerBlock = BlockRegistry.DarkSand.getDefaultState();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.decorator.generateFalls = false;
		
	}
	
	
	
	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        double d0 = GRASS_COLOR_NOISE.getValue((double)x * 0.25D, (double)z * 0.25D);

        if (d0 > 0.0D)
        {
            int i = x & 15;
            int j = z & 15;

            for (int k = 255; k >= 0; --k)
            {
                if (chunkPrimerIn.getBlockState(j, k, i).getMaterial() != Material.AIR)
                {
                    if (k == 62 && chunkPrimerIn.getBlockState(j, k, i).getBlock() != Blocks.WATER)
                    {
                        chunkPrimerIn.setBlockState(j, k, i, WATER);

                        
                    }

                    break;
                }
            }
        }

        this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
	
	
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return 0;
	}

}
