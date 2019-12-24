package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.tileEntities.TileMemorySieve;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMemorySieve extends ModBlock implements ITileEntityProvider{

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
				ItemStack returnedStack = tm.getItem();
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
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

}
