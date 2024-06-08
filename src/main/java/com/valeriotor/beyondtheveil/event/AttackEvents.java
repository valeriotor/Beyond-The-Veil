package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.lib.BTVEffects;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttackEvents {

    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(BTVEffects.VULNERABILITY.get())) {
            MobEffectInstance effect = entity.getEffect(BTVEffects.VULNERABILITY.get());
            event.setAmount(event.getAmount() * (2 + effect.getAmplifier()));
        }
    }

}
