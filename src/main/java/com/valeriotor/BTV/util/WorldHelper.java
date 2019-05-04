package com.valeriotor.BTV.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WorldHelper {
	
	public static BlockPos findClosestBiomeOfType(Biome biome, BlockPos pos, World w, int range) {
		BlockPos pos1;
		for(int i = 0; i*50 < range; i++) {
			for(int j = -i; j <= i; j++) {
				int addendum = (j == -i || j == i) ? 1 : 2*i;
				for(int k = -i; k <= i; k += addendum) {
					pos1 = pos.add(j*50, 0, k*50);
					if(w.getBiome(pos1) == biome) return pos1;
				}
			}
		}
		
		
		return null;
	}
	
	
}
