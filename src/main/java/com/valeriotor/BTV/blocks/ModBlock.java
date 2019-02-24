package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlock extends Block{

	
	public ModBlock(Material materialIn, String name) {
		super(materialIn);
		this.setRegistryName(References.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(References.BTV_TAB);
	}

}