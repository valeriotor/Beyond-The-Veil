package com.valeriotor.BTV.tileEntities;

import java.util.Random;

import javax.annotation.Nullable;

import com.valeriotor.BTV.world.BiomeRegistry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class TileSlugBait extends TileEntity implements ITickable{
	
	private boolean slugPresent = false;
	private double[] coords = new double[2];
	private int counter = 0;
	private boolean xNegative = false;
	private boolean zNegative = false;
	
	public TileSlugBait() {
		
	}
	
	@Override
	public void update() {
		if(this.world.getBlockState(pos.add(1, 0, 0)) != Blocks.WATER.getDefaultState() ||
		   this.world.getBlockState(pos.add(0, 0, 1)) != Blocks.WATER.getDefaultState() ||
		   this.world.getBlockState(pos.add(-1, 0, 0)) != Blocks.WATER.getDefaultState() ||
		   this.world.getBlockState(pos.add(0, 0, -1)) != Blocks.WATER.getDefaultState() ||
		   this.world.getBlockState(pos.add(0, 1, 0)) == Blocks.WATER.getDefaultState()) {
			this.slugPresent = false;
			return;
		}
		if(!this.world.isRemote) {
			if(!slugPresent) {
				counter++;
				if(counter > 64) {
					slugPresent = true;
					counter = 0;
					coords[0] = this.world.rand.nextBoolean() == true ? -this.world.rand.nextDouble()*4-1 : this.world.rand.nextDouble()*4+1;
					coords[1] = this.world.rand.nextBoolean() == true ? -this.world.rand.nextDouble()*4-1 : this.world.rand.nextDouble()*4+1;
					xNegative = coords[0] < 0 ? true : false;
					zNegative = coords[1] < 0 ? true : false;
					if(this.world.getBlockState(new BlockPos(this.pos.getX()+coords[0]+0.5, this.pos.getY(), this.pos.getZ()+coords[1]+0.5)) != Blocks.WATER.getDefaultState()) {
						slugPresent = false;
						counter = 65;
					}
				}
				this.sendUpdates(this.world);
				return;
			}
		
			if(Math.abs(coords[0]) < 1 && Math.abs(coords[1]) < 1) {
				slugPresent = false;
				counter = 65;
			}
			counter++;
			if(counter > 64) slugPresent = false;
			
			if((this.world.rand.nextBoolean() && Math.abs(coords[0]) > 1) || (Math.abs(coords[1]) < 1 && slugPresent)) {
				if(xNegative) coords[0]+=0.06;
				else coords[0]-=0.06;
			}else {
				if(zNegative) coords[1]+=0.06;
				else coords[1]-=0.06;
			}
			if(this.world.getBlockState(new BlockPos(this.pos.getX()+coords[0]+0.5, this.pos.getY(), this.pos.getZ()+coords[1]+0.5)) != Blocks.WATER.getDefaultState()) {
				slugPresent = false;
				counter = 65;
			}
			this.sendUpdates(this.world);
		}else {
			if(coords[0] != 0 || coords[1] != 0) this.world.spawnParticle(EnumParticleTypes.DRIP_WATER, this.pos.getX()+coords[0]+0.5, this.pos.getY()+1, this.pos.getZ()+coords[1]+0.5, 1, 1, 1);
		}
		
		
		
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	private void sendUpdates(World worldObj) {
		world.markBlockRangeForRenderUpdate(pos.add(-5, -1, -5), pos.add(5, 1, 5));
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
		markDirty();
	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}
	
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("slugPresent", this.slugPresent);
		compound.setInteger("counter", this.counter);
		compound.setDouble("xCoord", this.coords[0]);
		compound.setDouble("zCoord", this.coords[1]);
		compound.setBoolean("xNegative", this.xNegative);
		compound.setBoolean("zNegative", this.zNegative);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.slugPresent = compound.getBoolean("slugPresent");
		this.counter = compound.getInteger("counter");
		this.coords[0] = compound.getDouble("xCoord");
		this.coords[1] = compound.getDouble("zCoord");
		this.xNegative = compound.getBoolean("xNegative");
		this.zNegative = compound.getBoolean("zNegative");
	}
	
	public boolean catchSlug(double posX, double posZ) {
		if(Math.abs(this.pos.getX()+coords[0]+0.05-posX) < 0.65 && Math.abs(this.pos.getZ()+coords[1]+0.05-posZ) < 0.65) {
			return this.catchSlugForce();
		}
		return false;
	}
	
	public boolean catchSlugForce() {
		if(this.slugPresent) {
			this.slugPresent = false;
			this.counter = 65;
			this.sendUpdates(this.world);
			return true;
		}
		return false;
	}
	
}
