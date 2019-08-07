package com.valeriotor.BTV.tileEntities;

import com.valeriotor.BTV.entities.EntityWeeper;
import com.valeriotor.BTV.fluids.TearTank;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.TileFluidHandler;

public class TileLacrymatory extends TileFluidHandler{
	
	private EntityWeeper weeper;
	
	public TileLacrymatory() {
		this.tank = new TearTank(4000);
	}
	
	public boolean setWeeper(EntityWeeper weeper) {
		if(weeper == null) {
			this.weeper = null;
			return true;
		}
		if(this.weeper == null) {
			this.weeper = weeper;
			return true;
		}	
		return false;
		
	}
	
	public EntityWeeper getWeeper() {
		return this.weeper;
	}
	
	public void fillWithTears(FluidStack stack) {
		this.tank.fill(stack, true);
	}

}
