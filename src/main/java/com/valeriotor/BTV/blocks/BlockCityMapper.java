package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.tileEntities.TileCityMapper;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCityMapper extends ModBlock implements ITileEntityProvider{

	public BlockCityMapper(String name) {
		super(Material.IRON, name);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCityMapper();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		playerIn.openGui(BeyondTheVeil.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

}
