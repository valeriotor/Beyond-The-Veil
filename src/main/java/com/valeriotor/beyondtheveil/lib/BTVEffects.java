package com.valeriotor.beyondtheveil.lib;

import com.valeriotor.beyondtheveil.effect.GenericEffect;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTVEffects {

    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, References.MODID);

    public static void init(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }

    public static final RegistryObject<MobEffect> VULNERABLE = MOB_EFFECTS.register("vulnerable", () -> new GenericEffect(MobEffectCategory.HARMFUL, 0x777777));
    public static final RegistryObject<MobEffect> DISROBE = MOB_EFFECTS.register("disrobe", () -> new GenericEffect(MobEffectCategory.HARMFUL, 0x01064));
    public static final RegistryObject<MobEffect> DROP_ITEM = MOB_EFFECTS.register("drop_item", () -> new GenericEffect(MobEffectCategory.HARMFUL, 0x01094));



}
