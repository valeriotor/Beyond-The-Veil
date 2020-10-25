package com.valeriotor.beyondtheveil.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class BlockCoords {
	
	private final Block block;
	private final byte[][] coords;
	
	public BlockCoords(Block block, byte[][] coords) {
		this.block = block;
		this.coords = coords;
	}
	
	public BlockCoords(Entry<Block, byte[][]> entry) {
		this.block = entry.getKey();
		this.coords = entry.getValue();
	}
	
	public void generate(World w, BlockPos center) {
		for(byte[] xyzm : this.coords) {
			BlockPos pos = center.add(xyzm[0], xyzm[1], xyzm[2]);
			w.setBlockState(pos, block.getStateFromMeta(xyzm[3]), 2);
		}
	}
	
	public void generate(BlockPos center, Map<Long, BTVChunkCache> chunks, Set<Long> usedChunks) {
		for(byte[] xyzm : this.coords) {
			BlockPos pos = center.add(xyzm[0], xyzm[1], xyzm[2]);
			int chunkX = pos.getX() >> 4;
			int chunkZ = pos.getZ() >> 4;
			long cPos = ChunkPos.asLong(chunkX, chunkZ);
			if(!usedChunks.contains(cPos)) {
				BTVChunkCache cache = chunks.get(cPos);
				if(cache == null) {
					cache = new BTVChunkCache();
					chunks.put(cPos, cache);
				}
				cache.setBlockState(pos, block.getStateFromMeta(xyzm[3]));
			}
		}
	}
	
	public Block getBlock() {
		return block;
	}
}