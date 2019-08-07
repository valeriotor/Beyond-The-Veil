package com.valeriotor.BTV.fluids;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.blocks.fluid.BlockFluidTears;

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
