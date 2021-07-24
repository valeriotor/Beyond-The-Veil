package com.valeriotor.beyondtheveil.entities.AI;

import java.util.function.Predicate;

import com.valeriotor.beyondtheveil.entities.IPlayerMinion;

import com.valeriotor.beyondtheveil.entities.ictya.EntityJelly;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class AIRevenge extends EntityAITarget{

	private EntityLiving ent;
    EntityLivingBase attacker;
    private int timer = 0;
    private final Predicate<EntityLivingBase> predicate;
	
	public AIRevenge(EntityCreature creature) {
		this(creature, null);
	}
	
	public AIRevenge(EntityCreature creature, Predicate<EntityLivingBase> predicate) {
		super(creature, false);
		this.ent = creature;
		this.setMutexBits(1);
		this.predicate = predicate;
	}

	@Override
	public boolean shouldExecute() {
		if(this.ent != null) {
			if(ent.getRevengeTarget() != null) {
				if(predicate == null || predicate.test(ent.getRevengeTarget())) {
					EntityLivingBase attack = ent.getRevengeTarget();
					if(!(ent instanceof IPlayerMinion && attack instanceof EntityPlayer && ((EntityPlayer)attack).getPersistentID().equals(((IPlayerMinion)ent).getMasterID()))) {
						this.attacker = attack;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void startExecuting() {
		if(attacker instanceof EntityJelly) {
			this.ent.setAttackTarget(((EntityJelly)attacker).getMaster());
		} else {
			this.ent.setAttackTarget(attacker);
		}
		this.timer = 0;
		super.startExecuting();
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if(this.ent.getRevengeTimer() < 0) {
			return false;
		}
		if(!attacker.isEntityAlive())
			return false;
		if(predicate != null && !predicate.test(attacker))
			return false;
		if(ent.getRevengeTarget() != attacker && attacker.getDistance(ent) > 20) //TODO verify this works correctly for non Ictya entities
			return false;
		return super.shouldContinueExecuting();
	}
	
	@Override
	public void updateTask() {
		this.timer++;
		if(this.timer % 20 == 0) {
			this.timer = 0;
			if(attacker instanceof EntityJelly) {
				this.ent.setAttackTarget(((EntityJelly)attacker).getMaster());
			} else {
				this.ent.setAttackTarget(attacker);
			}
		}
	}

}
