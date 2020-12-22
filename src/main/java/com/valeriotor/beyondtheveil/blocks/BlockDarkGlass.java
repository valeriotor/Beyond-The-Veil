package com.valeriotor.beyondtheveil.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

public class BlockDarkGlass extends ModBlock{

	public BlockDarkGlass(String name) {
		super(Material.GLASS, name);
		setBlockUnbreakable();
        setResistance(6000001.0F);
		setSoundType(SoundType.GLASS);
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
