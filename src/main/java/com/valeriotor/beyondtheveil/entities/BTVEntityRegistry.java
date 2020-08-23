package com.valeriotor.beyondtheveil.entities;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamFluid;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamItem;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamVillager;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
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
		register("dreadfish", EntityDreadfish.class, count++ , 64);
		
	}
	
	private static void register(String name, Class<? extends Entity> entClass, int id, int trackingRange) {
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":" + name), entClass, name, id, BeyondTheVeil.instance, trackingRange, 1, true, 0xF52A37, 0x589BCD);
	}
	
	private static void registerNoEgg(String name, Class<? extends Entity> entClass, int id, int trackingRange) {
		EntityRegistry.registerModEntity(new ResourceLocation(References.MODID + ":" + name), entClass, name, id, BeyondTheVeil.instance, trackingRange, 1, true);
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
