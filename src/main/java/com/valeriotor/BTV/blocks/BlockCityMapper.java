package com.valeriotor.BTV.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.tileEntities.TileCityMapper;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCityMapper extends ModBlockFacing implements ITileEntityProvider{
	
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
		if(hand == EnumHand.OFF_HAND) return false;
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof TileCityMapper) {
			TileCityMapper tc = (TileCityMapper) te;
			if(!worldIn.isRemote && tc.timer >= 102) tc.sendSmallUpdates();
			if(tc.timer < 102) {
				if(!worldIn.isRemote)
					playerIn.sendMessage(new TextComponentTranslation("interact.citymapper.loading"));
				return true;
			} else if(tc.viewingPlayer != null && !tc.viewingPlayer.equals(playerIn.getName())){
				if(!worldIn.isRemote) {
					IPlayerData data = playerIn.getCapability(PlayerDataProvider.PLAYERDATA, null);
					data.incrementOrSetInteger(PlayerDataLib.MAPPER_PLAYER_INTERACT, 1, -3, true);
					playerIn.sendMessage(new TextComponentTranslation(String.format("interact.citymapper.player%d", Math.max(0, Math.min(5, data.getOrSetInteger(PlayerDataLib.MAPPER_PLAYER_INTERACT, -3, true))))));
				}
				return true;
			}else {
				if(!worldIn.isRemote) playerIn.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(PlayerDataLib.MAPPER_PLAYER_INTERACT, -3, true);
				tc.viewingPlayer = playerIn.getName();
				playerIn.openGui(BeyondTheVeil.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
				return true;
			}
		}
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
	
	private static final AxisAlignedBB BBOX = new AxisAlignedBB(0.0625D * 5,0.0D,0.0625D * 5,0.0625D * 11,0.0625D * 13,0.0625D * 11);
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BBOX;
	}
}
