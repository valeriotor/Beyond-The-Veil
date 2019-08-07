package com.valeriotor.BTV.fluids;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TearTank extends FluidTank{

	public TearTank(int capacity) {
		super(capacity);
	}
	
	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return fluid.getFluid() == ModFluids.tears && canFill();
	}

}
