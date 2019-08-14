package com.valeriotor.BTV.events;

import com.valeriotor.BTV.entities.EntityFletum;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class LivingEvents {
	
	@SubscribeEvent
	public static void livingHurt(LivingHurtEvent e) {
		if(e.getEntity().world.isRemote) return;
		if(e.getSource() instanceof EntityDamageSource) {
			if(e.getSource().getTrueSource() instanceof EntityPlayer) {
				GreatDreamerBuffs.applyAttackModifier(e);
			}
		}else if(e.getSource() == DamageSource.FALL) {
			if(e.getEntity() instanceof EntityFletum) {
				e.setCanceled(true);
			}
		}
	}
	
}
