package com.valeriotor.BTV.entities.AI;

import com.valeriotor.BTV.entities.EntityHamletDweller;
import com.valeriotor.BTV.entities.EntityHamletDweller.ProfessionsEnum;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class AIDwellerWanderHamlet extends EntityAIBase{
	
	private EntityHamletDweller dweller;
	
	public AIDwellerWanderHamlet(EntityHamletDweller e) {
		this.dweller = e;
		this.setMutexBits(1);
	}
	
	@Override
	public boolean shouldExecute() {
		long time = dweller.world.getWorldTime();
		if(Math.abs(time-dweller.goWorshipTime) < 500 || time == 100 ||
		  (Math.abs(time - dweller.goHomeTime) < 1000 || Math.abs(time - 23500) < 1000))
			return dweller.getDwellerHome() != null && dweller.getProfession() == ProfessionsEnum.FISHERMAN;
		return false;
	}
	
	@Override
	public void updateTask() {
		long time = dweller.world.getWorldTime();
		if(time == 100) {
			dweller.goWorshipTime = dweller.world.rand.nextInt(12000)+9000;
			dweller.goHomeTime = Math.min(dweller.goWorshipTime+5000, 23000);
			int r = dweller.world.rand.nextInt(11)-5;
			int s = r < 0 ? -5-r : 5-r;
			dweller.destination = dweller.villageCenter.add(r, 0, s);
			dweller.getNavigator().tryMoveToXYZ(dweller.home.getX(), dweller.home.getY(), dweller.home.getZ(), 1.0);
		} else if(Math.abs(time-dweller.goWorshipTime) < 500) {
			if(dweller.destination == null) dweller.destination = dweller.villageCenter.offset(EnumFacing.getHorizontal(dweller.world.rand.nextInt(3)),3);
			dweller.getNavigator().tryMoveToXYZ(dweller.destination.getX(), dweller.villageCenter.getY(), dweller.destination.getZ(), 1.0);
		
		}
		else if(Math.abs(time - dweller.goHomeTime) < 1000 || Math.abs(dweller.world.getWorldTime() - 23500) < 1000) {
			dweller.getNavigator().tryMoveToXYZ(dweller.home.getX(), dweller.home.getY(), dweller.home.getZ(), 1.0);
		}
	}
	
}
