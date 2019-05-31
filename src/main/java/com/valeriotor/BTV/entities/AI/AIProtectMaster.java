package com.valeriotor.BTV.entities.AI;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class AIProtectMaster extends EntityAITarget{
	
	private EntityLiving ent;
    EntityLivingBase attacker;
    private int timestamp;
	
	public AIProtectMaster(EntityCreature creature) {
		super(creature, false);
		this.ent = creature;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		if(!(this.ent instanceof IPlayerGuardian)) return false;
		EntityPlayer p = ((IPlayerGuardian)ent).getMaster();
		if(p != null) {
			this.attacker = p.getRevengeTarget();
            int i = p.getRevengeTimer();
            return i != this.timestamp && this.isSuitableTarget(this.attacker, false) /*&& this.ent.shouldAttackEntity(this.attacker, p)*/;
		}
		return false;
	}
	
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.attacker);
        EntityLivingBase entitylivingbase = ((IPlayerGuardian)this.ent).getMaster();

        if (entitylivingbase != null)
        {
            this.timestamp = entitylivingbase.getRevengeTimer();
        }

        super.startExecuting();
    }
	
}
