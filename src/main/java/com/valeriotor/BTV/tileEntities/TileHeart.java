package com.valeriotor.BTV.tileEntities;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileHeart extends TileEntity implements ITickable{

	public TileHeart() {
	
	}
	
	private int counter = 100;
	@Override
	public void update() {
		//System.out.println();
		if(this.world.isRemote) return;
		//System.out.println("test");
		counter--;
		if(counter <= 0) {
			counter = 100;
			List<EntityLiving> undead = this.world.getEntities(EntityLiving.class, e -> e.isEntityUndead() && e.getDistanceSq(pos) < 324 && e.getDistanceSq(pos) > 16);
			for(EntityLiving e : undead) {
				e.getNavigator().setPath(e.getNavigator().getPathToPos(this.pos), 0.9);
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return super.writeToNBT(compound);
	}

}
