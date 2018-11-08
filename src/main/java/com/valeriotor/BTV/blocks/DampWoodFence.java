package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.lib.References;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class DampWoodFence extends BlockFence{

	public DampWoodFence() {
		super(Material.WOOD, Material.WOOD.getMaterialMapColor());
		this.setRegistryName(References.MODID + ":damp_wood_fence");
		this.setUnlocalizedName("damp_wood_fence");
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.useNeighborBrightness = true;
		this.setHardness(4);
	}

}
