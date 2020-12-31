package com.valeriotor.beyondtheveil.lib;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import java.util.ArrayList;
import java.util.List;


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
	
	@ObjectHolder("fletum_weeping")
	public static final SoundEvent fletum_weeping = new SoundEvent(new ResourceLocation(References.MODID, "fletum_weeping")).setRegistryName("fletum_weeping");
	
	@ObjectHolder("shoggoth_idle")
	public static final SoundEvent shoggoth_idle = new SoundEvent(new ResourceLocation(References.MODID, "shoggoth_idle")).setRegistryName("shoggoth_idle");
	
	@ObjectHolder("surgeon_idle")
	public static final SoundEvent surgeon_idle = new SoundEvent(new ResourceLocation(References.MODID, "surgeon_idle")).setRegistryName("surgeon_idle");

	@ObjectHolder("deep_one_brute_smash")
	public static final SoundEvent deep_one_brute_smash = new SoundEvent(new ResourceLocation(References.MODID, "deep_one_brute_smash")).setRegistryName("deep_one_brute_smash");

	@ObjectHolder("deep_one_brute_roar")
	public static final SoundEvent deep_one_brute_roar = new SoundEvent(new ResourceLocation(References.MODID, "deep_one_brute_roar")).setRegistryName("deep_one_brute_roar");

	private static final List<SoundEvent> SOUNDS;

	static {
		List<SoundEvent> sounds = new ArrayList<>();
		sounds.add(dwellerIdle);
		sounds.add(dwellerHurt);
		sounds.add(canoeCreak);
		sounds.add(flute);
		sounds.add(sawcleavertransform);
		sounds.add(sawcleavertransformattack);
		sounds.add(dreamAlienis);
		sounds.add(spineRip);
		sounds.add(heartRip);
		sounds.add(deepOneRoar);
		sounds.add(worthless);
		sounds.add(tension);
		sounds.add(breathe);
		sounds.add(dagonThump);
		sounds.add(dagonTension);
		sounds.add(parasiteHurt);
		sounds.add(parasiteDeath);
		sounds.add(heartbeat);
		sounds.add(shoggoth_screech);
		sounds.add(deep_one_transform);
		sounds.add(weeper_idle);
		sounds.add(shoggoth_hurt);
		sounds.add(weeper_transform);
		sounds.add(fletum_weeping);
		sounds.add(shoggoth_idle);
		sounds.add(surgeon_idle);
		sounds.add(deep_one_brute_smash);
		sounds.add(deep_one_brute_roar);
		SOUNDS = ImmutableList.copyOf(sounds);
	}

	public static SoundEvent getSoundById(int id) {
		if(id >= 0 && id < SOUNDS.size())
			return SOUNDS.get(id);
		return null;
	}
	
	public static int getIdBySound(SoundEvent sound) {
		return SOUNDS.indexOf(sound);
	}
	
	public static int getNumberOfSounds() {
		return SOUNDS.size();
	}
}
