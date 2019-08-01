package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class BTVEntityRegistry {
	public static void register() {
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":deep_one"), EntityDeepOne.class, "deep_one", 101 , BeyondTheVeil.instance, 64, 1, true, 0xF52A35, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":hamlet_dweller"), EntityHamletDweller.class, "hamlet_dweller", 102 , BeyondTheVeil.instance, 128, 1, true, 0xF52A37, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":canoe"), EntityCanoe.class, "canoe", 103 , BeyondTheVeil.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":crawling_villager"), EntityCrawlingVillager.class, "crawling_villager", 105 , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":weeper"), EntityWeeper.class, "weeper", 106 , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":fletum"), EntityFletum.class, "fletum", 107 , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		
	}
	
	
	/** Used for terror and possibly other effects.
	 * 
	 * @param e
	 * @return
	 */
	public static boolean isScaryEntity(EntityLivingBase e) {
		if(e instanceof EntityPlayer && ((EntityPlayer)e).getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) return true;
		return e instanceof EntityDeepOne;
	}
	
	/** Used for terror and possibly other effects.
	 * 
	 * @param e
	 * @return
	 */
	public static boolean isFearlessEntity(EntityLivingBase e) {
		return e instanceof EntityDeepOne || e instanceof EntityWeeper || e instanceof EntityFletum ;
	}
	
	
}
