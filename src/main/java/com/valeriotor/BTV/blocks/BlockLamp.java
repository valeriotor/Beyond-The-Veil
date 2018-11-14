package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLamp extends Block{
	public BlockLamp() {
		super(Material.GLASS);
		this.setRegistryName(References.MODID + ":lamp");
		this.setUnlocalizedName("lamp");
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setLightLevel(4);
		this.setHardness(2);
		this.setResistance(10);
		this.setSoundType(SoundType.GLASS);
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		if(side == EnumFacing.DOWN) return worldIn.getBlockState(pos.add(0, 1, 0)).getBlockFaceShape(worldIn, pos, side) == BlockFaceShape.SOLID;
		else return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	
}
