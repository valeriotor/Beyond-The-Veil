package com.valeriotor.beyondtheveil.entities.ictya;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityManOWar extends EntityIctya{

	public EntityManOWar(World worldIn) {
		super(worldIn);
		setSize(1.5F, 3.0F);
	}
	
	@Override
	protected void initEntityAI() {	 	
		super.initEntityAI();
		this.tasks.addTask(0, new ManOWarAttack(this));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIWander(this, 0.125D, 100));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.125D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12D);
	}

	@Override
	public IctyaSize getSize() {
		return IctyaSize.MEDIUM;
	}

	@Override
	public double getFoodValue() {
		return 120;
	}

	@Override
	public double getMaxFood() {
		return 300;
	}

	@Override
	public double getFoodPer32Ticks() {
		return 2;
	}
	
	public void feed(double amount) {
		currentFood = Math.min(getMaxFood(), currentFood + amount);
	}
	
	private static class ManOWarAttack extends EntityAIBase {
		protected EntityManOWar attacker;
		private int timeToSpawn = 60;
	    
		public ManOWarAttack(EntityManOWar creature) {
	        this.attacker = creature;
	        this.setMutexBits(3);
	    }
		
		@Override
		public boolean shouldExecute() {
			EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

	        if (entitylivingbase == null)
	        {
	            return false;
	        }
	        else if (!entitylivingbase.isEntityAlive())
	        {
	            return false;
	        }
	        else
	         return true;
		}
		@Override
		public boolean shouldContinueExecuting() {
			return this.shouldExecute() && this.attacker.getAttackTarget().getDistance(attacker) < 50;
		}
		
		@Override
		public void resetTask() {
			attacker.setAttackTarget(null);
		}
		
		@Override
		public void updateTask() {
			timeToSpawn--;
			if(timeToSpawn <= 0) {
				EntityJelly jel = new EntityJelly(attacker.world, 250, attacker);
				jel.setPosition(attacker.posX, attacker.posY, attacker.posZ);
				attacker.world.spawnEntity(jel);
				timeToSpawn = 60;
			}
		}
		
	}

}
