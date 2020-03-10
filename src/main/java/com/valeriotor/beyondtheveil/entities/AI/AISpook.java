package com.valeriotor.beyondtheveil.entities.AI;

import com.valeriotor.beyondtheveil.entities.BTVEntityRegistry;
import com.valeriotor.beyondtheveil.entities.IPlayerGuardian;
import com.valeriotor.beyondtheveil.entities.ISpooker;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessagePlaySound;
import com.valeriotor.beyondtheveil.potions.PotionRegistry;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;

public class AISpook extends EntityAIBase{
	
	public final ISpooker entity;
	private int roarTime = 0;
	
	public AISpook(ISpooker e) {
		this.entity = e;
	}
	
	
	@Override
	public boolean shouldExecute() {
		return entity.shouldSpook();
	}
	
	@Override
	public void startExecuting() {
		this.entity.spookSelf();
		this.entity.setSpooking(true);
		this.roarTime = 30;
		SoundEvent sound = this.entity.getSound();
		entity.getWorld().getEntities(EntityLivingBase.class, e -> !BTVEntityRegistry.isFearlessEntity(e) && this.entity.getDistance(e) < 25)
						 .forEach(e -> {
						 if(!(entity instanceof IPlayerGuardian) || !e.equals(((IPlayerGuardian)entity).getMaster())) {
							 e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 10));
							 e.addPotionEffect(new PotionEffect(PotionRegistry.terror, 20 * 6, 0));
						 }
						 if(e instanceof EntityPlayerMP && sound != null) {
							 BTVPacketHandler.INSTANCE.sendTo(new MessagePlaySound(BTVSounds.getIdBySound(sound), e.getPosition().toLong()), (EntityPlayerMP)e);
						 }
						 });
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		this.entity.spookSelf();
		this.roarTime--;
		if(this.roarTime < 1) {
			this.entity.setSpooking(false);
			if(this.entity instanceof EntityLiving) ((EntityLiving)this.entity).moveForward = 15;
			return false;
		}
		return true;
	}
	
	
	

}
