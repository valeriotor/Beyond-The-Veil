package com.valeriotor.BTV.entities.AI;

import com.valeriotor.BTV.entities.EntityShoggoth;
import com.valeriotor.BTV.shoggoth.ShoggothBuilding;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class AIShoggothBuild extends EntityAIBase{
	
	private EntityShoggoth shoggoth;
	private int timer = 0;
	
	public AIShoggothBuild(EntityShoggoth shoggoth) {
		this.shoggoth = shoggoth;
		this.setMutexBits(1);
	}
	
	@Override
	public boolean shouldExecute() {
		return shoggoth.map != null;
	}

	@Override
	public boolean shouldContinueExecuting() {
		int progress = shoggoth.progress;
		if(progress < shoggoth.map.getInteger("num")) {
			if(shoggoth.building == null) {
				shoggoth.progress = 0;
				progress = 0;
				shoggoth.building = ShoggothBuilding.getBuilding(shoggoth.world, shoggoth.map.getCompoundTag(String.format("b%d", progress)));
			}
			BlockPos pos = new BlockPos(shoggoth.building.centerX, shoggoth.building.centerY, shoggoth.building.centerZ).offset(EnumFacing.getHorizontal(shoggoth.building.rotation + 2));
			timer--;
			if(timer < 0) {
				timer = 10;
				shoggoth.getNavigator().setPath(shoggoth.getNavigator().getPathToXYZ(pos.getX(), pos.getY(), pos.getZ()), 1);
			}
			if(Math.abs(shoggoth.posX - pos.getX()) < 5 && Math.abs(shoggoth.posZ - pos.getZ()) < 5) {
				shoggoth.building.placeBlock(shoggoth.world);
			}
			if(shoggoth.building.isDone()) {
				shoggoth.progress++;
				if(shoggoth.map.hasKey(String.format("b%d", shoggoth.progress))) {
					shoggoth.building = ShoggothBuilding.getBuilding(shoggoth.world, shoggoth.map.getCompoundTag(String.format("b%d", shoggoth.progress)));
				}
			}
		}
		return shoggoth.progress < shoggoth.map.getInteger("num");
	}

}
