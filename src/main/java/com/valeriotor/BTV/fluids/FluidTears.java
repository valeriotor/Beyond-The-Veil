package com.valeriotor.BTV.fluids;

import com.valeriotor.BTV.lib.References;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidTears extends Fluid{

	public FluidTears(String fluidName) {
		super(fluidName, new ResourceLocation(References.MODID,"fluids/tears_still"), new ResourceLocation(References.MODID,"fluids/tears_flow"));
		FluidRegistry.registerFluid(this);
		FluidRegistry.addBucketForFluid(this);
	}

}
