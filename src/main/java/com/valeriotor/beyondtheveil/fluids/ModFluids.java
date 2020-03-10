package com.valeriotor.beyondtheveil.fluids;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.blocks.fluid.BlockFluidTears;

public class ModFluids {
	
	public static FluidTears tears;
	
	public static void registerFluids() {
		tears = new FluidTears("tears");
		
		BlockRegistry.BlockFluidTears = new BlockFluidTears();
	}
	
	public static void renderFluids() {
		BlockRegistry.BlockFluidTears.render();
	}
	
}
