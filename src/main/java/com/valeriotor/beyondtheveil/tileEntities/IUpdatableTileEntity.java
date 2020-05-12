package com.valeriotor.beyondtheveil.tileEntities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IUpdatableTileEntity {
	
	public default void sendUpdates() {
		if(this instanceof TileEntity) {
			TileEntity te = (TileEntity)this;
			te.markDirty();
			BlockPos pos = te.getPos();
			World world = te.getWorld();
			world.markBlockRangeForRenderUpdate(pos, pos);
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
			world.scheduleBlockUpdate(pos,te.getBlockType(),0,0);
		}
	}
	
}
