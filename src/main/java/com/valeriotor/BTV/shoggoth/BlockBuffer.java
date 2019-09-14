package com.valeriotor.BTV.shoggoth;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.Rotation;

public class BlockBuffer {
	
	private short[][][] coords;
	private byte[][][] metadata;
	private Block[] blocks;
	public final short xSize;
	public final short ySize;
	public final short zSize;
	
	public BlockBuffer(HashMap<Block, byte[][]> blockCoords, int xSize, int ySize, int zSize) {
		int size = blockCoords.size();
		boolean air = false;
		if(!blockCoords.containsKey(Blocks.AIR)) air = true;
		blocks = new Block[air ? size+1 : size];
		short i = 0;
		if(air) blocks[i++] = Blocks.AIR;
		this.coords = new short[xSize][ySize][zSize];
		this.metadata = new byte[xSize][ySize][zSize];
		for(Entry<Block, byte[][]> entry : blockCoords.entrySet()) {
			blocks[i] = entry.getKey();
			for(byte[] a : entry.getValue()) {
				this.coords[a[0] + xSize / 2][a[1]][a[2] + zSize / 2] = (short) i;
				this.metadata[a[0] + xSize / 2][a[1]][a[2] + zSize / 2] = a[3];
			}
			i++;
		}
		this.xSize = (short) xSize;
		this.ySize = (short) ySize;
		this.zSize = (short) zSize;
	}
	
	public int getX(int progress) {
		return (progress % xSize) - xSize / 2;
	}
	
	public int getZ(int progress) {
		return (progress / xSize) % zSize - zSize / 2;
	}
	
	public int getY(int progress) {
		return progress / xSize / zSize;
	}
	
	public Block getBlock(int x, int y, int z) {
		return blocks[coords[x + xSize / 2][y][z + zSize / 2]];
	}
	
	public IBlockState getBlockState(int x, int y, int z) {
		Block b = this.getBlock(x, y, z);
		return b.getStateFromMeta(metadata[x + xSize / 2][y][z + zSize / 2]);
	}
	
	public IBlockState getRotatedBlockState(int x, int y, int z, int rotation) {
		IBlockState state = this.getBlockState(x, y, z).withRotation(Rotation.values()[rotation]);
		return state;
	}
	
}
