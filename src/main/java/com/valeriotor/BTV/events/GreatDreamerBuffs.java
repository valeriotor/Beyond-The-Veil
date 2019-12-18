package com.valeriotor.BTV.events;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Deities;
import com.valeriotor.BTV.worship.Worship;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class GreatDreamerBuffs {
	
	public static void applyAttackModifier(LivingHurtEvent e) {
		EntityPlayer p = (EntityPlayer) e.getSource().getTrueSource();
		if(Worship.getSelectedDeity(p) != Deities.GREATDREAMER) return;
		
		double modifier = DGWorshipHelper.getAttackModifier(p);
		if(p.isInWater()) e.setAmount((float) (e.getAmount()*modifier*modifier));
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
		
		e.setAmount(Math.min(e.getAmount(), 5));
		
	}
	
}
