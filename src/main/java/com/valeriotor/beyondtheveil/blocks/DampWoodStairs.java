package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.lib.BlockNames;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DampWoodStairs extends BlockStairs{

	public DampWoodStairs(IBlockState modelState) {
		super(modelState);
		this.setRegistryName(References.MODID, BlockNames.DAMPWOODSTAIRS);
		this.setUnlocalizedName(References.MODID + ":" + BlockNames.DAMPWOODSTAIRS);
		this.setCreativeTab(References.BTV_TAB);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(playerIn.getHeldItem(hand).getItem() == Item.getItemFromBlock(BlockRegistry.DampCanopy)) {
			playerIn.getHeldItem(hand).shrink(1);
			worldIn.setBlockState(pos, BlockRegistry.DampCanopyWood.getDefaultState().withProperty(FACING, state.getValue(FACING)));
			worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1F, 1F, false);
			return true;
		}
		return false;
	}

}
