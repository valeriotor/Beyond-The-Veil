package com.valeriotor.BTV.tileEntities;

import javax.annotation.Nullable;

import com.valeriotor.BTV.entities.EntityWeeper;
import com.valeriotor.BTV.fluids.TearTank;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.TileFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileLacrymatory extends TileFluidHandler{
	
	private EntityWeeper weeper;
	private int amount = 0;
	
	public TileLacrymatory() {
		this.tank = new TearTank(4000, this);
	}
	
	public boolean setWeeper(EntityWeeper weeper) {
		if(weeper == null) {
			this.weeper = null;
			return true;
		}
		if(this.weeper == null) {
			this.weeper = weeper;
			return true;
		}	
		return false;
		
	}
	
	public EntityWeeper getWeeper() {
		return this.weeper;
	}
	
	public void fillWithTears(FluidStack stack) {
		this.tank.fill(stack, true);
	}
	
	@Nullable
	public IFluidHandler getFluidHandler() {
		return this.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setInteger("amount", tank.getFluidAmount());
		return super.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		if(tag.hasKey("amount")) this.amount = tag.getInteger("amount");
		super.readFromNBT(tag);
	}
	
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
		//this.amount = pkt.getNbtCompound().getInteger("amount");
	}
	
	/** Used for rendering
	 * 
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public int getAmount() {
		return this.amount;
	}

}
