package com.valeriotor.beyondtheveil.tileEntities;

import java.util.List;
import java.util.UUID;

import com.valeriotor.beyondtheveil.entities.EntityCrawlingVillager;
import com.valeriotor.beyondtheveil.events.special.CrawlerWorshipEvents;
import com.valeriotor.beyondtheveil.worship.CrawlerWorship;
import com.valeriotor.beyondtheveil.worship.CrawlerWorship.WorshipType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileStatue extends TileEntity implements ITickable{
	
	private WorshipType type;
	private UUID master;
	private String masterName;
	private int timer;
	
	public TileStatue() {}
	
	public TileStatue(WorshipType type) {
		this.type = type;
		this.resetTimer();
	}
	
	@Override
	public void update() {
		if(world.isRemote) return;
		this.timer--;
		if(this.timer <= 0) {
			this.resetTimer();
			List<EntityCrawlingVillager> worms = world.getEntities(EntityCrawlingVillager.class, e -> e.getMasterID() == master && e.getDistanceSq(pos) < 625);
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
