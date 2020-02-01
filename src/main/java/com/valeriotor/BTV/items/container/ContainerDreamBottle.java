package com.valeriotor.BTV.items.container;

import com.valeriotor.BTV.items.ItemRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDreamBottle extends Container{
	
	private final int HOTBAR_SLOT_COUNT = 9;
	private final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

	private final int VANILLA_FIRST_SLOT_INDEX = 0;
	private final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	private final int TE_INVENTORY_SLOT_COUNT = 4;
	
	public ContainerDreamBottle(InventoryPlayer invPlayer, InventoryDreamBottle dreamBottle) {
		final int SLOT_X_SPACING = 18;
	    final int SLOT_Y_SPACING = 18;
		final int HOTBAR_XPOS = 8;
		final int HOTBAR_YPOS = 154;
		
		for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
			int slotNumber = x;
			addSlotToContainer(new Slot(invPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
		}

		final int PLAYER_INVENTORY_XPOS = 8;
		final int PLAYER_INVENTORY_YPOS = 96;
		
		for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
			for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
				int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
				int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
				int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
				addSlotToContainer(new Slot(invPlayer, slotNumber,  xpos, ypos));
			}
		}

		if (TE_INVENTORY_SLOT_COUNT != dreamBottle.getSizeInventory()) {
			System.err.println("Mismatched slot count in ContainerDreamBottle(" + TE_INVENTORY_SLOT_COUNT
												  + ") and DreamBottleInventory(" + dreamBottle.getSizeInventory()+")");
		}
		final int BOTTLE_INVENTORY_XPOS = 72;
		final int BOTTLE_INVENTORY_YPOS = 33;
		
		for (int i = 0; i < TE_INVENTORY_SLOT_COUNT; i++) {
			int slotNumber = i;
			addSlotToContainer(new CrystalSlot(dreamBottle, slotNumber, BOTTLE_INVENTORY_XPOS + SLOT_X_SPACING * (i%2), BOTTLE_INVENTORY_YPOS + 18 * (i/2)));
		}
	}
	
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getHeldItemMainhand().getItem() == ItemRegistry.dream_bottle;
	}
	
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
	
	
	private static class CrystalSlot extends Slot{

		public CrystalSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return stack.getItem() == ItemRegistry.memory_phial;
		}
		
	}
	
}
