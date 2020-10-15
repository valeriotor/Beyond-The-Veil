package com.valeriotor.beyondtheveil.entities.ictya;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntitySarfin extends EntityIctya{

	public EntitySarfin(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void initEntityAI() {	 	
		super.initEntityAI();
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIWander(this, 0.4D, 100));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
	}

	@Override
	public IctyaSize getSize() {
		return IctyaSize.PREY;
	}

	@Override
	public double getFoodValue() {
		return 20;
	}

	@Override
	public double getMaxFood() {
		return 20;
	}

	@Override
	public double getFoodPer32Ticks() {
		return 0;
	}

}
