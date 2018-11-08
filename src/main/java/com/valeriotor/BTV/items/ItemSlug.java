package com.valeriotor.BTV.items;

import com.valeriotor.BTV.lib.References;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemSlug extends Item{
	
	public ItemSlug() {
		this.setRegistryName(References.MODID + ":slug");
		this.setUnlocalizedName("slug");
		this.setCreativeTab(CreativeTabs.MISC);
	}
	
	

}
