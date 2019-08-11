package com.valeriotor.BTV.util;

import com.valeriotor.BTV.fluids.ModFluids;

import net.minecraft.util.EnumActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidHelper {
	
	public static boolean exactFluidTransfer(IFluidHandler destination, IFluidHandler source, FluidStack resource) {
		FluidStack fluidDrain = source.drain(resource, false);
		int fluidFill = destination.fill(resource, false);
		if(fluidDrain != null && fluidDrain.amount == resource.amount && fluidFill == resource.amount) {
			FluidUtil.tryFluidTransfer(destination, source, resource, true);
			return true;
		} else {
			return false;
		}
	}
	
}
