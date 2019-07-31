package com.valeriotor.BTV.potions;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionRegistry {
	
	public static PotionFolly folly = new PotionFolly(true, 0);
	public static PotionTerror terror = new PotionTerror(true, 1316095);
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Potion> event) {
		event.getRegistry().registerAll(folly, terror);
	}
	
}
