package com.valeriotor.BTV.blocks.flora;

import com.valeriotor.BTV.blocks.ModBlock;
import com.valeriotor.BTV.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMutator extends ModBlock implements ITileEntityProvider{

	public BlockMutator() {
		super(Material.IRON, "mutator");
		this.setResistance(60.0F);
		this.setHardness(4.0F);
		
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}

}
