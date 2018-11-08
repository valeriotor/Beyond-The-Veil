package com.valeriotor.BTV.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class DampStone extends Block{

	public DampStone() {
		super(Material.ROCK);
		this.setHardness(5.0F);
		setRegistryName("damp_stone");
		setUnlocalizedName("damp_stone");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setSoundType(SoundType.STONE);
		// TODO Auto-generated constructor stub
	}

}
