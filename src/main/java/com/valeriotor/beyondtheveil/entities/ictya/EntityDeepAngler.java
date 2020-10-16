package com.valeriotor.beyondtheveil.entities.ictya;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityDeepAngler extends EntityIctya{

	public EntityDeepAngler(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void initEntityAI() {	 	
		super.initEntityAI();
		this.tasks.addTask(0, new DeepAnglerAIAttackMelee(this, 1.6, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIWander(this, 0.4D, 5));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(70.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);	
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8D);
	}

	@Override
	public IctyaSize getSize() {
		return IctyaSize.SMALL;
	}

	@Override
	public double getFoodValue() {
		return 100;
	}

	@Override
	public double getMaxFood() {
		return 100;
	}

	@Override
	public double getFoodPer32Ticks() {
		return 1;
	}
	
	private static class DeepAnglerAIAttackMelee extends EntityAIAttackMelee {
		private boolean shouldMove = false;
		public DeepAnglerAIAttackMelee(EntityCreature creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}
		
		@Override
		public void startExecuting() {
			lure();
		}
		
		@Override
		public void updateTask() {
			if(shouldMove)
				super.updateTask();
			else {
				lure();
				if(attacker.getAttackTarget().getDistanceSq(attacker) < 100)
					shouldMove = true;
			}
		}
		
		private void lure() {
			EntityLivingBase ent = attacker.getAttackTarget();
			if(ent instanceof EntityIctya && (attacker.ticksExisted & 31) == 0) {
				EntityIctya e = (EntityIctya)ent;
				//System.out.println(e.getName() + " " + e.getPosition());
				e.getNavigator().clearPath();
				e.getNavigator().tryMoveToEntityLiving(attacker, e.getAIMoveSpeed()*2.5);
			}
		}
		
		@Override
		public void resetTask() {
			shouldMove = false;
			super.resetTask();
		}
		
	}

}
