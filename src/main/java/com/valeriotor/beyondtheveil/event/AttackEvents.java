package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.entity.DamageCapper;
import com.valeriotor.beyondtheveil.entity.NautilusEntity;
import com.valeriotor.beyondtheveil.lib.BTVEffects;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Objects;

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
            playerDamageEvent(event, player);
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
        if (entity instanceof ServerPlayer player) {
            bleedingBeltEvent(event, player);
        }

    }

    private static void playerDamageEvent(LivingDamageEvent event, ServerPlayer player) {
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
        if(event.getSource().getEntity() instanceof ServerPlayer sl) {
            Inventory inv = player.getInventory();
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack stack = inv.getItem(i);
                // TODO requires multiplayer testing
                if (stack.getTag() != null && stack.getTag().contains("bind_item_damage") && Objects.equals(sl.getUUID(), stack.getTag().getUUID("bind_item_damage"))) {
                    if (event.getAmount() > 4) {
                        player.hurt(player.damageSources().fellOutOfWorld(), event.getAmount() / 4);
                    }
                    event.setAmount(event.getAmount() * 1.5F);
                    break;
                }
            }
            inv = sl.getInventory();
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack stack = inv.getItem(i);
                // TODO requires multiplayer testing
                if (stack.getTag() != null && stack.getTag().contains("bind_item_weakness") && Objects.equals(player.getUUID(), stack.getTag().getUUID("bind_item_weakness"))) {
                    event.setAmount(event.getAmount() * 0.35F);
                    break;
                }
            }
            ItemStack offHand = sl.getItemInHand(InteractionHand.OFF_HAND);
            if (offHand.getItem() == Registration.SIGIL_PLAYER.get() && (offHand.getTag() == null || !offHand.getTag().contains("player"))) {
                float health = player.getHealth();
                float probability = (10 - health);
                if (player.getRandom().nextDouble() * 5 < probability) {
                    offHand.getOrCreateTag().putUUID("player", player.getUUID());
                    offHand.getTag().putString("username", player.getGameProfile().getName());
                }
            }
        }

    }

    private static void bleedingBeltEvent(LivingDamageEvent event, ServerPlayer player) {
        CuriosApi.getCuriosInventory(player).ifPresent(inv -> {
            inv.getStacksHandler("belt").ifPresent(slot -> {
                ItemStack stackInSlot = slot.getStacks().getStackInSlot(0);
                if (stackInSlot.getItem() == Registration.BLEEDING_BELT.get()) {
                    float hungerDamage = Math.min(event.getAmount(), player.getFoodData().getFoodLevel());
                    hungerDamage = Math.min(hungerDamage, stackInSlot.getMaxDamage() - stackInSlot.getDamageValue() - 1);
                    float remainingDamage = Math.max(0, event.getAmount() - hungerDamage);
                    if (hungerDamage > 0) {
                        player.getFoodData().setFoodLevel((int) (player.getFoodData().getFoodLevel() - hungerDamage));
                        stackInSlot.setDamageValue((int) (stackInSlot.getDamageValue() + hungerDamage));
                    }
                    event.setAmount(remainingDamage);
                }
            });
        });
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
        if (event.getEntity() instanceof Player player && player.getVehicle() instanceof NautilusEntity nautilus) {
            event.setCanceled(true);
            nautilus.hurt(event.getSource(), event.getAmount());
        }
        if (event.getSource().getEntity() instanceof Player player && player.getVehicle() instanceof NautilusEntity) {
            event.setCanceled(true);
        }

    }

}
