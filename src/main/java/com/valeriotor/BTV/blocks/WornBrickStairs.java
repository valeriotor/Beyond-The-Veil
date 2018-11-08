package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.lib.References;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public class WornBrickStairs extends BlockStairs{

	protected WornBrickStairs(IBlockState modelState) {
		super(modelState);
		this.setRegistryName(References.MODID + ":worn_brick_stairs");
		this.setUnlocalizedName("worn_brick_stairs");
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

}
