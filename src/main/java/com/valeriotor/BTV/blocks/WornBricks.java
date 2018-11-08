package com.valeriotor.BTV.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class WornBricks extends Block{

	public WornBricks() {
		super(Material.ROCK);
		this.setHardness(5.0F);
		setRegistryName("worn_bricks");
		setUnlocalizedName("worn_bricks");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setSoundType(SoundType.STONE);
		// TODO Auto-generated constructor stub
	}

}
