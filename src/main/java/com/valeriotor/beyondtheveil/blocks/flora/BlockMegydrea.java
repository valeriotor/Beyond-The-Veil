package com.valeriotor.beyondtheveil.blocks.flora;

import com.valeriotor.beyondtheveil.blocks.EnumHalf;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMegydrea extends BlockTallPlant{

	public BlockMegydrea(String name) {
		super(Material.PLANTS, name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(EnumHalf.HALF, EnumHalf.TOP));
	}
	
	@Override
	public boolean spread(World w, BlockPos pos, int mutation, float multiplier) {
		return false;
	}
	
	@Override
	public boolean canPlaceBlockAt(World w, BlockPos pos) {
		Block b = w.getBlockState(pos.up()).getBlock();
		if(w.getBlockState(pos.up()).isSideSolid(w, pos.up(), EnumFacing.DOWN) && w.isAirBlock(pos.down())) return true;
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos.down(), this.getDefaultState().withProperty(EnumHalf.HALF, EnumHalf.BOTTOM));
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(EnumHalf.HALF, EnumHalf.TOP);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(state.getValue(EnumHalf.HALF) == EnumHalf.BOTTOM) {
			if(worldIn.getBlockState(pos.up()).getBlock() != this) {
				worldIn.setBlockToAir(pos);
			}
		}else {
			if(worldIn.getBlockState(pos.down()).getBlock() != this) {
				worldIn.setBlockToAir(pos);
			}
		}
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return (layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.TRANSLUCENT);
	}

}
