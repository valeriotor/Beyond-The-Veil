package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.lib.BlockNames;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockWornBricks extends ModBlock{

	public BlockWornBricks() {
		super(Material.ROCK, BlockNames.WORNBRICKS);
		this.setHardness(5.0F);
		this.setSoundType(SoundType.STONE);
		// TODO Auto-generated constructor stub
	}

}
