package com.valeriotor.BTV.tileEntities;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileBarrel extends TileEntity{
	
	
	private int fishCount;
	private int fishType;
	
	public TileBarrel() {
		this.fishCount = 0;
		this.fishType = 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.fishType = compound.getInteger("type");
		this.fishCount = compound.getInteger("count");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("type", this.fishType);
		compound.setInteger("count", this.fishCount);
		return compound;
	}
	
	
	/** Sets the type of fish in the barrel.
	 * @param Type: 0 for cod, 1 for slug
	 * 
	 */
	public void setFishType(int type) {
		this.fishType = type;
		markDirty();
		
	}
	
	public void setFishCount(int newCount) {
		this.fishCount = newCount;
		
		
		markDirty();
		sendUpdates(world);
		
	}
	
	public int getFishCount() {
		return this.fishCount;
	}
	
	public int getFishType() {
		return this.fishType;
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	private void sendUpdates(World worldObj) {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}
}
