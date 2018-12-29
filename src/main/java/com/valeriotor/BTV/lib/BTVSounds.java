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
	
	public static SoundEvent getSoundById(int id) {
		switch(id) {
			case 0: return dwellerIdle;
			case 1: return dwellerHurt;
			case 2: return canoeCreak;
			case 3: return flute;
			case 4: return sawcleavertransform;
			case 5: return sawcleavertransformattack;
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
		return 0;
	}
}
