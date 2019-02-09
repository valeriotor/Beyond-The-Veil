package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.lib.BlockNames;
import com.valeriotor.BTV.lib.References;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class DampWoodFence extends BlockFence{

	public DampWoodFence() {
		super(Material.WOOD, Material.WOOD.getMaterialMapColor());
		this.setRegistryName(References.MODID, BlockNames.DAMPWOODFENCE);
		this.setUnlocalizedName(BlockNames.DAMPWOODFENCE);
		this.setCreativeTab(References.BTV_TAB);
		this.useNeighborBrightness = true;
		this.setHardness(4);
	}

}
