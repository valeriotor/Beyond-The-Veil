package com.valeriotor.BTV.tileEntities;

import javax.annotation.Nullable;

import com.valeriotor.BTV.dreaming.Memory;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;

public class TileFumeSpreader extends TileEntity{
	private Memory memory;
	
	public TileFumeSpreader() {
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if(memory != null) compound.setString("memory", memory.getDataName());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("memory")) this.memory = Memory.getMemoryFromDataName(compound.getString("memory"));
		super.readFromNBT(compound); 
	}
	
	public Memory setMemory(Memory memory) {
		this.memory = memory;
		markDirty();
		sendUpdates(world);
		return this.memory;
	}
	
	public Memory getMemory() {
		return this.memory;
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
