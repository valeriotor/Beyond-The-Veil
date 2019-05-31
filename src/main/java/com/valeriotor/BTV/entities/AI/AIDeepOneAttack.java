package com.valeriotor.BTV.entities.AI;

import com.valeriotor.BTV.entities.EntityDeepOne;

import net.minecraft.entity.ai.EntityAIAttackMelee;

public class AIDeepOneAttack extends EntityAIAttackMelee{
	
	private int raisedArm;
	private int raisedArmTicks;
	private EntityDeepOne deepOne;

	public AIDeepOneAttack(EntityDeepOne creature, double speedIn, boolean useLongMemory) {
		super(creature, speedIn, useLongMemory);
		this.deepOne = creature;
	}
	
	@Override
	public void startExecuting() {
		this.raisedArmTicks = 0;
		this.raisedArm = deepOne.world.rand.nextInt(3) + 1;
		
		super.startExecuting();
	}
	
	@Override
	public void updateTask() {
		super.updateTask();
		this.raisedArmTicks++;
		if(this.raisedArmTicks >= 5 && this.attackTick < 9) {
			this.deepOne.setRaisedArm(0);
			this.raisedArm = deepOne.world.rand.nextInt(3) + 1;
		}else {
			this.deepOne.setRaisedArm(raisedArm);
		}
	}
	
	@Override
	public void resetTask() {
		super.resetTask();
		this.deepOne.setRaisedArm(0);
	}

}
