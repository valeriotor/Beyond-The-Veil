package com.valeriotor.beyondtheveil.world.arche;

import java.util.List;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.world.biomes.BiomeRegistry;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeArche extends GenLayer{

	private static final List<Biome> BIOMES = Lists.newArrayList(BiomeRegistry.arche_caves, BiomeRegistry.arche_plains);
	public GenLayerBiomeArche(long p_i2125_1_, GenLayer parent) {
		super(p_i2125_1_);
		this.parent = parent;
	}

	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] aint = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

        for (int i = 0; i < areaHeight; ++i)
        {
            for (int j = 0; j < areaWidth; ++j)
            {
                this.initChunkSeed((long)(j + areaX), (long)(i + areaY));
                aint1[j + i * areaWidth] = Biome.getIdForBiome(BIOMES.get(nextInt(BIOMES.size())));
            }
        }

        return aint1;
	}
	

}
