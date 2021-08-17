package com.valeriotor.beyondtheveil.entities.ictya;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityDreadfish extends EntityIctya {

	public EntityDreadfish(World worldIn) {
		super(worldIn);
	} 
	
	@Override
	protected void initEntityAI() {	 	
		super.initEntityAI();
		this.tasks.addTask(0, new AIAttackMeleeAgainstLargeCreatures(this, 1.4, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIWander(this, 0.4D, 100));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);	
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(3D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(18D);
	}

	@Override
	protected boolean shouldAttack(EntityLivingBase attacked) {
		if (attacked instanceof EntityDreadfish) {
			return false;
		}
		return super.shouldAttack(attacked);
	}

	@Override
	public IctyaSize getSize() {
		return IctyaSize.MEDIUM;
	}

	@Override
	public double getFoodValue() {
		return 250;
	}

	@Override
	public double getMaxFood() {
		return 300;
	}

	@Override
	public double getFoodPer32Ticks() {
		return 3.2F;
	}	
	
	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount) {
		Entity ent = damageSrc.getTrueSource();
		if(ent instanceof EntityLivingBase && !(ent instanceof EntityDreadfish)) {
			List<EntityDreadfish> nearbyDreads = world.getEntities(EntityDreadfish.class, e -> e.getDistance(this) < 35);
			for(EntityDreadfish e : nearbyDreads) {
				e.setAttackTarget((EntityLivingBase)ent);
			}
		}
		super.damageEntity(damageSrc, damageAmount);
	}
	
}
