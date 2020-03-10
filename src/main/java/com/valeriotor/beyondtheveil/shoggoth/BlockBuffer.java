package com.valeriotor.beyondtheveil.shoggoth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
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
		blocks[i++] = Blocks.AIR;
		this.coords = new short[xSize][ySize][zSize];
		this.metadata = new byte[xSize][ySize][zSize];
		if(!air) {
			for(byte[] a : blockCoords.get(Blocks.AIR)) {
				this.coords[a[0] + xSize / 2][a[1]][a[2] + zSize / 2] = (short) 0;
			}
		}
		for(Entry<Block, byte[][]> entry : blockCoords.entrySet()) {
			if(entry.getKey() == Blocks.AIR) continue;
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
	
	public BlockBuffer(NBTTagCompound nbt) {
		this.xSize = nbt.getShort("xSize");
		this.ySize = nbt.getShort("ySize");
		this.zSize = nbt.getShort("zSize");
		this.coords = new short[xSize][ySize][zSize];
		this.metadata = new byte[xSize][ySize][zSize];
		int i = 0;
		List<Block> blocks = new ArrayList<>();
		while(nbt.hasKey(String.format("b%d", i))) blocks.add(Block.getBlockFromName(nbt.getString(String.format("b%d", i++)))); 
		this.blocks = (Block[]) blocks.toArray();
		int x = 0, y, z;
		NBTTagCompound xnbt, ynbt;
		while(nbt.hasKey(String.format("x%d", x))) {
			y = 0;
			xnbt = nbt.getCompoundTag(String.format("x%d", x));
			while(xnbt.hasKey(String.format("y%d", y))) {
				z = 0;
				ynbt = nbt.getCompoundTag("y" + y);
				while(ynbt.hasKey("s" + z)) {
					this.coords[x][y][z] = ynbt.getShort("s" + z);
					this.metadata[x][y][z] = ynbt.getByte("b" + z);
				}
			}
		}
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
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound xnbt;
		NBTTagCompound ynbt;
		for(int x = 0; x < this.xSize; x++) {
			xnbt = new NBTTagCompound();
			for(int y = 0; y < this.ySize; y++) {
				ynbt = new NBTTagCompound();
				for(int z = 0; z < this.zSize; z++) {
					ynbt.setShort(String.format("s%d", z), this.coords[x][y][z]);
					ynbt.setByte(String.format("b%d", z), this.metadata[x][y][z]);
				}
				xnbt.setTag(String.format("y%d", y), ynbt);
			}
			nbt.setTag(String.format("x%d", x), xnbt);
		}
		
		NBTTagCompound blocks = new NBTTagCompound();
		for(int i = 0; i < this.blocks.length; i++) {
			blocks.setString(String.format("b%d", i), this.blocks[i].getRegistryName().toString());
		}
		nbt.setTag("block", blocks);
		nbt.setInteger("xSize", xSize);
		nbt.setInteger("ySize", ySize);
		nbt.setInteger("zSize", zSize);
		return nbt;
	}
	
}
