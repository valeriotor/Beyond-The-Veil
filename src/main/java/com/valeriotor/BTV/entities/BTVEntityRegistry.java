package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class BTVEntityRegistry {
	public static void register() {
		int count = 101;
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":deep_one"), EntityDeepOne.class, "deep_one", count++ , BeyondTheVeil.instance, 64, 1, true, 0xF52A35, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":hamlet_dweller"), EntityHamletDweller.class, "hamlet_dweller", count++ , BeyondTheVeil.instance, 128, 1, true, 0xF52A37, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":canoe"), EntityCanoe.class, "canoe", count++ , BeyondTheVeil.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":crawling_villager"), EntityCrawlingVillager.class, "crawling_villager", count++ , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":weeper"), EntityWeeper.class, "weeper", count++ , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":fletum"), EntityFletum.class, "fletum", count++ , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":starspawn"), EntityStarspawn.class, "starspawn", count++ , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":shoggoth"), EntityShoggoth.class, "shoggoth", count++ , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		
	}
	
	
	/** Used for terror and possibly other effects.
	 * 
	 * @param e
	 * @return
	 */
	public static boolean isScaryEntity(EntityLivingBase e) {
		if(e instanceof EntityPlayer && ((EntityPlayer)e).getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) return true;
		return e instanceof EntityDeepOne || e instanceof EntityStarspawn || e instanceof EntityShoggoth;
	}
	
	/** Used for terror and possibly other effects.
	 * 
	 * @param e
	 * @return
	 */
	public static boolean isFearlessEntity(EntityLivingBase e) {
		return  !e.isNonBoss() ||e instanceof EntityDeepOne || e instanceof EntityWeeper || e instanceof EntityFletum || e instanceof EntityStarspawn || e instanceof EntityShoggoth;
	}
	
	
	public static boolean isHostileEntity(EntityLivingBase ent) {
		if(ent.isCreatureType(EnumCreatureType.MONSTER, false)) return true;
		if(ent instanceof EntityLiving) {
			EntityLiving e = (EntityLiving) ent;
			if(!e.targetTasks.taskEntries.isEmpty()) return true;
		}
		return false;
	}
	
	
}
