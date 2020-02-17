package com.valeriotor.BTV.lib;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;


@ObjectHolder(References.MODID)
public final class BTVSounds {
	
	@ObjectHolder("dweller_idle")
	public static final SoundEvent dwellerIdle = new SoundEvent(new ResourceLocation(References.MODID, "dweller_idle")).setRegistryName("dweller_idle");
	
	@ObjectHolder("dweller_hurt")
	public static final SoundEvent dwellerHurt = new SoundEvent(new ResourceLocation(References.MODID, "dweller_hurt")).setRegistryName("dweller_hurt");
	
	@ObjectHolder("canoe_creak")
	public static final SoundEvent canoeCreak = new SoundEvent(new ResourceLocation(References.MODID, "canoe_creak")).setRegistryName("canoe_creak");
	
	@ObjectHolder("flute")
	public static final SoundEvent flute = new SoundEvent(new ResourceLocation(References.MODID, "flute")).setRegistryName("flute");
	
	@ObjectHolder("sawcleavertransform")
	public static final SoundEvent sawcleavertransform = new SoundEvent(new ResourceLocation(References.MODID, "sawcleavertransform")).setRegistryName("sawcleavertransform");
	
	@ObjectHolder("sawcleavertransformattack")
	public static final SoundEvent sawcleavertransformattack = new SoundEvent(new ResourceLocation(References.MODID, "sawcleavertransformattack")).setRegistryName("sawcleavertransformattack");
	
	@ObjectHolder("dream_alienis")
	public static final SoundEvent dreamAlienis = new SoundEvent(new ResourceLocation(References.MODID, "dream_alienis")).setRegistryName("dream_alienis");
	
	@ObjectHolder("spine_rip")
	public static final SoundEvent spineRip = new SoundEvent(new ResourceLocation(References.MODID, "spine_rip")).setRegistryName("spine_rip");
	
	@ObjectHolder("heart_rip")
	public static final SoundEvent heartRip = new SoundEvent(new ResourceLocation(References.MODID, "heart_rip")).setRegistryName("heart_rip");
	
	@ObjectHolder("deep_one_roar")
	public static final SoundEvent deepOneRoar = new SoundEvent(new ResourceLocation(References.MODID, "deep_one_roar")).setRegistryName("deep_one_roar");
	
	@ObjectHolder("worthless")
	public static final SoundEvent worthless = new SoundEvent(new ResourceLocation(References.MODID, "worthless")).setRegistryName("worthless");
	
	@ObjectHolder("tension")
	public static final SoundEvent tension = new SoundEvent(new ResourceLocation(References.MODID, "tension")).setRegistryName("tension");
	
	@ObjectHolder("breathe")
	public static final SoundEvent breathe = new SoundEvent(new ResourceLocation(References.MODID, "breathe")).setRegistryName("breathe");
	
	@ObjectHolder("dagon_thump")
	public static final SoundEvent dagonThump = new SoundEvent(new ResourceLocation(References.MODID, "dagon_thump")).setRegistryName("dagon_thump");

	@ObjectHolder("dagon_tension")
	public static final SoundEvent dagonTension = new SoundEvent(new ResourceLocation(References.MODID, "dagon_tension")).setRegistryName("dagon_tension");
	
	@ObjectHolder("parasite_hurt")
	public static final SoundEvent parasiteHurt = new SoundEvent(new ResourceLocation(References.MODID, "parasite_hurt")).setRegistryName("parasite_hurt");
	
	@ObjectHolder("parasite_death")
	public static final SoundEvent parasiteDeath = new SoundEvent(new ResourceLocation(References.MODID, "parasite_death")).setRegistryName("parasite_death");

	@ObjectHolder("heartbeat")
	public static final SoundEvent heartbeat = new SoundEvent(new ResourceLocation(References.MODID, "heartbeat")).setRegistryName("heartbeat");
	
	@ObjectHolder("shoggoth_screech")
	public static final SoundEvent shoggoth_screech = new SoundEvent(new ResourceLocation(References.MODID, "shoggoth_screech")).setRegistryName("shoggoth_screech");
	
	@ObjectHolder("deep_one_transform")
	public static final SoundEvent deep_one_transform = new SoundEvent(new ResourceLocation(References.MODID, "deep_one_transform")).setRegistryName("deep_one_transform");
	
	@ObjectHolder("weeper_idle")
	public static final SoundEvent weeper_idle = new SoundEvent(new ResourceLocation(References.MODID, "weeper_idle")).setRegistryName("weeper_idle");
	
	@ObjectHolder("shoggoth_hurt")
	public static final SoundEvent shoggoth_hurt = new SoundEvent(new ResourceLocation(References.MODID, "shoggoth_hurt")).setRegistryName("shoggoth_hurt");
	
	@ObjectHolder("weeper_transform")
	public static final SoundEvent weeper_transform = new SoundEvent(new ResourceLocation(References.MODID, "weeper_transform")).setRegistryName("weeper_transform");
	
	public static SoundEvent getSoundById(int id) {
		switch(id) {
			case 0: return dwellerIdle;
			case 1: return dwellerHurt;
			case 2: return canoeCreak;
			case 3: return flute;
			case 4: return sawcleavertransform;
			case 5: return sawcleavertransformattack;
			case 6: return dreamAlienis;
			case 7: return spineRip;
			case 8: return heartRip;
			case 9: return deepOneRoar;
			case 10: return worthless;
			case 11: return tension;
			case 12: return breathe;
			case 13: return dagonThump;
			case 14: return dagonTension;
			case 15: return parasiteHurt;
			case 16: return parasiteDeath;
			case 17: return heartbeat;
			case 18: return shoggoth_screech;
			case 19: return deep_one_transform;
			case 20: return weeper_idle;
			case 21: return shoggoth_hurt;
			case 22: return weeper_transform;
			default: return null;
			
		}
	}
	
	public static int getIdBySound(SoundEvent sound) {
		if(sound.equals(dwellerIdle)) return 0;
		if(sound.equals(dwellerHurt)) return 1;
		if(sound.equals(canoeCreak)) return 2;
		if(sound.equals(flute)) return 3;
		if(sound.equals(sawcleavertransform)) return 4;
		if(sound.equals(sawcleavertransformattack)) return 5;
		if(sound.equals(dreamAlienis)) return 6;
		if(sound.equals(spineRip)) return 7;
		if(sound.equals(heartRip)) return 8;
		if(sound.equals(deepOneRoar)) return 9;
		if(sound.equals(worthless)) return 10;
		if(sound.equals(tension)) return 11;
		if(sound.equals(breathe)) return 12;
		if(sound.equals(dagonThump)) return 13;
		if(sound.equals(dagonTension)) return 14;
		if(sound.equals(parasiteHurt)) return 15;
		if(sound.equals(parasiteDeath)) return 16;
		if(sound.equals(heartbeat)) return 17;
		if(sound.equals(shoggoth_screech)) return 18;
		if(sound.equals(deep_one_transform)) return 19;
		if(sound.equals(weeper_idle)) return 20;
		if(sound.equals(shoggoth_hurt)) return 21;
		if(sound.equals(weeper_transform)) return 22;
		return 0;
	}
	
	public static int getNumberOfSounds() {
		return 23;
	}
}
