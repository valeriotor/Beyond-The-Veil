package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.lib.References;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class RegisterEntities {
	public static void register() {
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":deep_one"), EntityDeepOne.class, "deep_one", 101 , BeyondTheVeil.instance, 64, 1, true, 0xF52A35, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":hamlet_dweller"), EntityHamletDweller.class, "hamlet_dweller", 102 , BeyondTheVeil.instance, 128, 1, true, 0xF52A37, 0x589BCD);
		
	}
}
