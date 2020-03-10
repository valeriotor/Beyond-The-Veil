package com.valeriotor.beyondtheveil.shoggoth;

import java.awt.Point;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShoggothLongBuilding extends ShoggothBuilding{
	
	private Point vertex1;
	private Point vertex2;
	private int height;
	private final int length;
	
	public ShoggothLongBuilding(World w, NBTTagCompound nbt) {
		super(w, nbt);
		Point vertex1 = new Point(nbt.getInteger("v1x"), nbt.getInteger("v1y"));
		Point vertex2 = new Point(nbt.getInteger("v2x"), nbt.getInteger("v2y"));
		if((rotation & 1) == 1) {
			if(vertex1.x < vertex2.x) {
				this.vertex1 = vertex1;
				this.vertex2 = vertex2;
			}else {
				this.vertex1 = vertex2;
				this.vertex2 = vertex1;
			}
			this.length = Math.abs(vertex1.x - vertex2.x);
		}else {
			if(vertex1.y < vertex2.y) {
				this.vertex1 = vertex1;
				this.vertex2 = vertex2;
			}else {
				this.vertex1 = vertex2;
				this.vertex2 = vertex1;
			}
			this.length = Math.abs(vertex1.y - vertex2.y);
		}
		int[] coords = {progress / buffer.zSize / buffer.ySize, 0, 0};
		orderUp(coords);
		height = w.getHeight(vertex1.x + coords[0], vertex1.y + coords[2]);
	}
	
	@Override
	public boolean isDone() {
		return progress >= total * length;
	}
	
	@Override
	protected void placeBlockInternal(World w, int[] coords, IBlockState state) {
		coords[2] = progress / buffer.xSize / buffer.ySize;
		orderUp(coords);
		if(progress % (buffer.xSize * buffer.ySize) == 0) height = world.getHeight(vertex1.x + coords[0], vertex1.y + coords[2]);
		BlockPos pos = new BlockPos(vertex1.x + coords[0], height + coords[1], vertex1.y + coords[2]);
		IBlockState currentState = w.getBlockState(pos);
		if((currentState.getBlockHardness(this.world, pos) < 0 || state.getBlock() == currentState.getBlock()) && !isDone()) {
			progress++;
			this.placeBlock(w);
		} else {
			w.setBlockState(pos, state);
			progress++;
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("v1x", vertex1.x);
		nbt.setInteger("v1y", vertex1.y);
		nbt.setInteger("v2x", vertex2.x);
		nbt.setInteger("v2y", vertex2.y);
		return super.writeToNBT(nbt);
	}
	
	@Override
	protected int[] orderUp(int[] coords) {
		if(horizontal()) {
			int temp = coords[0];
			coords[0] = coords[2];
			coords[2] = -temp - 1;
		}
		return coords;
	}
	
}
