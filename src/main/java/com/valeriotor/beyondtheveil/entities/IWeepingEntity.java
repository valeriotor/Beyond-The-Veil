package com.valeriotor.beyondtheveil.entities;

import net.minecraft.util.math.BlockPos;

public interface IWeepingEntity {
	
	public BlockPos getLacrymatory();
	public void setLacrymatory(BlockPos pos);
	public int getTearTicks();
}
