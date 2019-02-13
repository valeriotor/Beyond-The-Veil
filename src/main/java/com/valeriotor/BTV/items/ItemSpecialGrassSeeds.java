package com.valeriotor.BTV.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSpecialGrassSeeds extends ItemSeeds{

	private Block grass;
	
	public ItemSpecialGrassSeeds(Block crops, Block grass) {
		super(crops, Blocks.FARMLAND);
		this.grass = grass;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if((block == Blocks.DIRT || block == Blocks.GRASS) && block != this.grass) {
			worldIn.setBlockState(pos, this.grass.getDefaultState());
			ItemStack itemstack = player.getHeldItem(hand);
			itemstack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

}
