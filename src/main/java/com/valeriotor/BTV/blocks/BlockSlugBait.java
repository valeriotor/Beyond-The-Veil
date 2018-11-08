package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.tileEntities.TileSlugBait;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;

public class BlockSlugBait extends Block implements ITileEntityProvider{

	public BlockSlugBait() {
		super(Material.WOOD);
		this.setResistance(2000.0F);
		this.setHardness(4.0F);
		setRegistryName(References.MODID + ":slug_bait");
		setUnlocalizedName("slug_bait");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
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
