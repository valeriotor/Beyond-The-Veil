package com.valeriotor.BTV.tileEntities;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.blocks.EnumHalf;
import com.valeriotor.BTV.blocks.flora.BlockPlant;
import com.valeriotor.BTV.blocks.flora.IMutationCatalyst;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.blocks.BlocksTC;

public class TileMutator extends TileEntity implements ITickable{
	
	private int mutation = 0;
	private int counter = 0;
	// Increased by PlantOrdo. Maybe other factors.
	private float mutationMultiplier = 1;
	// Increased by PlantOrdo. Maybe other factors.
	private float spreadMultiplier = 1;
	private Aspect aspect;
	private boolean isCovered = false;
	
	
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("mutation", this.mutation);
		compound.setInteger("counter", this.counter);
		compound.setFloat("mMultiplier", this.mutationMultiplier);
		compound.setFloat("sMultiplier", this.spreadMultiplier);
		if(this.aspect != null) compound.setString("aspect", this.aspect.getName());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.mutation = compound.getInteger("mutation");
		this.counter = compound.getInteger("counter");
		this.mutationMultiplier = compound.getFloat("mMultiplier");
		this.spreadMultiplier = compound.getFloat("sMultiplier");
		if(compound.hasKey("aspect"))
			this.aspect = thaumcraft.api.aspects.Aspect.getAspect(compound.getString("aspect").toLowerCase());
		super.readFromNBT(compound);
	}
	
	@Override
	public void update() {
		if(this.world.isRemote) return;
		if(this.aspect != null && this.isCovered){
			System.out.println(this.mutation);
			this.counter++;
			if(this.counter%20 == 19) {
				for(int x = -12; x < 12; x++) {
					for(int z = -12 + 6*this.counter/20; z < -6 + 6*this.counter/20; z++) {
						for(int y = -4; y < 4; y++) {
							Block b2 = this.world.getBlockState(pos.add(x, y, z)).getBlock(); 
							if(b2 instanceof IMutationCatalyst) {
								this.mutation += ((IMutationCatalyst) b2).mutationIncrease() * this.mutationMultiplier;
							}
							if(b2 instanceof BlockPlant) {
								((BlockPlant)b2).spread(this.world, this.pos.add(x, y, z), this.mutation, this.aspect.getName(), this.spreadMultiplier);
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
		this.aspect = this.getAspectFromCrystal(this.world.getBlockState(this.pos.down()).getBlock());
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
					if(b.getBlock() == BlockRegistry.PlantOrdo && b.getValue(EnumHalf.HALF) == EnumHalf.BOTTOM) {
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
	
	private Aspect getAspectFromCrystal(Block b) {
		if(b == BlocksTC.crystalAir) {
			return Aspect.AIR;
		}else if(b == BlocksTC.crystalEarth) {
			return Aspect.EARTH;
		}else if(b == BlocksTC.crystalEntropy) {
			return Aspect.ENTROPY;
		}else if(b == BlocksTC.crystalFire) {
			return Aspect.FIRE;
		}else if(b == BlocksTC.crystalOrder) {
			return Aspect.ORDER;
		}else if(b == BlocksTC.crystalWater) {
			return Aspect.WATER;
		}else return null;
	}
	
	private int getReqMutation() {
		if(this.aspect == Aspect.ORDER) {
			return 3000;
		}else if(this.aspect == Aspect.EARTH) {
			return 6000;
		}
		return 10000;
	}
	
	private Block getNewPlant() {
		if(this.aspect == Aspect.ORDER) {
			return BlockRegistry.PlantOrdo;
		}else if(this.aspect == Aspect.EARTH) {
			return BlockRegistry.PlantTerra;
		}
		return BlockRegistry.PlantOrdo;
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
