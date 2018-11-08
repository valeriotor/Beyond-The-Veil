package com.valeriotor.BTV.entities.AI;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AISwim extends EntityAIBase{
	
	private final EntityLiving entity;

	
	public AISwim(EntityLiving entityIn) {
		this.entity = entityIn;
		this.setMutexBits(4);
	}
	
	public boolean shouldExecute()
    {
        
        return this.entity.isInWater();
		
    }
	
	 public void startExecuting()
	    {
	        
		 
	     this.entity.posX+=10;
	     //this.entity.getNavigator().tryMoveToXYZ((double)i + 0.5D, (double)j, (double)k + 0.5D, 1.0D);
	        
	    }
	 
	 public boolean shouldContinueExecuting()
	    {	
		 	if(this.entity.isInWater()) {
		 	
	        return true;
		 	}else {
		 		return false;
		 	}
	    }
	 
	 public void updateTask()
	    {
	        
		 this.entity.posY+=0.1;
	        //this.entity.getNavigator().tryMoveToXYZ((double)i + 0.5D, (double)j, (double)k + 0.5D, 1.0D);
	        
	    }
}
