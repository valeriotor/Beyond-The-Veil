package com.valeriotor.beyondtheveil.blocks;

import java.util.Random;

import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamItem;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockCurtain extends ModBlockFacing{
	private static final AxisAlignedBB BBOX1 = new AxisAlignedBB(0.0625*7, 0, 0, 0.0625*9, 1, 1);
	private static final AxisAlignedBB BBOX2 = new AxisAlignedBB(0, 0, 0.0625*7, 1, 1, 0.0625*9);
	public BlockCurtain(String name) {
		super(Material.CLOTH, name);
		this.setSoundType(SoundType.CLOTH);
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if(!worldIn.isRemote && entityIn instanceof EntityDreamItem) {
			EntityDreamItem e = (EntityDreamItem)entityIn;
			ItemStack stack = e.getItem();
			TileEntity te = worldIn.getTileEntity(pos.up());
			if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
				IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				for(int i = 0; i < cap.getSlots(); i++) {
					if(stack.getItem() == cap.getStackInSlot(i).getItem()) {
						e.removeEntity();
						break;
					}
				}
			}
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(FACING).getAxis() == Axis.X ? BBOX1 : BBOX2;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

}
