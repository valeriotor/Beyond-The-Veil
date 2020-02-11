package com.valeriotor.BTV.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class InventoryGearBenchOutput implements IItemHandlerModifiable{

    private final NonNullList<ItemStack> stackResult = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	private ItemStack result = ItemStack.EMPTY;

	@Override
	public ItemStack getStackInSlot(int index) {
		return result;
	}

	@Override
	public int getSlots() {
		return 1;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		ItemStack stack = result;
		if(!simulate) result = ItemStack.EMPTY;
		return stack;
	}

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		result = stack;
	}

}
