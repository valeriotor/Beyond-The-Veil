package com.valeriotor.beyondtheveil.entities.util;

import com.valeriotor.beyondtheveil.entities.EntityDeepOne;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.MathHelper;

public class WaterMoveHelper extends EntityMoveHelper {

	public WaterMoveHelper(EntityLiving entitylivingIn) {
		super(entitylivingIn);
	}
	
	@Override
	public void onUpdateMoveHelper() {
		//System.out.println(this.posY + " y " + this.entity.posY);
		if (this.action == EntityMoveHelper.Action.MOVE_TO && entity.isInWater()) {
			this.action = EntityMoveHelper.Action.WAIT;
            double d0 = this.posX - this.entity.posX;
            double d1 = this.posZ - this.entity.posZ;
            double d2 = this.posY - this.entity.posY;
            //System.out.println(this.posY + " y " + this.entity.posY);
            //System.out.println(this.posX + " x " + this.entity.posX);
            //System.out.println(this.posZ + " z " + this.entity.posZ);
            double d3 = d0 * d0 + d1 * d1;
            if (d3 < 2.500000277905201E-1D /*&& d2*d2 < 1.5D*/)
            {
                this.entity.setMoveForward(0.0F);
                return;
            }

            float f9 = (float)(MathHelper.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f9, 90.0F);
            if(this.posY < this.entity.posY)
            	entity.motionY -= 0.03;
            else
            	entity.motionY += 0.03;
            if(entity instanceof EntityDeepOne && speed == 0.0) {
            	speed = 1.5;
            }
            this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
		} else {
			if(entity instanceof EntityDeepOne) {
	            double d0 = this.posX - this.entity.posX;
	            double d1 = this.posZ - this.entity.posZ;
	            double d2 = this.posY - this.entity.posY;
	            double d3 = d0 * d0 + d1 * d1;
				if(d3 < 2.500000277905201E-1D) {
					this.entity.setMoveForward(0.0F);
	                this.action = EntityMoveHelper.Action.WAIT;
	                return;
				}
			}
			super.onUpdateMoveHelper();
		}
	}
	
}
