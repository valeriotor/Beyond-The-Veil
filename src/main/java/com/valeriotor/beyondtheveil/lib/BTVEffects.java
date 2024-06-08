package com.valeriotor.beyondtheveil.lib;

import com.valeriotor.beyondtheveil.effect.FearsomeEffect;
import com.valeriotor.beyondtheveil.effect.FollyEffect;
import com.valeriotor.beyondtheveil.effect.GenericEffect;
import com.valeriotor.beyondtheveil.effect.TerrorEffect;
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

    public static final RegistryObject<MobEffect> VULNERABILITY = MOB_EFFECTS.register("vulnerability", () -> new GenericEffect(MobEffectCategory.HARMFUL, 0x777777));
    public static final RegistryObject<MobEffect> DISROBE = MOB_EFFECTS.register("disrobe", () -> new GenericEffect(MobEffectCategory.HARMFUL, 0x01064));
    public static final RegistryObject<MobEffect> DROP_ITEM = MOB_EFFECTS.register("drop_item", () -> new GenericEffect(MobEffectCategory.HARMFUL, 0x01094));
    public static final RegistryObject<MobEffect> FOLLY = MOB_EFFECTS.register("folly", () -> new FollyEffect(MobEffectCategory.HARMFUL, 0x01094));
    public static final RegistryObject<MobEffect> TERROR = MOB_EFFECTS.register("terror", () -> new TerrorEffect(MobEffectCategory.HARMFUL, 0x01094));
    public static final RegistryObject<MobEffect> FEARSOME = MOB_EFFECTS.register("fearsome", () -> new FearsomeEffect(MobEffectCategory.HARMFUL, 0x01094));



}
