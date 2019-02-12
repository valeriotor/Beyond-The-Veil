package com.valeriotor.BTV.blocks.flora;

import com.valeriotor.BTV.items.ItemRegistry;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PlantGhostWeed extends BlockCrops{
	
	public static final PropertyInteger CROP_AGE = PropertyInteger.create("age", 0, 4);
	private static final AxisAlignedBB[] CROP_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.35D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.40D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D)};

	public PlantGhostWeed(String name) {
		super();
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
	}
	
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	{
		IBlockState soil = worldIn.getBlockState(pos.down());
		return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && (soil.getBlock() == Blocks.DIRT || soil.getBlock() == Blocks.GRASS);
	}
	
	
	@Override
	protected Item getSeed() {
		return ItemRegistry.ghost_weed_seeds;
	}
	
	@Override
	protected PropertyInteger getAgeProperty() {
		return CROP_AGE;
	}
	
	@Override
	public int getMaxAge() {
		return 4;
	}
	
	@Override
	protected Item getCrop() {
		return Items.AIR;
	}
	
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {CROP_AGE});
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CROP_AABB[((Integer)state.getValue(this.getAgeProperty())).intValue()];
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		drops.add(new ItemStack(this.getSeed(), 1, 0));
		super.getDrops(drops, world, pos, state, fortune);
	}
	
}
