package com.valeriotor.beyondtheveil.fluids;

import com.valeriotor.beyondtheveil.tileEntities.TileLacrymatory;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TearTank extends FluidTank{
	
	private TileLacrymatory tl;
	
	public TearTank(int capacity, TileLacrymatory tl) {
		super(capacity);
		this.tl = tl;
	}
	
	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return fluid.getFluid() == ModFluids.tears && canFill();
	}
	
	@Override
	protected void onContentsChanged() {
		tl.sendUpdates();
		super.onContentsChanged();
	}
	
}
