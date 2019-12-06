package com.valeriotor.BTV.tileEntities;

import java.util.List;
import java.util.UUID;

import com.valeriotor.BTV.entities.EntityCrawlingVillager;
import com.valeriotor.BTV.events.ServerTickEvents;
import com.valeriotor.BTV.worship.CrawlerWorship;
import com.valeriotor.BTV.worship.CrawlerWorship.WorshipType;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileStatue extends TileEntity implements ITickable{
	
	private WorshipType type;
	private UUID master;	
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
			CrawlerWorship cw = ServerTickEvents.getWorship(this.master);
			int factor = this.type == WorshipType.DEFAULT ? 5 : 6;
			if(cw != null) {
				cw.setWorshipType(type);
				cw.setStrength(worms.size() / factor);
				cw.resetTimer();
			} else {
				ServerTickEvents.putWorship(master, new CrawlerWorship().setWorshipType(type).setStrength(worms.size() / factor));
			}
		}
	}
	
	private void resetTimer() {
		this.timer = 100;
	}
	
	public void setMaster(EntityPlayer p) {
		this.master = p.getPersistentID();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("type", this.type.ordinal());
		compound.setString("master", this.master.toString());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.type = WorshipType.values()[compound.getInteger("type")];
		this.master = UUID.fromString(compound.getString("master"));
		super.readFromNBT(compound);
	}

}
