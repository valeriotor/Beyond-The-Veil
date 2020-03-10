package com.valeriotor.beyondtheveil.blocks.flora;

import com.valeriotor.beyondtheveil.tileEntities.TilePlantTerra;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlantBreaker extends BlockPlant implements ITileEntityProvider, IMutationCatalyst{

	public PlantBreaker(String name) {
		super(Material.PLANTS, name);
		
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		if(side != EnumFacing.DOWN) return false;
		Block b = worldIn.getBlockState(pos.up()).getBlock();
		if(b != Blocks.DIRT && b != Blocks.GRASS && b != Blocks.MYCELIUM) return false;
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePlantTerra();
	}

	@Override
	public int mutationIncrease() {
		return 8;
	}

}
