package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BricksBlue extends Block{
	public BricksBlue() {
		super(Material.ROCK);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setRegistryName(References.MODID + ":bricks_blue");
		this.setUnlocalizedName("bricks_blue");
	}
}
