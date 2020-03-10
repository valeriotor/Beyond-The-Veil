package com.valeriotor.beyondtheveil.lib;

import com.valeriotor.beyondtheveil.items.ItemRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabBTV extends CreativeTabs{

	public CreativeTabBTV(int index, String name) {
		super(index, name);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ItemRegistry.tablet);
	}
}
