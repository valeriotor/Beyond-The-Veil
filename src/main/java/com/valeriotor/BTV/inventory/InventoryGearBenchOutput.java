package com.valeriotor.BTV.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryGearBenchOutput implements IInventory{

    private final NonNullList<ItemStack> stackResult = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	
	@Override
	public String getName() {
		return "inventory.gearbenchoutput";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return stackResult.isEmpty() || stackResult.get(0).getItem() == Items.AIR;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return stackResult.get(0);
	}

	public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndRemove(this.stackResult, 0);
    }

    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.stackResult, 0);
    }

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.stackResult.set(0, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
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
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		this.stackResult.clear();
	}

}
