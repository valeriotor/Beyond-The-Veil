package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.lib.BlockNames;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tileEntities.TileSlugBait;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;

public class BlockSlugBait extends ModBlock implements ITileEntityProvider{

	public BlockSlugBait() {
		super(Material.WOOD, BlockNames.SLUGBAIT);
		this.setResistance(2000.0F);
		this.setHardness(4.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileSlugBait();
	}
	
	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return  BlockRenderLayer.CUTOUT;
	}

}
