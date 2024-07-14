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
    public static final RegistryObject<SoundEvent> HEAD_STRETCH = registerSound("head_stretch");
    public static final RegistryObject<SoundEvent> HEAD_EXPLODE = registerSound("head_explode");

    public static void init(IEventBus bus) {
        SOUNDS.register(bus);
    }

    private static RegistryObject<SoundEvent> registerSound(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(References.MODID, name)));
    }

}
