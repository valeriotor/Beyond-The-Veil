package com.valeriotor.beyondtheveil.entities;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.entities.bosses.EntityDeepOneBrute;
import com.valeriotor.beyondtheveil.entities.bosses.EntityDeepOneMyrmidon;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamFluid;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamItem;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamVillager;
import com.valeriotor.beyondtheveil.entities.ictya.*;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class BTVEntityRegistry {
	public static void register() {
		int count = 101;
		register("deep_one", EntityDeepOne.class, count++ , 64);
		register("hamlet_dweller", EntityHamletDweller.class, count++ , 128);
		registerNoEgg("canoe", EntityCanoe.class, count++ , 64);
		register("crawling_villager", EntityCrawlingVillager.class, count++ , 64);
		register("weeper", EntityWeeper.class, count++ , 64);
		register("fletum", EntityFletum.class, count++ , 64);
		//register("starspawn"), EntityStarspawn.class, "starspawn", count++ , 64);
		register("shoggoth", EntityShoggoth.class, count++ , 64);
		register("blood_zombie", EntityBloodZombie.class, count++ , 64);
		register("blood_skeleton", EntityBloodSkeleton.class, count++ , 64);
		register("crazed_weeper", EntityCrazedWeeper.class, count++ , 64);
		registerNoEgg("dream_item", EntityDreamItem.class, count++ , 64);
		registerNoEgg("dream_fluid", EntityDreamFluid.class, count++ , 64);
		registerNoEgg("dream_villager", EntityDreamVillager.class, count++ , 64);
		register("surgeon", EntitySurgeon.class, count++ , 64);
		register("dreadfish", EntityDreadfish.class, count++ , 128);
		register("muray", EntityMuray.class, count++ , 128);
		register("octid", EntityOctid.class, count++ , 128);
		register("deep_angler", EntityDeepAngler.class, count++ , 128);
		register("sarfin", EntitySarfin.class, count++ , 128);
		register("man_o_war", EntityManOWar.class, count++ , 128);
		register("jelly", EntityJelly.class, count++ , 128);
		register("deep_one_brute", EntityDeepOneBrute.class, count++ , 128);
		register("deep_one_myrmidon", EntityDeepOneMyrmidon.class, count++ , 128);
		register("cephalopodian", EntityCephalopodian.class, count++, 128);
		register("sandflatter", EntitySandflatter.class, count++, 128);

		registerSpawnPlacementTypes();
		
	}
	
	private static void register(String name, Class<? extends Entity> entClass, int id, int trackingRange) {
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":" + name), entClass, name, id, BeyondTheVeil.instance, trackingRange, 1, true, 0xF52A37, 0x589BCD);
	}
	
	private static void registerNoEgg(String name, Class<? extends Entity> entClass, int id, int trackingRange) {
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":" + name), entClass, name, id, BeyondTheVeil.instance, trackingRange, 1, true);
	}
	
	private static void registerSpawnPlacementTypes() {
		EntitySpawnPlacementRegistry.setPlacementType(EntityOctid.class, SpawnPlacementType.IN_WATER);
		EntitySpawnPlacementRegistry.setPlacementType(EntityDreadfish.class, SpawnPlacementType.IN_WATER);
		EntitySpawnPlacementRegistry.setPlacementType(EntityDeepAngler.class, SpawnPlacementType.IN_WATER);
		EntitySpawnPlacementRegistry.setPlacementType(EntitySarfin.class, SpawnPlacementType.IN_WATER);
		EntitySpawnPlacementRegistry.setPlacementType(EntityMuray.class, SpawnPlacementType.IN_WATER);
		EntitySpawnPlacementRegistry.setPlacementType(EntityManOWar.class, SpawnPlacementType.IN_WATER);
		EntitySpawnPlacementRegistry.setPlacementType(EntityCephalopodian.class, SpawnPlacementType.IN_WATER);
		EntitySpawnPlacementRegistry.setPlacementType(EntitySandflatter.class, SpawnPlacementType.IN_WATER);
		EntitySpawnPlacementRegistry.setPlacementType(EntityDeepOne.class, SpawnPlacementType.IN_WATER);
		EntitySpawnPlacementRegistry.setPlacementType(EntityDeepOne.class, SpawnPlacementType.ON_GROUND);
	}
	
	
	/** Used for terror and possibly other effects.
	 *
	 */
	public static boolean isScaryEntity(EntityLivingBase e) {
		if(e instanceof EntityPlayer && ((EntityPlayer)e).getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) return true;
		return e instanceof EntityDeepOne || e instanceof EntityStarspawn || e instanceof EntityShoggoth || e instanceof EntityBloodZombie || e instanceof EntityBloodSkeleton || e instanceof EntityCrazedWeeper;
	}
	
	/** Used for terror and possibly other effects.
	 *
	 */
	public static boolean isFearlessEntity(EntityLivingBase e) {
		return  !e.isNonBoss() ||
				e instanceof EntityDeepOne || 
				e instanceof EntityWeeper || 
				e instanceof EntityFletum || 
				e instanceof EntityStarspawn || 
				e instanceof EntityShoggoth || 
				e instanceof EntityBloodZombie || 
				e instanceof EntityCrazedWeeper ||
				e instanceof EntityIctya;
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
