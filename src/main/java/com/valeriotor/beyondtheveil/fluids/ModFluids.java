package com.valeriotor.beyondtheveil.fluids;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.blocks.fluid.BlockFluidTears;
import com.valeriotor.beyondtheveil.lib.References;

public class ModFluids {
	
	public static final String FLUID_MODEL_PATH = References.MODID + ":fluid";
	
	public static FluidTears tears;
	
	public static void registerFluids() {
		tears = new FluidTears("tears");
		
		BlockRegistry.BlockFluidTears = new BlockFluidTears();
	}
	
	public static void renderFluids() {
		BlockRegistry.BlockFluidTears.render();
	}
	
}
