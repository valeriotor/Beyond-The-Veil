package com.valeriotor.beyondtheveil.blocks.flora;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PlantSaplingShrub extends BlockPlant{

	private static final AxisAlignedBB BBOX = new AxisAlignedBB(0.0625*5, 0, 0.0625*5, 0.0625*11, 0.0625*6, 0.0625*11);
	
	public PlantSaplingShrub(String name) {
		super(Material.PLANTS, name);
		this.setSoundType(SoundType.PLANT);
		this.setHardness(0);
	}
	
	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		boolean flag = false;
		for(EnumFacing facing : EnumFacing.HORIZONTALS) {
			if(worldIn.getBlockState(pos.offset(facing)).getBlock() instanceof PlantArborealGenerator) {
				flag = true;
				break;
			}
		}
		if(!flag) worldIn.setBlockToAir(pos);
	}
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return 3;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BBOX;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(fromPos.equals(pos.down())) {
			if(worldIn.getBlockState(fromPos).getBlock() != Blocks.GRASS && worldIn.getBlockState(fromPos).getBlock() != Blocks.DIRT) {
				worldIn.setBlockToAir(pos);
			}
		}
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

}
