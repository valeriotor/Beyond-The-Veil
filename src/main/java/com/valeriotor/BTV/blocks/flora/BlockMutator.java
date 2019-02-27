package com.valeriotor.BTV.blocks.flora;

import com.valeriotor.BTV.blocks.ModBlock;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.tileEntities.TileMutator;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMutator extends ModBlock implements ITileEntityProvider{

	public BlockMutator(String name) {
		super(Material.IRON, name);
		this.setResistance(60.0F);
		this.setHardness(4.0F);
		
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMutator();
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(!worldIn.isRemote && (fromPos.equals(pos.up()) || fromPos.equals(pos.down()))) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof TileMutator) ((TileMutator)te).blockNeighbourUpdate();
		}
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if(!worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof TileMutator) ((TileMutator)te).blockNeighbourUpdate();
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

}
