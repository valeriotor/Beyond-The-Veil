package com.valeriotor.BTV.tileEntities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.valeriotor.BTV.shoggoth.BaseBuilding2D;
import com.valeriotor.BTV.shoggoth.Building2D;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos.MutableBlockPos;

public class TileCityMapper extends TileEntity implements ITickable{
	
	public byte[][] colors = new byte[201][201];
	public byte[][] heights = new byte[201][201];
	public List<Building2D> buildings = new ArrayList<>();
	public int timer = -100;
	public String viewingPlayer = null;
	private boolean smallUpdates = false;
	
	@Override
	public void update() {
		if(world.isRemote) return;
		if(timer <= 100) {
			final int x = this.pos.getX() + timer;
			for(int z = this.pos.getZ() - 100; z <= this.pos.getZ() + 100; z++) {
				int y = this.world.getHeight(x, z);
				MutableBlockPos pos = new MutableBlockPos(x, y, z);
				IBlockState state = this.world.getBlockState(pos);
				while(pos.getY() > 1 && !state.isFullBlock() && state.getBlock() != Blocks.WATER) {
					pos.move(EnumFacing.DOWN);
					state = this.world.getBlockState(pos);
				}
				if(pos.getY() > 1) {
					colors[timer + 100][z - this.pos.getZ() + 100] = (byte) state.getMapColor(this.world, this.pos).colorIndex;
					heights[timer + 100][z - this.pos.getZ() + 100] = (byte) (pos.getY() - 128);
				}
			}
			timer++;
		}else if(timer == 101) {
			this.sendUpdates();
			timer++;
		}
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		for(int i = 0; i < 201; i++) {
			compound.setByteArray(String.format("c%d", i), colors[i]);
			compound.setByteArray(String.format("h%d", i), heights[i]);
		}
		this.writeToNBTSmall(compound);
		return super.writeToNBT(compound);
	}
	
	public NBTTagCompound writeToNBTSmall(NBTTagCompound compound) {
		compound.setInteger("timer", timer);
		if(this.viewingPlayer != null) compound.setString("player", this.viewingPlayer);
		return this.writeBuildingsToNBT(compound);
	}
	
	public NBTTagCompound writeBuildingsToNBT(NBTTagCompound compound) {
		for(int i = 0; i < buildings.size(); i++) {
			NBTTagCompound nbt = new NBTTagCompound();
			compound.setTag(String.format("b%d", i), this.buildings.get(i).writeToNBT(nbt));
		}
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey(String.format("c%d", 100)))
			for(int i = 0; i < 201; i++) {
				if(compound.hasKey(String.format("c%d", i))) {
					colors[i] = compound.getByteArray(String.format("c%d", i));
					heights[i] = compound.getByteArray(String.format("h%d", i));
				}
			}
		if(compound.hasKey("player")) this.viewingPlayer = compound.getString("player");
		else this.viewingPlayer = null;
		this.timer = compound.getInteger("timer");
		this.readBuildingsFromNBT(compound);
		super.readFromNBT(compound);
		
	}
	
	public void readBuildingsFromNBT(NBTTagCompound compound) {
		int i = 0;
		this.buildings.clear();
		while(compound.hasKey(String.format("b%d", i))) {
			this.buildings.add(Building2D.getFromNBT(compound.getCompoundTag(String.format("b%d", i++))));
		}
	}
	
	public void sendUpdates() {
		markDirty();
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
	}
	
	public void sendSmallUpdates() {
		this.smallUpdates = true;
		this.sendUpdates();
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = this.smallUpdates ? this.writeToNBTSmall(new NBTTagCompound()) : this.writeToNBT(new NBTTagCompound());
		this.smallUpdates = false;
		return nbt;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		handleUpdateTag(pkt.getNbtCompound());
	}

}
