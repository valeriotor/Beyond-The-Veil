package com.valeriotor.beyondtheveil.tileEntities;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileArborealGeneratorBottom extends TileEntity implements ITickable{
	
	private int count = 0;
	private int sideCount[] = {0,0,0,0};
	
	public TileArborealGeneratorBottom() {
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("count", this.count);
		compound.setInteger("count0", this.sideCount[0]);
		compound.setInteger("count1", this.sideCount[1]);
		compound.setInteger("count2", this.sideCount[2]);
		compound.setInteger("count3", this.sideCount[3]);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.count = compound.getInteger("count");
		this.sideCount[0] = compound.getInteger("count0");
		this.sideCount[1] = compound.getInteger("count1");
		this.sideCount[2] = compound.getInteger("count2");
		this.sideCount[3] = compound.getInteger("count3");
		super.readFromNBT(compound);
	}
	
	@Override
	public void update() {
		if(!this.world.isRemote) {
			count++;
			if(count >= 50) {
				count = 0;
				for(int i = 0; i < EnumFacing.HORIZONTALS.length; i++) {
					Block side = this.world.getBlockState(this.pos.offset(EnumFacing.HORIZONTALS[i])).getBlock();
					Block down = this.world.getBlockState(this.pos.offset(EnumFacing.HORIZONTALS[i]).down()).getBlock();
					if((side == Blocks.AIR || side == BlockRegistry.PlantSaplingShrub) &&
						(down == Blocks.GRASS || down == Blocks.DIRT)) {
						sideCount[i]++;
					}else sideCount[i] = 0;
					
					if(sideCount[i] > 490 || this.world.rand.nextInt(500) < sideCount[i]) {
						sideCount[i] = 0;
						this.progress(pos.offset(EnumFacing.HORIZONTALS[i]));
					}
				}
			}
		}
	}
	
	public IBlockState getRandomSapling() {
		int i = this.world.rand.nextInt(48);
		return Blocks.SAPLING.getDefaultState().withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.values()[i/8]);
	}
	
	public void progress(BlockPos pos) {
		if(this.world.getBlockState(pos).getBlock() == Blocks.AIR) {
			this.world.setBlockState(pos, BlockRegistry.PlantSaplingShrub.getDefaultState());
		}else {
			this.world.setBlockState(pos, this.getRandomSapling());
		}
	}
	
	
	
}
