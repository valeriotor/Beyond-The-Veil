package com.valeriotor.BTV.lib;

import com.valeriotor.BTV.items.ItemRegistry;

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
