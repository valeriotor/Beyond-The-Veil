package com.valeriotor.BTV.entities;

import java.util.UUID;

import com.valeriotor.BTV.animations.Animation;
import com.valeriotor.BTV.animations.AnimationRegistry;
import com.valeriotor.BTV.entities.AI.AIProtectMaster;
import com.valeriotor.BTV.entities.AI.AIRevenge;

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
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBloodZombie extends EntityMob implements IPlayerGuardian{
	
	
	private Animation idle_animation;
	private int animCounter = -1;
	private UUID master;
	
	public EntityBloodZombie(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);	
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(3D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);

		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10D);
	}
	
	protected void initEntityAI() {	 	
        this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIAttackMelee(this, 1.4, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.targetTasks.addTask(1, new AIProtectMaster(this));
        this.targetTasks.addTask(2, new AIRevenge(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 10, true, false,  p -> (this.master == null)));
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(world.isRemote) {
			if(animCounter > 0) {
				animCounter--;
				if(this.idle_animation != null) {
					this.idle_animation.update();
					if(this.idle_animation.isDone()) this.idle_animation = null;
				}	
			} else {
				if(this.animCounter == 0 && Math.abs(this.motionX) < 0.005 && Math.abs(this.motionZ) < 0.005 ) this.idle_animation = new Animation(AnimationRegistry.blood_zombie_idle);
				animCounter = world.rand.nextInt(15)*200 + 800;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getAnimCounter() {
		return this.animCounter;
	}
	
	@SideOnly(Side.CLIENT)
	public Animation getIdleAnimation() {
		return this.idle_animation;
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

}
