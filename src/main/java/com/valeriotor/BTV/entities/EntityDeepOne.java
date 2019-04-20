package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.entities.AI.AIDeepOneAttack;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDeepOne extends EntityCreature{
	private int i = 0;
	private boolean isTargetInWater = false;
	private Block facingBlock;
	private Block facingBlockUp;
	private static final DataParameter<Integer> ARM_RAISED = EntityDataManager.<Integer>createKey(EntityDeepOne.class, DataSerializers.VARINT);
	public EntityDeepOne(World worldIn) {
		super(worldIn);
		
	}
	
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes(); 

	    // standard attributes registered to EntityLivingBase
	   getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
	   getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	   getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
	   getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
	   

	    // need to register any additional attributes
	   getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	   getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(18.0D);
	}
	
	 protected void initEntityAI() {	
		 	
	        //this.tasks.addTask(0, new EntityAISwimming(this));
	        //this.tasks.addTask(1, new AISwim(this));
	        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	        this.tasks.addTask(8, new EntityAILookIdle(this));
	        this.tasks.addTask(2, new AIDeepOneAttack(this, 1.5D, true));
	        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
	        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
	 }
	 
	 @Override
	 protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ARM_RAISED, -1);
	 }
	 
	 @SideOnly(Side.CLIENT)
	 public int getRaisedArm() {
		 return this.dataManager.get(ARM_RAISED);
	 }
	 
	 public void setRaisedArm(int arm) {
		 this.dataManager.set(ARM_RAISED, arm);
	 }
	 
	 @Override
	    public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
	        super.setAttackTarget(entitylivingbaseIn);
	 }
	 
	 @Override
	    public boolean attackEntityAsMob(Entity entityIn) {
	        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
	 
	        //if(flag) {
	            //this.applyEnchantments(this, entityIn);
	        //}
	 
	        return flag;
	    }
	 
	
	 
	 public void onLivingUpdate() {
		 super.onLivingUpdate();
		 if(this.isInWater()) {
			 i++;
			 if (this.getHealth() < this.getMaxHealth() && i>=20)
	            {
	                this.heal(1.0F);
	                i=0;
	            }
			 if(this.getAttackTarget() != null) {
				 if(this.world.getBlockState(this.getPosition().add(0, 1, 0)).getBlock() != Blocks.WATER) {
					 if(this.getAttackTarget().posY>this.posY+0.3 && isTargetInWater) {
					 		this.motionY=0.1;
				 }
				 }else if(this.getAttackTarget().posY>this.posY) {
				 		this.motionY=0.1;
				 		
				 	}
			 	
			 
			 
			 
			 double angle = Math.atan((Math.abs(this.getAttackTarget().posX-this.posX))/(Math.abs(this.getAttackTarget().posZ-this.posZ)));
			 
			 if(this.getAttackTarget().posX>this.posX) {
			 	 this.motionX = Math.sin(angle)/6;
			 	 
			 }else {
			 	 this.motionX = -Math.sin(angle)/6;
			 	 
			 }
			 if(this.getAttackTarget().posZ>this.posZ) {
			 	 this.motionZ = Math.cos(angle)/6;
			 	 
			 }else {
			 	 this.motionZ = -Math.cos(angle)/6;
			 	 
			 }
			 this.faceEntity(this.getAttackTarget(), 180, 180);
			 
			 isTargetInWater = this.getAttackTarget().isInWater();
			 facingBlock = this.world.getBlockState(this.getPosition().offset(this.getHorizontalFacing())).getBlock();
			 facingBlockUp = this.world.getBlockState(this.getPosition().offset(this.getHorizontalFacing()).add(0, 1, 0)).getBlock();
			 if((facingBlock != Blocks.WATER && facingBlock != Blocks.AIR) || (facingBlockUp != Blocks.WATER && facingBlockUp != Blocks.AIR)) {
				 this.motionY = 0.3;
			 	}
			 }
			 
			 
		 }
	 }
	 
	 
	 
	 
	 
	 
	 

}
