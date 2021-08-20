package com.valeriotor.beyondtheveil.util;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigLib {
	
	public static int connectionRed, connectionGreen, connectionBlue, innsmouthWeight, crucibleDamage, crucibleCooldown, archeId;
	
	@Mod.EventBusSubscriber
	public static class EventHandlerConfig {
		
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent event) {
			if (event.getModID().equals(References.MODID)) {
				BeyondTheVeil.proxy.syncConfig();
			}
		}
	}
}
