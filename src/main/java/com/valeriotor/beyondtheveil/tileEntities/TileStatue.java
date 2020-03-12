package com.valeriotor.beyondtheveil.tileEntities;

import java.util.List;
import java.util.UUID;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.entities.EntityCrawlingVillager;
import com.valeriotor.beyondtheveil.events.special.CrawlerWorshipEvents;
import com.valeriotor.beyondtheveil.worship.CrawlerWorship;
import com.valeriotor.beyondtheveil.worship.CrawlerWorship.WorshipType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class TileStatue extends TileEntity implements ITickable{
	
	private WorshipType type;
	private UUID master;
	private String masterName;
	private int timer;
	private Ticket ticket;
	private boolean triedTicket;
	
	public TileStatue() {}
	
	public TileStatue(WorshipType type) {
		this.type = type;
		this.resetTimer();
		triedTicket = false;
	}
	
	@Override
	public void invalidate() {
		ForgeChunkManager.releaseTicket(ticket);
		super.invalidate();
	}
	
	public void forceChunkLoading(Ticket ticket) {
		if (this.ticket == null)
			this.ticket = ticket;
	}
	
	@Override
	public void update() {
		if(world.isRemote) return;
		if(!triedTicket){
			triedTicket = true;
			if(ticket==null)
				ticket = ForgeChunkManager.requestTicket(BeyondTheVeil.instance,world,ForgeChunkManager.Type.NORMAL);
			if(ticket == null)
				System.out.println("A Statue was not able to request a ticket at pos: " + pos);
			else {
				ticket.getModData().setInteger("statueX",pos.getX());
				ticket.getModData().setInteger("statueY",pos.getY());
				ticket.getModData().setInteger("statueZ",pos.getZ());
				ForgeChunkManager.forceChunk(ticket,new ChunkPos(pos.getX()>>4,pos.getZ()>>4));
			}
			
		}
		this.timer--;
		if(this.timer <= 0) {
			this.resetTimer();
			if(this.master != null) {
				List<EntityCrawlingVillager> worms = world.getEntities(EntityCrawlingVillager.class, e -> this.master.equals(e.getMasterID()) && e.getDistanceSq(pos) < 625);
				if(worms.isEmpty()) return;
				CrawlerWorship cw = CrawlerWorshipEvents.getWorship(this.master);
				int factor = this.type == WorshipType.DEFAULT ? 5 : 6;
				if(cw != null) {
					cw.setWorshipType(type);
					cw.setStrength(worms.size() / factor);
					cw.resetTimer();
				} else {
					CrawlerWorshipEvents.putWorship(master, new CrawlerWorship().setWorshipType(type).setStrength(worms.size() / factor));
				}
			}
		}
	}
	
	private void resetTimer() {
		this.timer = 100;
	}
	
	public void setMaster(EntityPlayer p) {
		this.master = p.getPersistentID();
		this.masterName = p.getName();
	}
	
	public void setMasterID(UUID id) {
		this.master = id;
	}
	
	public void setMasterName(String name) {
		this.masterName = name;
	}
	
	public UUID getMaster() {
		return this.master;
	}
	
	public String getMasterName() {
		EntityPlayer p = this.world.getMinecraftServer().getPlayerList().getPlayerByUUID(master);
		if(p != null) {
			this.masterName = p.getName();
			return this.masterName;
		}
		return this.masterName;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if(this.type != null)
			compound.setInteger("type", this.type.ordinal());
		compound = this.writeToNBTSmol(compound);
		return super.writeToNBT(compound);
	}
	
	public NBTTagCompound writeToNBTSmol(NBTTagCompound compound) {
		if(this.master != null)
			compound.setString("master", this.master.toString());
		if(this.masterName != null)
			compound.setString("mastername", this.masterName);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("type"))
			this.type = WorshipType.values()[compound.getInteger("type")];
		this.readFromNBTSmol(compound);
		super.readFromNBT(compound);
	}
	
	public void readFromNBTSmol(NBTTagCompound compound) {
		if(compound.hasKey("master"))
			this.master = UUID.fromString(compound.getString("master"));
		if(compound.hasKey("mastername"))
			this.masterName = compound.getString("mastername");
	}

}
