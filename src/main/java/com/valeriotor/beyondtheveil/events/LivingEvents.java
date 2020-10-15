package com.valeriotor.beyondtheveil.events;

import java.util.List;

import com.valeriotor.beyondtheveil.entities.BTVEntityRegistry;
import com.valeriotor.beyondtheveil.entities.EntityShoggoth;
import com.valeriotor.beyondtheveil.entities.IDamageCapper;
import com.valeriotor.beyondtheveil.entities.IPlayerGuardian;
import com.valeriotor.beyondtheveil.events.special.AzacnoParasiteEvents;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.potions.PotionHeartbreak;
import com.valeriotor.beyondtheveil.potions.PotionRegistry;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class LivingEvents {
	
	private static final boolean DEBUG = false;
	
	@SubscribeEvent
	public static void livingHurt(LivingHurtEvent e) {
		if(e.getSource() instanceof EntityDamageSource) {
			if(e.getSource().getTrueSource() instanceof EntityPlayer) {
				GreatDreamerBuffs.applyAttackModifier(e);
				commandMinions(e);
				AzacnoParasiteEvents.damageEntity(e);
				ItemRegistry.coral_staff.commandUndead(e);
			}
		}
		e.setCanceled(cancelDamage(e));
	}
	
	public static boolean cancelDamage(LivingHurtEvent e) {
		EntityLivingBase ent = e.getEntityLiving();
		DamageSource d = e.getSource();
		if(ent instanceof EntityShoggoth) {
			if( d == DamageSource.IN_WALL || 
				d == DamageSource.CRAMMING) return true;
		} 
		if(!DEBUG && ent instanceof IDamageCapper) {
			capDamage(e, ((IDamageCapper)ent).getMaxDamage());
		}
		
		return false;
	}
	
	private static void capDamage(LivingHurtEvent e, float amount) {
		if(e.getAmount() > amount)
			e.setAmount(amount);
	}
	
	public static void commandMinions(LivingHurtEvent e) {
		EntityPlayer p = (EntityPlayer) e.getSource().getTrueSource();
		List<EntityLiving> minions = p.world.getEntities(EntityLiving.class, ent -> ent instanceof IPlayerGuardian && p.getPersistentID().equals(((IPlayerGuardian)ent).getMasterID()));
		for(EntityLiving ent : minions) {
			((IPlayerGuardian)ent).setTarget(e.getEntityLiving());
		}
	}
	
	@SubscribeEvent
	public static void setAttackTargetEvent(LivingSetAttackTargetEvent event) {
		EntityLivingBase eb = event.getEntityLiving();
		if(eb.world.isRemote) return;
		if(event.getTarget() == null) return;
		if(!(eb instanceof EntityLiving)) return;
		EntityLiving e = (EntityLiving)eb;
		if(e.getActivePotionEffect(PotionRegistry.folly) != null && !BTVEntityRegistry.isFearlessEntity(e)) {
			e.setAttackTarget(null);
		} else if(ServerTickEvents.isHearted(e.getEntityId())) {
			e.setAttackTarget(null);
		}
	}
	
	@SubscribeEvent
	public static void deathEvent(LivingDeathEvent event) {
		if(event.getSource().getTrueSource() instanceof EntitySkeleton) {
			((EntitySkeleton)event.getSource().getTrueSource()).setAttackTarget(null);
		}
	}
	
	@SubscribeEvent
	public static void healEvent(LivingHealEvent event) {
		EntityLivingBase e = event.getEntityLiving();
		if(e.isPotionActive(PotionRegistry.heartbreak)) {
			PotionEffect f = e.getActivePotionEffect(PotionRegistry.heartbreak);
			float maxHp = PotionHeartbreak.getMaxHp(e, f.getAmplifier());
			if(e.getHealth()+event.getAmount() > maxHp) {
				event.setAmount(maxHp-e.getHealth());
			}
		}
	}
}
