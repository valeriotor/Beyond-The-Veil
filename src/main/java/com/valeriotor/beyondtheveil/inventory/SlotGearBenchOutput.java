package com.valeriotor.beyondtheveil.inventory;

import com.valeriotor.beyondtheveil.crafting.GearBenchRecipe;
import com.valeriotor.beyondtheveil.crafting.GearBenchRecipeRegistry;
import com.valeriotor.beyondtheveil.events.ResearchEvents;
import com.valeriotor.beyondtheveil.tileEntities.TileGearBench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.UniversalBucket;
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
		IItemHandler cap = this.matrix.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		for(int i = 0; i < 16; i++) {
			Item a = cap.extractItem(i, 1, false).getItem();
			if((a instanceof ItemBucket || a instanceof UniversalBucket) && a != Items.BUCKET) {
				cap.insertItem(i, new ItemStack(Items.BUCKET), false);
			}
		}
		if(!p.world.isRemote) {
			ResearchEvents.gearBenchCraftEvent(p, stack);
		}
		matrix.markDirty();
		return super.onTake(p, stack);
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer playerIn) {
		if(this.getStack() != null && !this.getStack().isEmpty()) {
			GearBenchRecipe gbr = GearBenchRecipeRegistry.recipesFromKeys.get(this.getStack().getItem().getRegistryName().toString());
			if(gbr != null)
				return gbr.isKnown(playerIn) && super.canTakeStack(playerIn);
		}
		return super.canTakeStack(playerIn);
	}
}
