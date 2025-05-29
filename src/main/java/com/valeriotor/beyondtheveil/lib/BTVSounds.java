package com.valeriotor.beyondtheveil.lib;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTVSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, References.MODID);

    public static final RegistryObject<SoundEvent> INCISING = registerSound("incising");
    public static final RegistryObject<SoundEvent> INCISION = registerSound("incision");
    public static final RegistryObject<SoundEvent> INSERTING = registerSound("inserting");
    public static final RegistryObject<SoundEvent> EXTRACTING = registerSound("extracting");
    public static final RegistryObject<SoundEvent> HEAD_STRETCH = registerSound("head_stretch");
    public static final RegistryObject<SoundEvent> HEAD_EXPLODE = registerSound("head_explode");
    public static final RegistryObject<SoundEvent> WATER_DREAM = registerSound("water_dream");
    public static final RegistryObject<SoundEvent> WATER_DREAM_SHORT = registerSound("water_dream_short");
    public static final RegistryObject<SoundEvent> TENSION = registerSound("tension");
    public static final RegistryObject<SoundEvent> BAPTISM_GS = registerSound("baptism_gs");
    public static final RegistryObject<SoundEvent> WEEPING = registerSound("weeping");
    public static final RegistryObject<SoundEvent> FLETUM_WEEPING = registerSound("fletum_weeping");
    public static final RegistryObject<SoundEvent> HEARTBEAT = registerSound("heartbeat");
    public static final RegistryObject<SoundEvent> HEART_RIP = registerSound("heart_rip");
    public static final RegistryObject<SoundEvent> SPINE_RIP = registerSound("spine_rip");
    public static final RegistryObject<SoundEvent> CURRENTS = registerSound("currents");

    public static void init(IEventBus bus) {
        SOUNDS.register(bus);
    }

    private static RegistryObject<SoundEvent> registerSound(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(References.MODID, name)));
    }

}
