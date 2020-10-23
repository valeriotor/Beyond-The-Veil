package com.valeriotor.beyondtheveil.util;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BTVChunkCache {
	private final List<ShortState> blocks = new LinkedList<>();
	
	public void setBlockState(int x, int y, int z, IBlockState state) {
		int xPiece = x & 15;
		int yPiece = y << 8;
		int zPiece = (z & 15) << 4;
		short coords = (short) (((y << 8) | ((z & 15) << 4) | (x & 15)) & 65535);
		blocks.add(new ShortState(coords, state));
		BlockPos pos = new BlockPos(7510 << 4 | (coords & 15), (coords >> 8) & 255, -13386 << 4 | ((coords >> 4) & 15));
		int woiji = 30;
	}
	
	public void setBlockState(BlockPos pos, IBlockState state) {
		setBlockState(pos.getX(), pos.getY(), pos.getZ(), state);
	}
	
	public void generate(World w, int chunkX, int chunkZ) {
		for(ShortState ss : blocks)
			ss.generate(w, chunkX, chunkZ);
	}
	
	private static class ShortState {
		private final short coords; // (15 downto 8) = y | (7 downto 4) = z | (3 downto 0) = x
		private final IBlockState state;
		
		private ShortState(short coords, IBlockState state) {
			this.coords = coords;
			this.state = state;
		}
		
		private void generate(World w, int chunkX, int chunkZ) {
			BlockPos pos = new BlockPos(chunkX << 4 | (coords & 15), (coords >> 8) & 255, chunkZ << 4 | ((coords >> 4) & 15));
			w.setBlockState(pos, state, 18);
		}
		
	}
	
}
