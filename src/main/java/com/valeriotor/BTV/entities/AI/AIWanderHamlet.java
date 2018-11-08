package com.valeriotor.BTV.entities.AI;

import com.valeriotor.BTV.entities.EntityHamletDweller;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AIWanderHamlet extends EntityAIBase{
	
	private int goWorship;
	private int goHome;
	private final EntityHamletDweller entity;
	private World world;
	private BlockPos startPos;
	private BlockPos endPos;
	
	public AIWanderHamlet( EntityLiving entity, int goWorshipTime, int goHomeTime, World w) {
		this.entity = (EntityHamletDweller) entity;
		this.goHome = goHomeTime;
		this.goWorship = goWorshipTime;
		this.world = w;
		this.setMutexBits(1);
	}
	
	@Override
	public boolean shouldExecute() {
		if(this.world.getWorldTime() == this.goWorship || this.world.getWorldTime() == this.goHome) {
			startPos = this.entity.getPosition();
			return true;
		}
		return false;
	}
	
	@Override
	public void startExecuting() {
		super.startExecuting();
		if(Math.abs(this.goHome-this.world.getWorldTime()) < 10) {
			endPos = this.entity.getHomePosition();
			this.entity.getNavigator().tryMoveToXYZ(endPos.getX(), endPos.getY(), endPos.getZ(), 0.5);
		}else {
			endPos = this.entity.getVillageCenter();
			this.entity.getNavigator().tryMoveToXYZ(endPos.getX(), endPos.getY(), endPos.getZ(), 0.5);
		}
		
		
	}
	
	public boolean shouldContinueExecuting()
    {
        return !this.entity.getNavigator().noPath();
    }

}
