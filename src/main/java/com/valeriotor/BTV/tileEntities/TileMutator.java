package com.valeriotor.BTV.tileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileMutator extends TileEntity implements ITickable{
	
	private int mutation = 0;
	
	
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("mutation", this.mutation);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.mutation = compound.getInteger("mutation");
		super.readFromNBT(compound);
	}
	
	@Override
	public void update() {
		
	}
	
	public int getMutation() {
		return this.mutation;
	}

}
