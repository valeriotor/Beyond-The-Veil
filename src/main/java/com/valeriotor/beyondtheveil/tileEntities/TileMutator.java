package com.valeriotor.beyondtheveil.tileEntities;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.blocks.EnumHalf;
import com.valeriotor.beyondtheveil.blocks.flora.BlockPlant;
import com.valeriotor.beyondtheveil.blocks.flora.IMutationCatalyst;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileMutator extends TileEntity implements ITickable{
	
	private int mutation = 0;
	private int counter = 0;
	// Increased by PlantOrdo. Maybe other factors.
	private float mutationMultiplier = 1;
	// Increased by PlantOrdo. Maybe other factors.
	private float spreadMultiplier = 1;
	private boolean isCovered = false;
	
	
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("mutation", this.mutation);
		compound.setInteger("counter", this.counter);
		compound.setFloat("mMultiplier", this.mutationMultiplier);
		compound.setFloat("sMultiplier", this.spreadMultiplier);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.mutation = compound.getInteger("mutation");
		this.counter = compound.getInteger("counter");
		this.mutationMultiplier = compound.getFloat("mMultiplier");
		this.spreadMultiplier = compound.getFloat("sMultiplier");
		super.readFromNBT(compound);
	}
	
	@Override
	public void update() {
		if(this.world.isRemote) return;
		if(this.isCovered){
			this.counter++;
			if(this.counter%20 == 19) {
				for(int x = -12; x < 12; x++) {
					for(int z = -12 + 6*this.counter/20; z < -6 + 6*this.counter/20; z++) {
						for(int y = -4; y < 4; y++) {
							Block b2 = this.world.getBlockState(pos.add(x, y, z)).getBlock(); 
							if(b2 instanceof IMutationCatalyst) {
								this.mutation += ((IMutationCatalyst) b2).mutationIncrease() * this.mutationMultiplier;
							}	// TODO: Make vanilla plants work too (and be spread as well)
							if(b2 instanceof BlockPlant) {
								((BlockPlant)b2).spread(this.world, this.pos.add(x, y, z), this.mutation, this.spreadMultiplier);
							}
						}
					}
				}
			}
			if(this.counter >= 99) this.counter = 0;
			
			if(this.mutation > this.getReqMutation()) this.completeMutation();
			
			
		}else {
			this.counter = 0;
			this.mutation = 0;
		}
	}
	
	public void blockNeighbourUpdate() {
		if(this.world.getBlockState(this.pos.up()).getBlock() == Blocks.GRASS || this.world.getBlockState(this.pos.up()).getBlock() == Blocks.DIRT) {
			this.isCovered = true;
		}else {
			this.isCovered = false;
		}
	}
	
	public void blockAreaUpdate() {
		int count = 0;
		for(int x = -3; x < 4; x++) {
			for(int y = -3; y < 4; y++) {
				for(int z = -3; z < 4; z++) {
					IBlockState b = this.world.getBlockState(this.pos.add(x, y, z));
					if(b.getBlock() == BlockRegistry.PlantVijhiss && b.getValue(EnumHalf.HALF) == EnumHalf.BOTTOM) {
						count++;
						if(count >=  4) break;
					}
				}
			}
		}
		this.spreadMultiplier = 1 + count;
		this.mutationMultiplier = 1 + count;
	}
	
	public int getMutation() {
		return this.mutation;
	}
	
	private int getReqMutation() {
		return 6000;
	}
	
	private Block getNewPlant() {
		int a = this.world.rand.nextInt(2);
		switch(a) {
		case 0: return BlockRegistry.PlantVijhiss;
		case 1: return BlockRegistry.PlantBreaker;
		default: return null;
		}
	}
	
	private void completeMutation() {
		if(this.world.getBlockState(pos.up().up()).getBlock() == Blocks.AIR && this.world.getBlockState(pos.up().up().up()).getBlock() == Blocks.AIR) {
			this.world.setBlockState(pos.up().up(), this.getNewPlant().getDefaultState());
			this.world.setBlockToAir(pos.down());
			this.mutation = 0;
		}
	}
	
	public void setSpreadMultiplier(float multiplier) {
		this.spreadMultiplier = multiplier;
	}
	
	public void setMutationMultiplier(float multiplier) {
		this.mutationMultiplier = multiplier;
	}
	
	public float getSpreadMultiplier() {
		return this.spreadMultiplier;
	}
	
	public float getMutationMultiplier() {
		return this.mutationMultiplier;
	}
	
	

}
