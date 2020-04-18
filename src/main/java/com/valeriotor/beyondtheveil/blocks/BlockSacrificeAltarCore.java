package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.multiblock.MultiblockRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockSacrificeAltarCore extends ModBlock{

	public BlockSacrificeAltarCore(Material materialIn, String name) {
		super(materialIn, name);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote && hand == EnumHand.MAIN_HAND && playerIn.getHeldItemMainhand().isEmpty()) {
			boolean built = MultiblockRegistry.multiblocks.get(MultiblockRegistry.SACRIFICE_ALTAR).checksOutBottomCenter(worldIn, pos);
			if(built)
				playerIn.sendMessage(new TextComponentTranslation("multiblock.sacrifice_altar.checksout"));
			else
				playerIn.sendMessage(new TextComponentTranslation("multiblock.sacrifice_altar.noaltar"));
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

}
