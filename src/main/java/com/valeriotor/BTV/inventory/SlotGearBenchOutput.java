package com.valeriotor.BTV.inventory;

import com.valeriotor.BTV.events.ResearchEvents;
import com.valeriotor.BTV.tileEntities.TileGearBench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotGearBenchOutput extends Slot{
	
	private TileGearBench matrix;
	
	public SlotGearBenchOutput(IInventory inventoryIn, TileGearBench matrix, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.matrix = matrix;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
	@Override
	public ItemStack onTake(EntityPlayer p, ItemStack stack) {
		for(int i = 0; i < 16; i++) {
			this.matrix.decrStackSize(i, 1);
		}
		if(!p.world.isRemote) {
			ResearchEvents.gearBenchCraftEvent(p, stack);
		}
		return super.onTake(p, stack);
	}
}
