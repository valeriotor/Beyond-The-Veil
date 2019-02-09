package com.valeriotor.BTV.blocks;

import javax.imageio.spi.ServiceRegistry;

import com.valeriotor.BTV.lib.BlockNames;
import com.valeriotor.BTV.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class BlockDarkSand extends BlockFalling{

	public BlockDarkSand() {
		super(Material.SAND);
		this.setResistance(40.0F);
		this.setHardness(2.0F);
		setRegistryName(BlockNames.DARKSAND);
		setUnlocalizedName(BlockNames.DARKSAND);
		this.setCreativeTab(References.BTV_TAB);
		this.setSoundType(SoundType.SAND);
	}
	
	
	
	
	
	
}
