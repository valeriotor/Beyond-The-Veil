package com.valeriotor.beyondtheveil.lib;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public static final RegistryObject<SoundEvent> SUBMARINE_CRASH = registerSound("submarine_crash");
    public static final RegistryObject<SoundEvent> PROPELLER = registerSound("propeller");
    public static final RegistryObject<SoundEvent> SHOREMAN_DIALOGUE = registerSound("shoreman_dialogue");
    public static final RegistryObject<SoundEvent> BLOOD_RITUAL = registerSound("blood_ritual");
    public static final RegistryObject<SoundEvent> SURGEON_IDLE = registerSound("surgeon_idle");
    public static final RegistryObject<SoundEvent> BLOOD_ZOMBIE_LONG = registerSound("blood_zombie_long");
    public static final RegistryObject<SoundEvent> BLOOD_ZOMBIE_SHORT = registerSound("blood_zombie_short");
    public static final RegistryObject<SoundEvent> DAGON_TENSION = registerSound("dagon_tension");
    public static final RegistryObject<SoundEvent> DAGON_THUMP = registerSound("dagon_thump");
    public static final RegistryObject<SoundEvent> KILL_KEEPER = registerSound("kill_keeper");
    public static final RegistryObject<SoundEvent> KILL_CULTIST = registerSound("kill_cultist");
    public static final RegistryObject<SoundEvent> SHOREMAN_CULTIST_TENSION = registerSound("shoreman_cultist_tension");
    public static final RegistryObject<SoundEvent> KEEPER_SUFFOCATE = registerSound("keeper_suffocate");
    public static final List<RegistryObject<SoundEvent>> CURRENTS_LIST = registerCurrents();

    public static void init(IEventBus bus) {
        SOUNDS.register(bus);
    }

    private static RegistryObject<SoundEvent> registerSound(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(References.MODID, name)));
    }

    private static List<RegistryObject<SoundEvent>> registerCurrents() {
        List<RegistryObject<SoundEvent>> currents = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            currents.add(registerSound("currents" + i));
        }
        return Collections.unmodifiableList(currents);
    }

}
