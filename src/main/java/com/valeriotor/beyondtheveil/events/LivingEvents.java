package com.valeriotor.beyondtheveil.events;

import java.util.List;

import com.valeriotor.beyondtheveil.entities.BTVEntityRegistry;
import com.valeriotor.beyondtheveil.entities.EntityFletum;
import com.valeriotor.beyondtheveil.entities.EntityShoggoth;
import com.valeriotor.beyondtheveil.entities.IPlayerGuardian;
import com.valeriotor.beyondtheveil.events.special.AzacnoParasiteEvents;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.potions.PotionRegistry;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class LivingEvents {
	
	private static final boolean DEBUG = true;
	
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
	
	@SuppressWarnings("unused")
	public static boolean cancelDamage(LivingHurtEvent e) {
		EntityLivingBase ent = e.getEntityLiving();
		DamageSource d = e.getSource();
		if(ent instanceof EntityShoggoth) {
			if( d == DamageSource.IN_WALL || 
				d == DamageSource.CRAMMING) return true;
			if(e.getAmount() > 8 && !DEBUG) e.setAmount(8);
		}
		
		return false;
	}
	
	public static void commandMinions(LivingHurtEvent e) {
		EntityPlayer p = (EntityPlayer) e.getSource().getTrueSource();
		List<EntityLiving> minions = p.world.getEntities(EntityLiving.class, ent -> ent instanceof IPlayerGuardian && ((IPlayerGuardian)ent).getMasterID() == p.getPersistentID());
		for(EntityLiving ent : minions) {
			((IPlayerGuardian)ent).setTarget(e.getEntityLiving());
		}
	}
	
	@SubscribeEvent
	public static void setAttackTargetEvent(LivingSetAttackTargetEvent event) {
		EntityLivingBase eb = event.getEntityLiving();
		if(eb instanceof EntityLiving && eb.getActivePotionEffect(PotionRegistry.folly) != null && !BTVEntityRegistry.isFearlessEntity(eb)
				&& event.getTarget() != null) {
			((EntityLiving)eb).setAttackTarget(null);
		}
	}
	
}
