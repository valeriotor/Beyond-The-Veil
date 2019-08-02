package com.valeriotor.BTV.items.baubles;

import com.valeriotor.BTV.lib.References;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBleedingBelt extends Item implements IBauble{

	public ItemBleedingBelt(String name) {
		this.setMaxStackSize(1);
		this.setCreativeTab(References.BTV_TAB);
		setRegistryName(References.MODID, name);
		setUnlocalizedName(name);
	}
	
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.BELT;
	}

}
