package com.valeriotor.beyondtheveil.blocks.util;

import com.valeriotor.beyondtheveil.tileEntities.TileDreamFocus;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IDyableFocus {
	public default boolean setColor(ItemStack stack, World w, BlockPos pos) {
		TileEntity te = w.getTileEntity(pos);
		if(te instanceof TileDreamFocus) {
			if(stack.getItem() instanceof ItemDye)
				((TileDreamFocus)te).setDyeColor(EnumDyeColor.byDyeDamage(stack.getMetadata()));
			else if(stack.getItem() == Items.CLAY_BALL)
				((TileDreamFocus)te).toggleShowPath();
			else 
				return false;
			return true;
		}
		return false;
	}
}
