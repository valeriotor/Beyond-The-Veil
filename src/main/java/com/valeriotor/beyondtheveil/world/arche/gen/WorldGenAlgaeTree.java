package com.valeriotor.beyondtheveil.world.arche.gen;

import java.util.Random;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAlgaeTree extends WorldGenerator{
	private static final IBlockState ALGAE_BLOCK = BlockRegistry.BlockThickAlgae.getDefaultState();
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		int height = rand.nextInt(4)+6;
		int width = rand.nextInt(3)+2;
		int topWidth = rand.nextInt(5)+4;
		MutableBlockPos pos = new MutableBlockPos(position);
		EnumFacing side = EnumFacing.getHorizontal(rand.nextInt(4));
		boolean flag = false;
		for(int i = 0; i < height; i++) {
			if(!flag) {
				if(i == height - width - 1) {
					flag = true;
					width--;
					pos.move(side);
				}
			} else {
				if(i == height - width) {
					pos.move(side);
				}
			}
			worldIn.setBlockState(pos, ALGAE_BLOCK);
			pos.move(EnumFacing.UP);
		}
		worldIn.setBlockState(pos, ALGAE_BLOCK);
		for(int i = 1; i < topWidth; i++) {
			pos.move(EnumFacing.NORTH);
			pos.move(EnumFacing.EAST);
			this.createTopRing(worldIn, pos, i*2+1);
			if(i > 1) pos.move(EnumFacing.DOWN);
		}
		return true;
	}
	
	private void createTopRing(World worldIn, MutableBlockPos pos, int width) {
		EnumFacing facing = EnumFacing.WEST;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < width; j++) {
				worldIn.setBlockState(pos, ALGAE_BLOCK);
				if(j < width-1)
					pos.move(facing);
			}
			facing = facing.rotateYCCW();
		}
	}

}
