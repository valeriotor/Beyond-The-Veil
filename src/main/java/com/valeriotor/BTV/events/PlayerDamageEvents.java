package com.valeriotor.BTV.events;

import java.util.Map.Entry;
import java.util.UUID;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.EntityDeepOne;
import com.valeriotor.BTV.events.special.AzacnoParasiteEvents;
import com.valeriotor.BTV.events.special.DrowningRitualEvents;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.worship.AzacnoParasite;

import baubles.api.BaublesApi;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

@Mod.EventBusSubscriber
public class PlayerDamageEvents {
	
	@SubscribeEvent
	public static void hurtEvent(LivingHurtEvent event) {
		if(event.getEntityLiving() instanceof EntityPlayer) {
			if(event.getEntityLiving().world.isRemote) return;
			damageParasite(event);
			GreatDreamerBuffs.applyDefenseModifier(event);
			DrowningRitualEvents.preventDamage(event);
			dagonProtect(event);
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
	
	private static void dagonProtect(LivingHurtEvent event) {
		if(!(event.getSource().getTrueSource() instanceof EntityLiving)) return;
		EntityPlayer p = (EntityPlayer) event.getEntityLiving();
		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(p);
		
		if(k.isResearchKnown("ALLIANCE@0") && !k.isResearchComplete("wait")) {
			EntityDeepOne guardian = new EntityDeepOne(p.world, 500);
			guardian.setPosition(p.posX, p.posY, p.posZ);
			guardian.setAttackTarget((EntityLivingBase)event.getSource().getTrueSource());
			guardian.setMaster(p);
			p.world.spawnEntity(guardian);
			ThaumcraftApi.internalMethods.progressResearch(p, "wait");
		}
	}
	
	private static void damageParasite(LivingHurtEvent event) {
		if(event.getSource().isFireDamage()) {
			EntityPlayer p = (EntityPlayer) event.getEntityLiving();
			if(AzacnoParasiteEvents.parasites.containsKey(p.getPersistentID())) {
				AzacnoParasiteEvents.parasites.get(p.getPersistentID()).damageParasite((int)event.getAmount());
			}
		}
		
	}
	
}
