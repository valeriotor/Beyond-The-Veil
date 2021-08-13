package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.lib.BlockNames;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class DampWoodFence extends BlockFence{

	public DampWoodFence() {
		super(Material.WOOD, Material.WOOD.getMaterialMapColor());
		this.setRegistryName(References.MODID, BlockNames.DAMPWOODFENCE);
		this.setUnlocalizedName(References.MODID + ":" + BlockNames.DAMPWOODFENCE);
		this.setCreativeTab(References.BTV_TAB);
		this.useNeighborBrightness = true;
		this.setHardness(4);
	}

}
