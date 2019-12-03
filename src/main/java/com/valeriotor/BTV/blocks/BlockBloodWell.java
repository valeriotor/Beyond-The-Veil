package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.items.ItemBloodSigil;
import com.valeriotor.BTV.tileEntities.TileBloodWell;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBloodWell extends ModBlock implements ITileEntityProvider{

	public BlockBloodWell(Material materialIn, String name) {
		super(materialIn, name);
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
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
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileBloodWell();
	}
	
	public boolean checkStructure(BlockPos pos, World w) {
		boolean well = true;
		for(int x = -2; x <= 2 && well; x++ ) {
			for(int z = -2; z <= 2 && well; z++) {
				if(x == 2 || x == -2 || z == 2 || z == -2) {
					if(w.getBlockState(pos.add(x, 0, z)).getBlock() != BlockRegistry.BlockBloodBrick)
						well = false;
					if(well && (x == z || x == - z) && (z == 2 || z == -2)) {
						if(w.getBlockState(pos.add(x, 1, z)).getBlock() != BlockRegistry.BlockBloodBrick ||
						   w.getBlockState(pos.add(x, 2, z)).getBlock() != BlockRegistry.BlockBloodBrick) {
							well = false;
						}
					}
				}
				if(w.getBlockState(pos.add(x, -1, z)).getBlock() != BlockRegistry.BlockBloodBrick)
					well = false;
			}
		}
		return well;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote) return true;
		if(hand == EnumHand.OFF_HAND) return false;
		if(playerIn.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBloodSigil) return false; 
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof TileBloodWell) {
			((TileBloodWell)te).sendInfo(playerIn);
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	

}
