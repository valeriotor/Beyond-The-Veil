package com.valeriotor.BTV.blocks;

import javax.imageio.spi.ServiceRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class DarkSand extends BlockFalling{

	public DarkSand() {
		super(Material.SAND);
		this.setResistance(40.0F);
		this.setHardness(2.0F);
		setRegistryName("dark_sand");
		setUnlocalizedName("dark_sand");
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setSoundType(SoundType.SAND);
	}
	
	
	
	
	
	
}
