package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlock extends Block{

	
	public ModBlock(Material materialIn, String name) {
		super(materialIn);
		this.setRegistryName(References.MODID, name);
		this.setUnlocalizedName(References.MODID + ":" + name);
		this.setCreativeTab(References.BTV_TAB);
		this.setHardness(4.0F);
	}

}
