package com.valeriotor.BTV.blocks.flora;

import javax.annotation.Nullable;

import com.valeriotor.BTV.blocks.ModBlock;
import com.valeriotor.BTV.blocks.flora.BlockRedstoneGrass.RedstoneLevels;
import com.valeriotor.BTV.items.ItemRegistry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGhostGrass extends ModBlock{

public static final AxisAlignedBB BBOX = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.9325, 1.0);
	
	public BlockGhostGrass(String name) {
		super(Material.GRASS, name);
		this.setSoundType(SoundType.PLANT);
		this.setHardness(0.6F);		
	}
	
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		drops.add(new ItemStack(Blocks.DIRT));
		drops.add(new ItemStack(ItemRegistry.ghost_weed_seeds));
	}
	
	@Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
	
	@Override
		public boolean isFullCube(IBlockState state) {
			return false;
		}
	
	
}
