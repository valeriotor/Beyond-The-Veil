package com.valeriotor.BTV.inventory;

import com.valeriotor.BTV.events.ResearchEvents;
import com.valeriotor.BTV.tileEntities.TileGearBench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotGearBenchOutput extends SlotItemHandler{
	
	private TileGearBench matrix;
	
	public SlotGearBenchOutput(IItemHandler inventoryIn, TileGearBench matrix, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.matrix = matrix;
	}
	
	@Override
	public ItemStack onTake(EntityPlayer p, ItemStack stack) {
		for(int i = 0; i < 16; i++) {
			this.matrix.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP).extractItem(i, 1, false);
		}
		if(!p.world.isRemote) {
			ResearchEvents.gearBenchCraftEvent(p, stack);
		}
		matrix.markDirty();
		return super.onTake(p, stack);
	}
}
