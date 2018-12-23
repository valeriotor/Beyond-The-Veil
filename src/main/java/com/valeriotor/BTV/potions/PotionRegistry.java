package com.valeriotor.BTV.potions;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionRegistry {
	
	public static Folly folly = new Folly(true, 0);
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Potion> event) {
		event.getRegistry().registerAll(folly);
	}
	
}
