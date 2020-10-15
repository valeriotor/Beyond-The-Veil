package com.valeriotor.beyondtheveil.entities.ictya;

import java.util.List;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class EntityOctid extends EntityIctya{

	private static final DataParameter<Boolean> SQUIRTING = EntityDataManager.<Boolean>createKey(EntityOctid.class, DataSerializers.BOOLEAN);
	private int squirtTimer = 0;
	
	public EntityOctid(World worldIn) {
		super(worldIn);
	}
	
	protected void initEntityAI() {	 	
		super.initEntityAI();
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIWander(this, 0.4D, 100));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SQUIRTING, false);
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
		return 10;
	}

	@Override
	public double getFoodPer32Ticks() {
		return 0;
	}
	
	@Override
	protected <T extends EntityLivingBase> EntityAIAvoidEntity<T> getFleeingAI(EntityCreature entityIn, Class<T> classToAvoidIn,
			Predicate<? super T> avoidTargetSelectorIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
		return new OctidFlee<>(entityIn, classToAvoidIn, avoidTargetSelectorIn, avoidDistanceIn/2, farSpeedIn, nearSpeedIn);
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if(!world.isRemote) {
			if(squirtTimer > 0) {
				squirtTimer--;
				if(squirtTimer == 0)
					dataManager.set(SQUIRTING, false);
			}
		} else {
			if(dataManager.get(SQUIRTING)) {
				for(int i = 0; i < 27; i++) {
					world.spawnParticle(EnumParticleTypes.PORTAL, posX + (i%3-1)*0.1, posY + ((i/3)%3-1)*0.1, posZ + (i/9-1)*0.1, 0, 0, 0, 0);
				}
			}
		}
	}
	
	private void squirt() {
		dataManager.set(SQUIRTING, true);
		squirtTimer = 20;
		world.playSound(null, posX, posY, posZ, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.NEUTRAL, 1, 1);
	}
	
	private static class OctidFlee<T extends EntityLivingBase> extends EntityAIAvoidEntity<T> {

		public OctidFlee(EntityCreature entityIn, Class<T> classToAvoidIn, Predicate<? super T> avoidTargetSelectorIn,
				float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
			super(entityIn, classToAvoidIn, avoidTargetSelectorIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
		}
		
		@Override
		public void startExecuting() {
			List<Entity> ents = this.entity.world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().grow(5), e -> e instanceof EntityLivingBase);
			for(Entity e : ents) {
				((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20*3, 2, false, false));
			}
			((EntityOctid)this.entity).squirt();
			super.startExecuting();
		}

		
	}

}
