package com.valeriotor.beyondtheveil.entities;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.entities.AI.AIProtectMaster;
import com.valeriotor.beyondtheveil.entities.AI.AIRevenge;
import com.valeriotor.beyondtheveil.lib.BTVSounds;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class EntityCrazedWeeper extends EntityMob{

	private int animationTicks = 0;
	private Animation transformAnimation;
	private int transformCounter = 55;
	private static final DataParameter<Boolean> TRANSFORMING = EntityDataManager.<Boolean>createKey(EntityCrazedWeeper.class, DataSerializers.BOOLEAN);
	public EntityCrazedWeeper(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);	
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(3D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12D);
	}
	
	protected void initEntityAI() {	 	
        this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIAttackMelee(this, 1.4, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new AIRevenge(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, false));
	}
	
	@Override
	 protected void entityInit() {
		super.entityInit();
		this.dataManager.register(TRANSFORMING, true);
	 }
	
	@Override
	protected boolean canDespawn() {
		return false;
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	@Override
	public void onLivingUpdate() {
		if(!this.world.isRemote) {
			if(this.transformCounter > 0) {
				this.transformCounter--;
				EntityPlayer p = world.getClosestPlayerToEntity(this, 5);
				if(p != null) {
					this.faceEntity(p, 180, 180);
					if(transformCounter % 10 == 0)
					this.motionX = ((transformCounter%20)/10 - 0.5)/40;
				}
				if(this.transformCounter == 0) {
					this.getDataManager().set(TRANSFORMING, false);
				}
			}
		} else {
			if(this.getDataManager().get(TRANSFORMING) && this.animationTicks == 0)
				this.transformAnimation = new Animation(AnimationRegistry.crazed_weeper_transform);
			this.animationTicks++;
			if(this.transformAnimation != null) {
				this.transformAnimation.update();
				if(this.animationTicks == 20)
					this.world.playSound(posX, posY, posZ, BTVSounds.weeper_transform, SoundCategory.HOSTILE, 1, 1, false);
				if(this.transformAnimation.isDone()) {
					this.transformAnimation = null;
				}
			}
			if(this.animationTicks == 200) {
				this.animationTicks = 0;
			}
		}
		super.onLivingUpdate();
	}
	
	public int getAnimationTicks() {
		return this.animationTicks;
	}
	
	public Animation getTransformAnimation() {
		return this.transformAnimation;
	}
	
	@Override
	public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
		if(!this.getDataManager().get(TRANSFORMING))
			super.setAttackTarget(entitylivingbaseIn);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("transforming", this.getDataManager().get(TRANSFORMING));
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.getDataManager().set(TRANSFORMING, compound.getBoolean("transforming"));
		if(!this.getDataManager().get(TRANSFORMING)) this.transformCounter = -1;
		super.readFromNBT(compound);
	}

}
