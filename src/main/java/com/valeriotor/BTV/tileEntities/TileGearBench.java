package com.valeriotor.BTV.tileEntities;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import com.valeriotor.BTV.research.ResearchUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class TileGearBench extends TileEntity implements IInventory{
	
	private NonNullList<ItemStack> benchContents = NonNullList.<ItemStack>withSize(16, ItemStack.EMPTY);
	private HashMap<UUID, IInventoryChangedListener> listeners = new HashMap<>();
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, this.benchContents);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		ItemStackHelper.loadAllItems(compound, this.benchContents);
	}

	@Override
	public String getName() {
		return "container.gearbench";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 16;
	}

	@Override
	public boolean isEmpty() {
		 for(ItemStack itemstack : this.benchContents)
	            if(!itemstack.isEmpty())
	                return false;
		 return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if(index < 16)
			return this.benchContents.get(index);
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		this.markDirty();
		return ItemStackHelper.getAndSplit(this.benchContents, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		this.markDirty();
		return this.benchContents.remove(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.benchContents.set(index, stack);
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer p) {
		return ResearchUtil.isResearchKnown(p, "SLEEPCHAMBER");
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		this.benchContents.clear();
	}
	
	public void markDirty() {
		for(Entry<UUID, IInventoryChangedListener> entry : listeners.entrySet()) {
			entry.getValue().onInventoryChanged(this);
		}
    }
	
	public void addListener(EntityPlayer p, IInventoryChangedListener listener) {
		listeners.put(p.getPersistentID(), listener);
	}
	
	public void removeListener(EntityPlayer p) {
		listeners.remove(p.getPersistentID());
	}
	
}
