package com.valeriotor.BTV.entities;

import java.util.UUID;

import com.valeriotor.BTV.animations.Animation;
import com.valeriotor.BTV.animations.AnimationRegistry;
import com.valeriotor.BTV.entities.AI.AIProtectMaster;
import com.valeriotor.BTV.entities.AI.AIRevenge;
import com.valeriotor.BTV.entities.AI.AISpook;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBloodSkeleton extends EntityMob implements IPlayerGuardian, ISpooker{
	
	private int animCounter = -1;
	private int spookCooldown = 400;
	private Animation idleAnimation;
	private Animation spookAnimation;
	private UUID master;
	
	private static final DataParameter<Boolean> SPOOKING = EntityDataManager.<Boolean>createKey(EntityDeepOne.class, DataSerializers.BOOLEAN);
	
	public EntityBloodSkeleton(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SPOOKING, false);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);	
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(0D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);

		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8D);
	}
	
	protected void initEntityAI() {	 	
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new AISpook(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.2, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new AIProtectMaster(this));
        this.targetTasks.addTask(2, new AIRevenge(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 10, true, false,  p -> (this.master == null)));
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(world.isRemote) {
			animCounter--;
			if(this.dataManager.get(SPOOKING)) {
				if(this.spookAnimation == null) {
					this.spookAnimation = new Animation(AnimationRegistry.blood_skeleton_spook);
				}
			}
			
			if(animCounter > 0) {
				if(this.spookAnimation != null) {
					 if(this.spookAnimation.isDone()) this.spookAnimation = null;
					 else this.spookAnimation.update();
				 }
				if(this.idleAnimation != null) {
					this.idleAnimation.update();
					if(this.idleAnimation.isDone()) this.idleAnimation = null;
				}
			} else {
				if(this.animCounter == 0 && Math.abs(this.motionX) < 0.005 && Math.abs(this.motionZ) < 0.005 ) this.idleAnimation = new Animation(AnimationRegistry.blood_skeleton_idle);
				animCounter = world.rand.nextInt(15)*2 + 120;
			}
		} else {
			if(this.spookCooldown >= 0)
				this.spookCooldown--;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getAnimCounter() {
		return this.animCounter;
	}
	
	public Animation getIdleAnimation() {
		return this.idleAnimation;
	}
	
	public Animation getSpookAnimation() {
		return this.spookAnimation;
	}
	
	public void setMaster(EntityPlayer player) {
		if(player != null)
			this.master = player.getPersistentID();
	}

	@Override
	public EntityPlayer getMaster() {
		return world.getMinecraftServer().getPlayerList().getPlayerByUUID(master);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if(this.master != null) compound.setString("master", master.toString());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("master")) this.master = UUID.fromString(compound.getString("master"));
		super.readFromNBT(compound);
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public UUID getMasterID() {
		return this.master;
	}

	@Override
	public void setSpooking(boolean spook) {
		this.dataManager.set(SPOOKING, spook);
		this.spookCooldown = 400;
	}

	@Override
	public SoundEvent getSound() {
		return null;
	}

	@Override
	public void spookSelf() {		
	}

	@Override
	public int getSpookCooldown() {
		return this.spookCooldown;
	}

}
