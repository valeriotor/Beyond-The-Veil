package com.valeriotor.beyondtheveil.tileEntities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.entities.IPlayerGuardian;
import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.lib.BTVSounds;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileHeart extends TileEntity implements ITickable{

	public TileHeart() {
	
	}
	
	private int counter = 69;
	private int animCounter = 0;
	private int wellCounter = -1;
	private BlockPos link;
	private Set<EntityLiving> damned = new HashSet<>();
	
	@Override
	public void update() {
		if(this.world.isRemote) {
			if(this.wellCounter > 0) 
				this.wellCounter--;
			this.animCounter++;
			if(this.animCounter >= 30) {
				this.animCounter = 0;
				this.world.playSound(pos.getX(), pos.getY(), pos.getZ(), BTVSounds.heartbeat, SoundCategory.AMBIENT, 1, 1, true);
			}
			
		} else {
			if(this.wellCounter > 0) {
				this.wellCounter--;
				if(this.wellCounter == 0)
					this.world.setBlockState(pos, BlockRegistry.BlockBloodWell.getDefaultState());
			}
			counter--;
			if(counter % 10 == 0) {
				if(this.link != null) {
					Iterator<EntityLiving> iter = damned.iterator();
					while(iter.hasNext()) {
						EntityLiving e = iter.next();
						if(e == null || e.isDead) iter.remove();
						else if(e.getDistanceSq(link) < 9) iter.remove();
						else e.getNavigator().setPath(e.getNavigator().getPathToPos(this.link), 0.9);
					}
				} else damned.clear();
			}
			if(counter <= 0) {
				counter = 69;
				List<EntityLiving> undead = this.world.getEntities(EntityLiving.class, e -> e.isEntityUndead() && e.getDistanceSq(pos) < 324 && e.getDistanceSq(pos) > 16 && !(e instanceof IPlayerGuardian));
				for(EntityLiving e : undead) {
					ServerTickEvents.insertDamned(e.getEntityId());
					if(!damned.contains(e)) e.getNavigator().setPath(e.getNavigator().getPathToPos(this.pos), 0.9);
				}
				if(link != null) {
					List<EntityLiving> undead2 = this.world.getEntities(EntityLiving.class, e -> e.isEntityUndead() && e.getDistanceSq(pos) < 20 && !(e instanceof IPlayerGuardian));
					for(EntityLiving e : undead2) {
						ServerTickEvents.insertDamned(e.getEntityId());
						damned.add(e);
						e.getNavigator().setPath(e.getNavigator().getPathToPos(this.link), 0.9);
					}
				}
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if(link != null) compound.setLong("link", link.toLong());
		compound.setInteger("wellcount", wellCounter);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("link")) this.link = BlockPos.fromLong(compound.getLong("link"));
		this.wellCounter = compound.getInteger("wellcount");
		super.readFromNBT(compound);
	}
	
	public void setLink(BlockPos pos) {
		this.link = pos;
	}
	
	public BlockPos getLink() {
		return this.link;
	}
	
	@SideOnly(Side.CLIENT)
	public int getAnimCounter() {
		return this.animCounter;
	}
	
	public void startWell() {
		this.wellCounter = 60;
		this.sendUpdates(world);
		markDirty();
	}
	
	public int getWellCounter() {
		return this.wellCounter;
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
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}

}
