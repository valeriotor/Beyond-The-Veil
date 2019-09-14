package com.valeriotor.BTV.shoggoth;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ShoggothBuilding {
	
	public static ShoggothBuilding getBuilding(World w, NBTTagCompound nbt) {
		BuildingTemplate b = BuildingRegistry.templates[nbt.getInteger("index")];
		if(b.longBuilding) return new ShoggothLongBuilding(w, nbt);
		return new ShoggothBaseBuilding(w, nbt);
	}
	
	public final BuildingTemplate building;
	public final World world;
	public final int centerX;
	public final int centerY;
	public final int centerZ;
	public final int rotation;
	public int progress = 0;
	public int total = 0;
	public BlockBuffer buffer;
	
	public ShoggothBuilding(World w, NBTTagCompound nbt) {
		this.world = w;
		int index = nbt.getInteger("index");
		this.building = BuildingRegistry.templates[index];
		this.centerX = nbt.getInteger("centerX");
		this.centerZ = nbt.getInteger("centerZ");
		if(!nbt.hasKey("centerY"))
			this.centerY = w.getHeight(centerX, centerZ);
		else this.centerY = nbt.getInteger("centerY");
		this.rotation = nbt.getInteger("rot");
		this.buffer = building.getBlockBuffer();
		if(this.buffer != null) this.total = buffer.xSize * buffer.ySize * buffer.zSize;
		if(nbt.hasKey("progress")) this.progress = nbt.getInteger("progress");
	}
	
	public void placeBlock(World w) {
		int[] coords = {buffer.getX(progress % total), buffer.getY(progress % total), buffer.getZ(progress % total)};
		Block b = buffer.getBlock(coords[0], coords[1], coords[2]);
		while(coords[1] > 4 && b == Blocks.AIR) {
			progress++;
			coords[0] = buffer.getX(progress % total);
			coords[1] = buffer.getY(progress % total);
			coords[2] = buffer.getZ(progress % total);
			b = buffer.getBlock(coords[0], coords[1], coords[2]);
		}
		IBlockState state = buffer.getRotatedBlockState(coords[0], coords[1], coords[2], rotation);
		this.placeBlockInternal(w, coords, state);
	}
	
	protected void placeBlockInternal(World w, int[] coords, IBlockState state) {
		orderUp(coords);
		w.setBlockState(new BlockPos(centerX + coords[0], centerY + coords[1], centerZ + coords[2]), state);
		progress++;
	}
	
	protected int[] orderUp(int[] coords) {
		if(rotation == 1) {
			int temp = coords[0];
			coords[0] = -coords[2];
			coords[2] = temp;
		} else if(rotation == 2) {
			coords[0] = -coords[0];
			coords[2] = -coords[2];
		} else if(rotation == 3) {
			int temp = coords[0];
			coords[0] = coords[2];
			coords[2] = -temp;
		}
		return coords;
	}
	
	
	public boolean isDone() {
		return progress >= total; // Obviously temporary
	}
	
	public boolean horizontal() {
		return (rotation & 1) == 1;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("index", this.building.index);
		nbt.setInteger("centerX", this.centerX);
		nbt.setInteger("centerY", this.centerY);
		nbt.setInteger("centerZ", this.centerZ);
		nbt.setInteger("rot", this.rotation);
		nbt.setInteger("progress", this.progress);
		return nbt;
	}
	
}
