package com.valeriotor.BTV.blocks.flora;

import com.valeriotor.BTV.blocks.ModBlock;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPlant extends ModBlock{
	
	public int spreadChance = 0;
	public int spreadMinMutation = 1000;
	
	public BlockPlant(Material materialIn, String name) {
		super(materialIn, name);
	}
	
	public void spread(World w, BlockPos pos, int mutation) {
		if(mutation >= this.spreadMinMutation) {
			if(this.spreadChance >= w.rand.nextInt(1000)) {
				for(EnumFacing facing : EnumFacing.HORIZONTALS) {
					BlockPos newXZ = pos.offset(facing);
					for(int y = pos.getY() - 3; y < pos.getY() + 3; y++) {
						BlockPos newPos = new BlockPos(newXZ.getX(), y, newXZ.getZ());
						IBlockState state = w.getBlockState(newPos);
						if(state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.MYCELIUM) {
							if(w.getBlockState(newPos.up()).getBlock() ==  Blocks.AIR) {
								w.setBlockState(newPos.up(), this.getDefaultState());
								break;
							}
						}
					}
				}
			}
		}
	}
	
}
