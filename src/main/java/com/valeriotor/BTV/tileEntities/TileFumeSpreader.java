package com.valeriotor.BTV.tileEntities;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;

public class TileFumeSpreader extends TileEntity{
	private Aspect chosenAspect;
	public TileFumeSpreader() {
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if(chosenAspect != null) compound.setString("containing", this.chosenAspect.getName());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.chosenAspect = thaumcraft.api.aspects.Aspect.getAspect(compound.getString("containing").toLowerCase());
		super.readFromNBT(compound); 
	}
	
	public Aspect setAspect(Aspect aspect) {
		this.chosenAspect = aspect;
		markDirty();
		sendUpdates(world);
		return this.chosenAspect;
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
