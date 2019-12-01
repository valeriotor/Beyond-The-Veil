package com.valeriotor.BTV.entities;

import java.util.UUID;

import com.valeriotor.BTV.animations.Animation;
import com.valeriotor.BTV.animations.AnimationRegistry;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10D);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(3D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);			
	}
	
	@Override
	public void onUpdate() {
		if(world.isRemote) {
			if(animCounter > 0) {
				animCounter--;
				if(this.idle_animation != null) {
					this.idle_animation.update();
					if(this.idle_animation.isDone()) this.idle_animation = null;
				}	
			} else {
				if(this.animCounter == 0) this.idle_animation = new Animation(AnimationRegistry.blood_zombie_idle);
				animCounter = world.rand.nextInt(3000) + 800;
			}
		}
		super.onUpdate();
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

}
