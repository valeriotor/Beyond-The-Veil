package com.valeriotor.BTV.entities.AI;

import com.valeriotor.BTV.entities.EntityDeepOne;
import com.valeriotor.BTV.lib.BTVSounds;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessagePlaySound;
import com.valeriotor.BTV.potions.PotionRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;

public class AIDeepOneRoar extends EntityAIBase{
	
	public final EntityDeepOne entity;
	private int roarTime = 0;
	
	public AIDeepOneRoar(EntityDeepOne e) {
		this.entity = e;
	}
	
	
	@Override
	public boolean shouldExecute() {
		if(this.entity.getAttackTarget() == null) return false;
		if(this.entity.getRoarCooldown() > 0) return false;
		float dist = this.entity.getAttackTarget().getDistance(this.entity);
		if(dist < 3 || dist > 20) return false;
		return true;
	}
	
	@Override
	public void startExecuting() {
		this.entity.faceEntity(this.entity.getAttackTarget(), 360, 360);
		this.entity.rotationYawHead = 0;
		this.entity.setRoaring(true);
		this.roarTime = 30;
		this.entity.world.getEntities(EntityLivingBase.class, e -> !(e instanceof EntityDeepOne) && e.getDistance(this.entity) < 25)
						 .forEach(e -> {
						 e.addPotionEffect(new PotionEffect(PotionRegistry.terror, 120, 2));
						 if(e instanceof EntityPlayerMP) {
							 BTVPacketHandler.INSTANCE.sendTo(new MessagePlaySound(BTVSounds.getIdBySound(BTVSounds.deepOneRoar), e.getPosition().toLong()), (EntityPlayerMP)e);
						 }
						 });
		this.entity.world.playSound(this.entity.posX, this.entity.posY, this.entity.posZ, BTVSounds.deepOneRoar, SoundCategory.HOSTILE, 1, 1, false);
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		this.entity.motionX = 0;
		this.entity.motionZ = 0;
		this.entity.faceEntity(this.entity.getAttackTarget(), 360, 360);
		this.roarTime--;
		if(this.roarTime < 1) {
			this.entity.setRoaring(false);
			this.entity.moveForward = 15;
			return false;
		}
		return true;
	}
	
	
	

}
