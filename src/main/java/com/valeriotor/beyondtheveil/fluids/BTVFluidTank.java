package com.valeriotor.beyondtheveil.fluids;

import com.valeriotor.beyondtheveil.tileEntities.IUpdatableTileEntity;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;

public class BTVFluidTank extends FluidTank{
	
	private IUpdatableTileEntity tile;
	
	public BTVFluidTank(int capacity, IUpdatableTileEntity te) {
		super(capacity);
		this.tile = te;
	}
	
	@Override
	protected void onContentsChanged() {
		tile.sendUpdates();
	}

	

}
