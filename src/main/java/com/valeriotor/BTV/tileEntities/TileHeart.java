package com.valeriotor.BTV.tileEntities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileHeart extends TileEntity implements ITickable{

	public TileHeart() {
	
	}
	
	private int counter = 99;
	private BlockPos link;
	private Set<EntityLiving> damned = new HashSet<>();
	
	@Override
	public void update() {
		if(this.world.isRemote) return;
		counter--;
		if(counter % 10 == 0) {
			if(this.link != null) {
				Iterator<EntityLiving> iter = damned.iterator();
				while(iter.hasNext()) {
					EntityLiving e = iter.next();
					if(e == null || e.isDead) iter.remove();
					else if(e.getDistanceSq(link) < 16) iter.remove();
					else e.getNavigator().setPath(e.getNavigator().getPathToPos(this.link), 0.9);
				}
			} else damned.clear();
		}
		if(counter <= 0) {
			counter = 99;
			List<EntityLiving> undead = this.world.getEntities(EntityLiving.class, e -> e.isEntityUndead() && e.getDistanceSq(pos) < 324 && e.getDistanceSq(pos) > 16);
			for(EntityLiving e : undead) {
				if(!damned.contains(e)) e.getNavigator().setPath(e.getNavigator().getPathToPos(this.pos), 0.9);
			}
			if(link != null) {
				List<EntityLiving> undead2 = this.world.getEntities(EntityLiving.class, e -> e.isEntityUndead() && e.getDistanceSq(pos) < 16);
				for(EntityLiving e : undead2) {
					damned.add(e);
					e.getNavigator().setPath(e.getNavigator().getPathToPos(this.link), 0.9);
				}
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if(link != null) compound.setLong("link", link.toLong());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("link")) this.link = BlockPos.fromLong(compound.getLong("link"));
		super.readFromNBT(compound);
	}
	
	public void setLink(BlockPos pos) {
		this.link = pos;
	}
	
	public BlockPos getLink() {
		return this.link;
	}

}
