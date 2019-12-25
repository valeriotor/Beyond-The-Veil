package com.valeriotor.BTV.blocks;

import java.util.List;

import com.valeriotor.BTV.tileEntities.TileMemorySieve;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMemorySieve extends ModBlock implements ITileEntityProvider{

	private static final double a = 0.0625;
	private static final AxisAlignedBB BBOX = new AxisAlignedBB(a*7, 0, a*7, a*9, a*12, a*9);
	private static final AxisAlignedBB BBOX2 = new AxisAlignedBB(a*2, a*13, a*2, a*14, a*14, a*14);
	
	public BlockMemorySieve(Material materialIn, String name) {
		super(materialIn, name);
	}
	
	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(hand == EnumHand.MAIN_HAND) {
			if(w.isRemote) return true;
			TileEntity te = w.getTileEntity(pos);
			if(te instanceof TileMemorySieve) {
				TileMemorySieve tm = (TileMemorySieve)te;
				ItemStack returnedStack = tm.getItem(p.getHeldItemMainhand());
				if(returnedStack.getItem() != Items.AIR) {
					p.addItemStackToInventory(returnedStack);
					return true;
				} else {
					tm.addItem(p.getHeldItemMainhand());
					return true;
				}
			}
		}
		return super.onBlockActivated(w, pos, state, p, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMemorySieve();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, BBOX);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, BBOX2);
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
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return (layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.TRANSLUCENT);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BBOX2;
	}

}
