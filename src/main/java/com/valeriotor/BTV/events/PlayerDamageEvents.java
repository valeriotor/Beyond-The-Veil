package com.valeriotor.BTV.events;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.PlayerDataLib;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PlayerDamageEvents {
	
	@SubscribeEvent
	public static void hurtEvent(LivingHurtEvent event) {
		if(event.getEntityLiving() instanceof EntityPlayer) {
			if(event.getEntityLiving().world.isRemote) return;
			GreatDreamerBuffs.applyDefenseModifier(event);
		}
	}	

	@SubscribeEvent
	public static void damageEvent(LivingDamageEvent event) {
		if(event.getEntityLiving() instanceof EntityPlayer) {
			if(event.getEntityLiving().world.isRemote) return;
			resetFlute(event);
			GreatDreamerBuffs.applyDamageCap(event);
			applyBleedingBelt(event);
		}
	}
	
	private static void resetFlute(LivingDamageEvent event) {
		EntityPlayer p = (EntityPlayer)event.getEntityLiving();
		ItemStack stack = null;
		if(p.getHeldItemMainhand().getItem() == ItemRegistry.flute) {
			stack = p.getHeldItemMainhand();
		}else if(p.getHeldItemOffhand().getItem() == ItemRegistry.flute){
			stack = p.getHeldItemOffhand();
		}
		if(stack != null) {
			if(p.getItemInUseCount() > 0)
			stack.setItemDamage(150);
		}
	}
	
	private static void applyBleedingBelt(LivingDamageEvent event) {
		EntityPlayer p = (EntityPlayer)event.getEntityLiving();
		if(BaublesApi.getBaublesHandler(p).getStackInSlot(3).getItem() == ItemRegistry.bleeding_belt &&
			p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(String.format(PlayerDataLib.PASSIVE_BAUBLE, 3), 1, false) == 1	) {
			float damage = event.getAmount();
			int food = p.getFoodStats().getFoodLevel();
			if(damage < food) {
				p.getFoodStats().setFoodLevel((int)(food - damage));
				event.setAmount(0);
			}else {
				p.getFoodStats().setFoodLevel(0);
				event.setAmount(damage - food);
			}
		}
	}
	
}
