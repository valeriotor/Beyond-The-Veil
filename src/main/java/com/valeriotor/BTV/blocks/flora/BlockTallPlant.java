package com.valeriotor.BTV.blocks.flora;

import java.util.Random;

import com.valeriotor.BTV.blocks.EnumHalf;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockTallPlant extends BlockPlant{

	public BlockTallPlant(Material materialIn, String name) {
		super(materialIn, name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(EnumHalf.HALF, EnumHalf.BOTTOM));
		this.setHardness(3);
		this.setSoundType(SoundType.PLANT);
	}
	
	@Override
	public boolean spread(World w, BlockPos pos, int mutation, float multiplier) {
		if(w.getBlockState(pos).getValue(EnumHalf.HALF) == EnumHalf.BOTTOM)
			return super.spread(w, pos, mutation, multiplier);
		return false;
	}
	
	@Override
	public boolean canPlaceBlockAt(World w, BlockPos pos) {
		Block b = w.getBlockState(pos.down()).getBlock();
		if((b == Blocks.DIRT || b == Blocks.GRASS) && w.getBlockState(pos.up()).getBlock() == Blocks.AIR && pos.getY() < w.getHeight() - 1) return true;
		return false;
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
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(EnumHalf.HALF, EnumHalf.TOP));
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {EnumHalf.HALF});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState().withProperty(EnumHalf.HALF, meta == 0 ? EnumHalf.BOTTOM : EnumHalf.TOP);
		
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(EnumHalf.HALF) == EnumHalf.BOTTOM ? 0 : 1;
		
		return meta;
	}

}
