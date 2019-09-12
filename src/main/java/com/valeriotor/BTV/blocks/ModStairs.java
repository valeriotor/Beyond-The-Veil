package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.lib.References;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class ModStairs extends BlockStairs{

	protected ModStairs(IBlockState modelState, String name) {
		super(modelState);
		this.setRegistryName(References.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(References.BTV_TAB);
	}
	
	
	
}
