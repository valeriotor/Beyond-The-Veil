package com.valeriotor.BTV.events;

import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntityDamageSource;
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
				EntityPlayer p = (EntityPlayer) e.getSource().getTrueSource();
				int lvl = Deities.GREATDREAMER.cap(p).getLevel();
				if(lvl >= 3) {
					if(p.isInWater()) e.setAmount((float) (e.getAmount()*2*Math.log(lvl)));
					else e.setAmount((float) (e.getAmount()*1.1*Math.log(lvl)));
				}
			}
		}
	}
	
}
