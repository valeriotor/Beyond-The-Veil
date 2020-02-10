package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.gui.container.GuiContainerHandler;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.tileEntities.TileGearBench;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockGearBench extends ModBlock implements ITileEntityProvider{

	public BlockGearBench(Material materialIn, String name) {
		super(materialIn, name);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileGearBench();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(ResearchUtil.isResearchKnown(playerIn, "SLEEPCHAMBER"))
			playerIn.openGui(BeyondTheVeil.instance, GuiContainerHandler.GEAR_BENCH, worldIn, pos.getX(), pos.getY(), pos.getZ());
		else
			playerIn.sendMessage(new TextComponentTranslation("interact.gearbench.dunno"));
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

}
