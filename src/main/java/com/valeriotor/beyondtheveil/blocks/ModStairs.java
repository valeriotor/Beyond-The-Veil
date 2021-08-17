package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class ModStairs extends BlockStairs{

	protected ModStairs(IBlockState modelState, String name) {
		super(modelState);
		this.setRegistryName(References.MODID, name);
		this.setUnlocalizedName(References.MODID + ":" + name);
		this.setCreativeTab(References.BTV_TAB);
	}
	
	
	
}
