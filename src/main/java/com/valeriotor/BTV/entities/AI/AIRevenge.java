package com.valeriotor.BTV.entities.AI;

import com.valeriotor.BTV.entities.IPlayerMinion;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class AIRevenge extends EntityAITarget{

	private EntityLiving ent;
    EntityLivingBase attacker;
    private int timer = 0;
	
	public AIRevenge(EntityCreature creature) {
		super(creature, false);
		this.ent = creature;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		if(this.ent != null) {
			if(ent.getRevengeTarget() != null) {
				EntityLivingBase attack = ent.getRevengeTarget();
				if(!(ent instanceof IPlayerMinion && attack instanceof EntityPlayer && ((IPlayerMinion)ent).getMasterID() == ((EntityPlayer)attack).getPersistentID() )) {
					this.attacker = attack;
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void startExecuting() {
		this.ent.setAttackTarget(attacker);
		this.timer = 0;
		super.startExecuting();
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if(this.ent.getRevengeTimer() < 0) {
			return false;
		}
		return super.shouldContinueExecuting();
	}
	
	@Override
	public void updateTask() {
		this.timer++;
		if(this.timer % 20 == 0) {
			this.timer = 0;
			this.ent.setAttackTarget(attacker);
		}
	}

}
