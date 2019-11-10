package com.valeriotor.BTV.events.special;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.valeriotor.BTV.events.ServerTickEvents;
import com.valeriotor.BTV.worship.AzacnoParasite;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class AzacnoParasiteEvents {

	public static final HashMap<EntityPlayer, AzacnoParasite> parasites = new HashMap<>();
	
	
	public static void updateParasites() {
		Iterator<Entry<EntityPlayer, AzacnoParasite>> iter = parasites.entrySet().iterator();
		while(iter.hasNext()) {
			if(iter.next().getValue().update())
				iter.remove();
		}
	}
	
	public static void damageEntity(LivingHurtEvent e) {
		EntityPlayer attacker = (EntityPlayer) e.getSource().getTrueSource();
		if(ServerTickEvents.getPlayerTimer("azacno", attacker) == null) return;

		if(e.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer damaged = (EntityPlayer) e.getEntityLiving();
			if(!parasites.containsKey(damaged))
				parasites.put(damaged, new AzacnoParasite(damaged));
		} else {
			
		}
	}
	
}
