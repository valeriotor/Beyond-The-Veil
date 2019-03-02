package com.valeriotor.BTV.blocks.flora;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.valeriotor.BTV.blocks.BlockSleepChamber;
import com.valeriotor.BTV.blocks.EnumHalf;
import com.valeriotor.BTV.tileEntities.TileArborealGeneratorBottom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PlantArborealGenerator extends BlockTallPlant implements ITileEntityProvider, IMutationCatalyst{
	
	private static final AxisAlignedBB TRUNK_AABB = new AxisAlignedBB(0.0625*4, 0, 0.0625*5, 0.0625*10, 1.0, 0.0625*11);
	private static final AxisAlignedBB TRUNKTOP_AABB = new AxisAlignedBB(0.0625*4, 0, 0.0625*5, 0.0625*10, 0.0625*7, 0.0625*11);
	
	public PlantArborealGenerator(String name) {
		super(Material.PLANTS, name);
		this.setSoundType(SoundType.WOOD);
		this.spreadChance = 5;
		this.spreadMinMutation = 1000;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(meta == 0) return new TileArborealGeneratorBottom();
		return null;
	}

	@Override
	public int mutationIncrease() {
		return 12;
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
		if(state.getValue(EnumHalf.HALF) == EnumHalf.BOTTOM) return TRUNK_AABB;
		return TRUNKTOP_AABB;
	}
	
	
	

}
