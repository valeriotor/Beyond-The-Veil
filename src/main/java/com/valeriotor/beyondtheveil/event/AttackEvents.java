package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.entity.DamageCapper;
import com.valeriotor.beyondtheveil.lib.BTVEffects;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttackEvents {

    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        Entity source = event.getSource().getEntity();
        if (entity.hasEffect(BTVEffects.VULNERABILITY.get())) {
            MobEffectInstance effect = entity.getEffect(BTVEffects.VULNERABILITY.get());
            event.setAmount(event.getAmount() * (2 + effect.getAmplifier()));
        }
        if (entity instanceof ServerPlayer player) {
            player.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).ifPresent(c -> {
                if (c.hasTimer("baptism")) {
                    event.setCanceled(true);
                }
            });
            if (DataUtil.getBoolean(player, PlayerDataLib.BAPTIZED)) {
                if (player.isInWater()) {
                    event.setAmount(event.getAmount() * 0.7F);
                } else if (player.isInWaterOrRain()) {
                    event.setAmount(event.getAmount() * 0.9F);
                }
            }
        }
        if (source instanceof Player player) {
            if (DataUtil.getBoolean(player, PlayerDataLib.BAPTIZED)) {
                if (player.isInWater()) {
                    event.setAmount(event.getAmount() * 2);
                } else if (player.isInWaterOrRain()) {
                    event.setAmount(event.getAmount() * 1.3F);
                }
            }
        }
        if (entity instanceof DamageCapper dc) {
            if (event.getAmount() > dc.getDamageCap()) {
                event.setAmount(dc.getDamageCap());
            }
        }
    }

    @SubscribeEvent
    public static void livingAttackEvent(LivingAttackEvent event) {
        if (event.getEntity().level() instanceof ServerLevel sl) {
            event.getEntity().getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(c -> {
                c.setCounter("memory_hormones", 80);
                int taken = c.takeXP();
                ExperienceOrb.award(sl, event.getEntity().position(), taken);
            });
        }

    }

}
