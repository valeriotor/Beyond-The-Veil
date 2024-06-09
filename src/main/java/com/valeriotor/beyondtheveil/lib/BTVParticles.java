package com.valeriotor.beyondtheveil.lib;

import com.valeriotor.beyondtheveil.entity.DeepOneEntity;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTVParticles {
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, References.MODID);

    public static void init(IEventBus bus) {
        PARTICLE_TYPES.register(bus);
    }

    public static final RegistryObject<SimpleParticleType> BLOODSPILL = PARTICLE_TYPES.register("bloodspill", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> TEARSPILL = PARTICLE_TYPES.register("tearspill", () -> new SimpleParticleType(false));


}
