package com.valeriotor.BTV.inventory;

import java.util.function.Consumer;

import com.valeriotor.BTV.crafting.GearBenchRecipe;
import com.valeriotor.BTV.crafting.GearBenchRecipeRegistry;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.tileEntities.TileGearBench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerGearBench extends Container{
	private final int HOTBAR_SLOT_COUNT = 9;
	private final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

	private final int VANILLA_FIRST_SLOT_INDEX = 0;
	private final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	private final int TE_INVENTORY_SLOT_COUNT = 4;
	
	private final TileGearBench bench;
	
	public ContainerGearBench(InventoryPlayer invPlayer, TileGearBench gb) {
		final int SLOT_X_SPACING = 18;
	    final int SLOT_Y_SPACING = 18;
		final int HOTBAR_XPOS = 8;
		final int HOTBAR_YPOS = 147;
		this.bench = gb;
		this.addSlotToContainer(new SlotGearBenchOutput(gb.output, gb, 0, 138, 24));
		
		for(int i = 0; i < 16; i++) {
			this.addSlotToContainer(new SlotItemHandler(gb.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), i, 8+18*(i%4), -2+18*(i/4)));
		}
		gb.changeOutput();
		for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
			int slotNumber = x;
			addSlotToContainer(new Slot(invPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
		}

		final int PLAYER_INVENTORY_XPOS = 8;
		final int PLAYER_INVENTORY_YPOS = 89;
		
		for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
			for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
				int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
				int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
				int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
				addSlotToContainer(new Slot(invPlayer, slotNumber,  xpos, ypos));
			}
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return ResearchUtil.isResearchKnown(playerIn, "SLEEPCHAMBER");
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		IItemHandler h = this.bench.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if(index == 0) {
			ItemStack stack = this.bench.output.getStackInSlot(0);
			int min = 64;
			for(int i = 0; i < 16; i++) {
				int a = h.getStackInSlot(i).getCount();
				if(a < min)
					min = a;
			}
			for(int i = 0; i < 16; i++) {
				h.extractItem(i, min, false);
			}
			stack.setCount(min);
			ItemHandlerHelper.giveItemToPlayer(playerIn, stack);
		} /*else if(index >= 1 && index <= 16) {
			ItemStack stack = h.getStackInSlot(index-1);
			for(int i = 17; i < 53; i++) {
				ItemStack iStack = this.inventorySlots.get(i).getStack();
				
			}		
		}*/
		return ItemStack.EMPTY;
	}
	
}
