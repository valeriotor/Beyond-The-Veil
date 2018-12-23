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
	
}
