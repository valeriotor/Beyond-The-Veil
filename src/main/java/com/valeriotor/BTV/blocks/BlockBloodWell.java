package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.tileEntities.TileBloodWell;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
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
	

}
