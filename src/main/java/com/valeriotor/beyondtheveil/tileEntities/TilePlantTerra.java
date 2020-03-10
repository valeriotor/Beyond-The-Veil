package com.valeriotor.beyondtheveil.tileEntities;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TilePlantTerra extends TileEntity implements ITickable{
	
	private int counter = 0;
	
	public TilePlantTerra() {}
	
	@Override
	public void update() {
		if(this.world.getBlockState(pos.down()).getBlock() != Blocks.AIR && this.world.getBlockState(this.pos.down()).getBlockHardness(this.world, this.pos.up()) >= 0) this.counter++;
		else this.counter = 0;
		if(this.counter >= 50) {
			this.counter = 0;
			this.world.destroyBlock(this.pos.down(), true);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("counter", this.counter);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.counter = compound.getInteger("counter");
		super.readFromNBT(compound);
	}
	
	
	
	
}
