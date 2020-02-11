package com.valeriotor.BTV.tileEntities;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.valeriotor.BTV.crafting.GearBenchRecipe;
import com.valeriotor.BTV.crafting.GearBenchRecipeRegistry;
import com.valeriotor.BTV.research.ResearchUtil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class TileGearBench extends TileEntity{
	public ItemStackHandler output = new ItemStackHandler(1) {
		public boolean isItemValid(int slot, ItemStack stack) {
			return false;
		};
	};
	private ItemStackHandler handler = new ItemStackHandler(16) {
		protected void onContentsChanged(int slot) {
			changeOutput();
		};
	};
	
	public void changeOutput() {
		GearBenchRecipe gbr = GearBenchRecipeRegistry.getRecipe(handler);
		if(gbr != null)
			output.setStackInSlot(0, gbr.getOutput());
		else
			output.setStackInSlot(0, ItemStack.EMPTY);
		markDirty();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		writeUpdateTag(compound);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		readUpdateTag(compound);
		super.readFromNBT(compound);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handler) : super.getCapability(capability, facing);
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = new NBTTagCompound();
		writeUpdateTag(compound);
		return compound;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		readUpdateTag(pkt.getNbtCompound());
	}
	
	private void sendUpdates(World worldObj) {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}
	
	private NBTTagCompound writeUpdateTag(NBTTagCompound compound) {
		compound.setTag("handler", handler.serializeNBT());
		return compound;
	}
	
	private void readUpdateTag(NBTTagCompound compound) {
		handler.deserializeNBT(compound.getCompoundTag("handler"));
	}
	
}
