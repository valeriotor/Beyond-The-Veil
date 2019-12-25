package com.valeriotor.BTV.items.container;

import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import thaumcraft.api.items.ItemsTC;

public class InventoryDreamBottle extends InventoryBasic{
	
	private ItemStack dreamBottle;

	public InventoryDreamBottle(String title, boolean customName, int slotCount, ItemStack dreamBottle) {
		super(title, customName, slotCount);
		this.dreamBottle = dreamBottle;
		this.readFromNBT(ItemHelper.checkTagCompound(dreamBottle));
		IInventoryChangedListener listener = new IInventoryChangedListener() {
			
			@Override
			public void onInventoryChanged(IInventory invBasic) {
				if(invBasic instanceof InventoryDreamBottle) {
					((InventoryDreamBottle)invBasic).writeToNBT();
				}
				
			}
		};
		this.addInventoryChangeListener(listener);
	}

	private ItemStack[] stacks = new ItemStack[4];
	
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("gui.dreambottle.name");
	}
	
	public void writeToNBT() {
		NBTTagCompound nbt = ItemHelper.checkTagCompound(dreamBottle);
		for(int i = 0; i < this.getSizeInventory(); i++) {
			NBTTagCompound cp = new NBTTagCompound();
			ItemStack stack = this.getStackInSlot(i);
			if(stack == null) this.setInventorySlotContents(i, ItemStack.EMPTY);
			this.getStackInSlot(i).writeToNBT(cp);
			nbt.setTag(String.format("slot%d", i), cp);
		}
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		for(int i = 0; i < 4; i++) {
			NBTTagCompound tc = ItemHelper.checkTagCompound(nbt, String.format("slot%d", i));
			ItemStack stack = new ItemStack(tc);
			this.setInventorySlotContents(i, stack);
		}
	}
	
	public boolean isItemValidForSlot(int i, ItemStack itemstack)	
	{
	    return (!itemstack.isEmpty()) && ((itemstack.getItem() == ItemRegistry.memory_phial));
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public ItemStack addItem(ItemStack stack) {
		if(stack.getItem() != ItemRegistry.memory_phial) return stack;
		return super.addItem(stack);
	}

	

}
