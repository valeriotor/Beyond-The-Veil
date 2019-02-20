package com.valeriotor.BTV.blocks.flora;

import java.util.Random;

import com.valeriotor.BTV.blocks.BlockSleepChamber;
import com.valeriotor.BTV.blocks.EnumHalf;
import com.valeriotor.BTV.tileEntities.TileArborealGeneratorBottom;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlantArborealGenerator extends BlockPlant implements ITileEntityProvider{
	
	public static final PropertyEnum<EnumHalf> HALF = PropertyEnum.<EnumHalf>create("half", EnumHalf.class);
	
	public PlantArborealGenerator(String name) {
		super(Material.PLANTS, name);
		this.setSoundType(SoundType.PLANT);
		this.spreadChance = 5;
		this.spreadMinMutation = 1000;
		this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, EnumHalf.BOTTOM));
	}
	
	@Override
	public boolean canPlaceBlockAt(World w, BlockPos pos) {
		Block b = w.getBlockState(pos.down()).getBlock();
		if((b == Blocks.DIRT || b == Blocks.GRASS) && w.getBlockState(pos.up()).getBlock() == Blocks.AIR && pos.getY() < w.getHeight() - 1) return true;
		return false;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(state.getValue(HALF) == EnumHalf.BOTTOM) {
			if(worldIn.getBlockState(pos.up()).getBlock() != this) {
				worldIn.setBlockToAir(pos);
				this.dropBlockAsItem(worldIn, pos, state, 0);
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
		worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, EnumHalf.TOP));
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(state.getValue(HALF) == EnumHalf.BOTTOM) return super.getItemDropped(state, rand, fortune);
		else return Items.AIR;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {HALF});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState().withProperty(HALF, meta == 0 ? EnumHalf.BOTTOM : EnumHalf.TOP);
		
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(HALF) == EnumHalf.BOTTOM ? 0 : 1;
		
		return meta;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(meta == 0) return new TileArborealGeneratorBottom();
		return null;
	}
	
	
	

}
