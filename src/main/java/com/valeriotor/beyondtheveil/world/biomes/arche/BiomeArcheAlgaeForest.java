package com.valeriotor.beyondtheveil.world.biomes.arche;

import java.util.Random;

import com.valeriotor.beyondtheveil.world.arche.gen.WorldGenAlgaeTree;
import com.valeriotor.beyondtheveil.world.biomes.BiomeRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class BiomeArcheAlgaeForest extends BiomeArche{
	public static final WorldGenAlgaeTree ALGAE_TREE = new WorldGenAlgaeTree();
	public BiomeArcheAlgaeForest() {
		super(BiomeRegistry.ARCHE_ALGAE_FOREST_NAME, new BiomeProperties("Arche Algae Forest").setHeightVariation(0.04F).setBaseHeight(-2.4F).setTemperature(0.03F).setRainfall(0.0F).setRainDisabled().setWaterColor(0));
	}
	
	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos) {
		for(int count = 0; count < 2; count ++) {
			int i = rand.nextInt(16) + 8;
	        int j = rand.nextInt(16) + 8;
	        BlockPos.MutableBlockPos newPos = new MutableBlockPos(pos.add(i, 0, j));
	        int y = 0;
	        for(; y < 30; y++) {
	        	newPos.move(EnumFacing.UP);
	        	if(worldIn.getBlockState(newPos).getBlock() == Blocks.WATER) break;
	        }
	        ALGAE_TREE.generate(worldIn, rand, newPos);
		}
	}

}
