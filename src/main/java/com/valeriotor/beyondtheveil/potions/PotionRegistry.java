package com.valeriotor.beyondtheveil.potions;

import net.minecraft.potion.Potion;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionRegistry {
	
	public static Potion folly = new PotionFolly(true, 0);
	public static Potion terror = new PotionTerror(true, 1316095).setPotionName("terror");
	public static Potion heartbreak = new PotionHeartbreak(true, 0xFFBB3333).setPotionName("heartbreak");
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Potion> event) {
		event.getRegistry().registerAll(folly, terror, heartbreak);
		folly.setPotionName(new TextComponentTranslation("effect.folly").getFormattedText());
		terror.setPotionName(new TextComponentTranslation("effect.terror").getFormattedText());
		heartbreak.setPotionName(new TextComponentTranslation("effect.heartbreak").getFormattedText());
	}
	
}
