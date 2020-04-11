package com.valeriotor.beyondtheveil.events;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.worship.DGWorshipHelper;
import com.valeriotor.beyondtheveil.worship.Deities;
import com.valeriotor.beyondtheveil.worship.Worship;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class GreatDreamerBuffs {
	
	public static void applyAttackModifier(LivingHurtEvent e) {
		EntityPlayer p = (EntityPlayer) e.getSource().getTrueSource();
		if(Worship.getSelectedDeity(p) != Deities.GREATDREAMER) return;
		
		double modifier = DGWorshipHelper.getAttackModifier(p);
		if(p.isInWater()) {
			e.setAmount((float) (e.getAmount()*modifier*modifier));
			if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) e.setAmount((float)(e.getAmount()*modifier));
		}
		else e.setAmount((float) (e.getAmount()*modifier));
	}
	
	public static void applyDefenseModifier(LivingHurtEvent e) {
		EntityPlayer p = (EntityPlayer) e.getEntityLiving();
		if(Worship.getSelectedDeity(p) != Deities.GREATDREAMER) return;
		
		double modifier = DGWorshipHelper.getDefenseModifier(p);
		if(p.isInWater()) e.setAmount((float) (e.getAmount()*modifier*modifier));
		else e.setAmount((float) (e.getAmount()*modifier));

		if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) 
			e.setAmount((float) (e.getAmount()*modifier));
		
	}
	
	public static void applyDamageCap(LivingDamageEvent e) {
		EntityPlayer p = (EntityPlayer) e.getEntityLiving();
		if(Worship.getSelectedDeity(p) != Deities.GREATDREAMER) return;
		if(!p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) return;
		
		e.setAmount(Math.min(e.getAmount(), p.isInWater() ? 3 : 5));
		
	}
	
	public static boolean denyFall(LivingAttackEvent event) {
		EntityPlayer p = (EntityPlayer) event.getEntityLiving();
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		if(event.getSource() == DamageSource.FALL && 
				(data.getString(PlayerDataLib.TRANSFORMED) || data.getString(PlayerDataLib.DREAMFOCUS))) {
			event.setCanceled(true);
			return true;
		}
		return false;
	}
	
}
