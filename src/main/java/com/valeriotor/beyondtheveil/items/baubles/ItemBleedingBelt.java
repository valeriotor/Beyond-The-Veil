package com.valeriotor.beyondtheveil.items.baubles;

import com.valeriotor.beyondtheveil.items.ModItem;
import com.valeriotor.beyondtheveil.lib.References;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBleedingBelt extends ModItem implements IBauble{

	public ItemBleedingBelt(String name) {
		super(name);
		this.setMaxStackSize(1);
	}
	
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.BELT;
	}

}
