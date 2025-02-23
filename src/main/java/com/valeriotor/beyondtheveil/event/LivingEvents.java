package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.BTVEffects;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LivingEvents {

    @SubscribeEvent
    public static void endermanAngerEvent(EnderManAngerEvent event) {
        Player p = event.getPlayer();
        if (p != null) {
            if (DataUtil.getBoolean(p, PlayerDataLib.REMINISCING)) {
                event.setCanceled(true);
            }
        }
    }

    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    @SubscribeEvent
    public static void effectExpireEvent(MobEffectEvent.Expired event) {
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance == null) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (effectInstance.getEffect() == BTVEffects.DISROBE.get()) {
            List<EquipmentSlot> possibleSlots = new ArrayList<>();
            for (EquipmentSlot armorSlot : ARMOR_SLOTS) {
                if (!entity.getItemBySlot(armorSlot).isEmpty()) {
                    possibleSlots.add(armorSlot);
                }
            }
            if (possibleSlots.size() > 0) {
                Collections.shuffle(possibleSlots); // why shuffling instead of random.nextint -> get? Because we may want more than one slot
                int numberDropped = effectInstance.getAmplifier() >= 2 ? effectInstance.getAmplifier() : 1;
                for (int i = 0; i < Math.min(possibleSlots.size(), numberDropped); i++) {
                    ItemStack toDrop = entity.getItemBySlot(possibleSlots.get(i));
                    entity.setItemSlot(possibleSlots.get(i), ItemStack.EMPTY);
                    if (entity instanceof Player player && effectInstance.getAmplifier() == 0) {
                        ItemHandlerHelper.giveItemToPlayer(player, toDrop);
                    } else {
                        Direction random = Direction.from2DDataValue(entity.getRandom().nextInt(4));
                        DefaultDispenseItemBehavior.spawnItem(entity.level(), toDrop, 5, random, entity.position().relative(random, 2));
                    }
                }
            }
        } else if (effectInstance.getEffect() == BTVEffects.DROP_ITEM.get()) {
            List<EquipmentSlot> slots = new ArrayList<>();
            slots.add(EquipmentSlot.MAINHAND);
            if (effectInstance.getAmplifier() > 1) {
                slots.add(EquipmentSlot.OFFHAND);
            }
            Vec3 position = entity.position();
            double xComponent = -Math.sin(Math.toRadians(entity.getYHeadRot()));
            double zComponent = Math.cos(Math.toRadians(entity.getYHeadRot()));
            position = position.add(xComponent * (1 + effectInstance.getAmplifier() / 2D), 1, zComponent * (1 + effectInstance.getAmplifier() / 2D));
            for (EquipmentSlot slot : slots) {
                ItemStack toDrop = entity.getItemBySlot(slot);
                if (!toDrop.isEmpty()) {
                    entity.setItemSlot(slot, ItemStack.EMPTY);
                    ItemEntity itementity = new ItemEntity(entity.level(), position.x, position.y, position.z, toDrop);
                    itementity.setDefaultPickUpDelay();
                    itementity.setDeltaMovement(new Vec3(xComponent * (0.4 + effectInstance.getAmplifier() / 3D), 0, zComponent * (0.4 + effectInstance.getAmplifier() / 3D)));
                    entity.level().addFreshEntity(itementity);
                }

            }
        }
    }

    @SubscribeEvent
    public static void targetEvent(LivingChangeTargetEvent event) {
        // TODO test this
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(BTVEffects.FOLLY.get())) {
            event.setNewTarget(null);
        }
    }

    @SubscribeEvent
    public static void fallEvent(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(BTVEffects.SINK.get())) {
            MobEffectInstance effect = entity.getEffect(BTVEffects.SINK.get());
            event.setDamageMultiplier((effect.getAmplifier() + 1) * 2);
        }
    }

    @SubscribeEvent
    public static void canApplyEffect(MobEffectEvent.Applicable event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            CuriosApi.getCuriosInventory(player).ifPresent(inv -> {
                inv.getStacksHandler("head").ifPresent(slot -> {
                    ItemStack stackInSlot = slot.getStacks().getStackInSlot(0);
                    if (stackInSlot.getItem() == Registration.BONE_TIARA.get()) {
                        if (Registration.BONE_TIARA.get().EFFECTS.contains(event.getEffectInstance().getEffect())) {
                            event.setResult(Event.Result.DENY);
                        }
                    }
                });
            });
        }
    }

    @SubscribeEvent
    public static void tickEvent(LivingEvent.LivingTickEvent event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            CuriosApi.getCuriosInventory(player).ifPresent(inv -> {
                inv.getStacksHandler("head").ifPresent(slot -> {
                    ItemStack stackInSlot = slot.getStacks().getStackInSlot(0);
                    if (stackInSlot.getItem() == Registration.BONE_TIARA.get()) {
                        for (MobEffect effect : Registration.BONE_TIARA.get().EFFECTS) {
                            player.removeEffect(effect);
                        }
                    }
                });
            });
        }
    }

    @SubscribeEvent
    public static void knockbackEvent(LivingKnockBackEvent event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            CuriosApi.getCuriosInventory(player).ifPresent(inv -> {
                inv.getStacksHandler("head").ifPresent(slot -> {
                    ItemStack stackInSlot = slot.getStacks().getStackInSlot(0);
                    if (stackInSlot.getItem() == Registration.BONE_TIARA.get()) {
                        event.setCanceled(true);
                    }
                });
            });
        }
    }


}
