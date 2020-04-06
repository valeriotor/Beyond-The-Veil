package com.valeriotor.beyondtheveil.entities;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;

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
		//EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":starspawn"), EntityStarspawn.class, "starspawn", count++ , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":shoggoth"), EntityShoggoth.class, "shoggoth", count++ , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":blood_zombie"), EntityBloodZombie.class, "blood_zombie", count++ , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":blood_skeleton"), EntityBloodSkeleton.class, "blood_skeleton", count++ , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":crazed_weeper"), EntityCrazedWeeper.class, "crazed_weeper", count++ , BeyondTheVeil.instance, 64, 1, true, 0xF52A37, 0x589BCD);
		
	}
	
	
	/** Used for terror and possibly other effects.
	 * 
	 * @param e
	 * @return
	 */
	public static boolean isScaryEntity(EntityLivingBase e) {
		if(e instanceof EntityPlayer && ((EntityPlayer)e).getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) return true;
		return e instanceof EntityDeepOne || e instanceof EntityStarspawn || e instanceof EntityShoggoth || e instanceof EntityBloodZombie || e instanceof EntityBloodSkeleton || e instanceof EntityCrazedWeeper;
	}
	
	/** Used for terror and possibly other effects.
	 * 
	 * @param e
	 * @return
	 */
	public static boolean isFearlessEntity(EntityLivingBase e) {
		return  !e.isNonBoss() ||e instanceof EntityDeepOne || e instanceof EntityWeeper || e instanceof EntityFletum || e instanceof EntityStarspawn || e instanceof EntityShoggoth || e instanceof EntityBloodZombie || e instanceof EntityCrazedWeeper;
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
