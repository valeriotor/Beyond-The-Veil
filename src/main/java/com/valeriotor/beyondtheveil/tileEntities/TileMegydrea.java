package com.valeriotor.beyondtheveil.tileEntities;

import javax.annotation.Nullable;

import com.valeriotor.beyondtheveil.fluids.BTVFluidTank;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.TileFluidHandler;

public class TileMegydrea extends TileFluidHandler implements IUpdatableTileEntity{
	
	public TileMegydrea() {
		this.tank = new BTVFluidTank(40000, this);
	}
	
	@Override
	public void sendUpdates() {
		markDirty();
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	public int getAmount() {
		return this.tank.getFluidAmount();
	}
	
	public FluidStack getFluidStack() {
		return this.tank.getFluid();
	}
	
	public Fluid getFluid() {
		FluidStack stack = this.getFluidStack();
		if(stack != null)
			return stack.getFluid();
		return null;
	}
	
}
