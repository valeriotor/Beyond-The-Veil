package com.valeriotor.beyondtheveil.blocks;

import javax.imageio.spi.ServiceRegistry;

import com.valeriotor.beyondtheveil.lib.BlockNames;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockDarkSand extends ModBlock{

	public BlockDarkSand(String name) {
		super(Material.SAND, name);
		this.setResistance(40.0F);
		this.setHardness(2.0F);
		this.setSoundType(SoundType.SAND);
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.BLUE_STAINED_HARDENED_CLAY;
	}
	
	
	
	
}
