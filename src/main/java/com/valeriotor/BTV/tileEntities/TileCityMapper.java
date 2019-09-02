package com.valeriotor.BTV.tileEntities;

import javax.annotation.Nullable;

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
	
	public int[][] colorsAndHeights = new int[201][201]; // Colors are stored in the right-most 3 bytes of the ints, height is in the left-most byte
	private int timer = -100;
	
	@Override
	public void update() {
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
					colorsAndHeights[timer + 100][z - this.pos.getZ() + 100] = (pos.getY() << 24) | (this.world.getBlockState(pos).getMapColor(this.world, this.pos).colorValue & 16777215);
				}
			}
			timer++;
		}else if(timer == 101) {
			timer++;
			this.sendUpdates();
		}
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		for(int i = 0; i < 201; i++)
			compound.setIntArray(String.format("cah%d", i), colorsAndHeights[i]);
		compound.setInteger("timer", timer);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		for(int i = 0; i < 201; i++) {
			if(compound.hasKey(String.format("cah%d", i))) {
				colorsAndHeights[i] = compound.getIntArray(String.format("cah%d", i));
			}
		}
		this.timer = compound.getInteger("timer");
		super.readFromNBT(compound);
	}
	
	public void sendUpdates() {
		markDirty();
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		handleUpdateTag(pkt.getNbtCompound());
	}

}
