package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.capability.util.ProcessionDataProvider;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.world.dimension.BTVDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LivingTickEvents {
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
        pickupXP(event);
        convalescentCounters(event);
        doProcession(event);
        doArcheDamage(event);

    }

    private static void convalescentCounters(LivingEvent.LivingTickEvent event) {
        event.getEntity().getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(ConvalescentData::tickCounters);
    }

    private static void pickupXP(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Mob villager && villager.tickCount % 10 == 0) {
            villager.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(c -> {
                if (c.getFlags().containsKey("memory_hormones") && c.getCounter("memory_hormones") == 0) {
                    AABB aabb = new AABB(villager.getX() - 3, villager.getY() - 3, villager.getZ() - 3, villager.getX() + 3, villager.getY() + 3, villager.getZ() + 3);
                    List<ExperienceOrb> entities = villager.level().getEntities(EntityTypeTest.forClass(ExperienceOrb.class), aabb, e -> true);
                    for (ExperienceOrb orb : entities) {
                        int value = orb.value;
                        CompoundTag helperTag = new CompoundTag();
                        orb.addAdditionalSaveData(helperTag);
                        int count = Math.max(1, helperTag.getInt("Count")); // Hacky. Reflection would've been better?
                        c.addXP(value * count);
                        orb.discard();
                    }
                }
            });
        }
    }

    private static void doProcession(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Mob mob && (mob.tickCount & 7) == 0) {
            mob.getCapability(ProcessionDataProvider.PROCESSION_DATA).ifPresent(c -> {
                BlockPos destination = c.getDestination();
                if (destination != null) {
                    mob.getNavigation().moveTo(destination.getX() + 0.5, destination.getY(), destination.getZ() + 0.5, 1);
                }
            });
        }
    }

    private static void doArcheDamage(LivingEvent.LivingTickEvent event) {
        LivingEntity e = event.getEntity();
        if (e.isDeadOrDying()) {
            return;
        }
        if (e.tickCount % 20 == 0 && e.level().dimension() == BTVDimensions.ARCHE_LEVEL && e.isUnderWater()) {
            float damage = e.getMaxHealth() / 3F;
            if (e instanceof Player p) {
                if (DataUtil.getBoolean(p, PlayerDataLib.BAPTIZED)) {
                    Integer breath = DataUtil.getOrSetInteger(p, PlayerDataLib.ARCHE_BREATH, PlayerTickEvents.TOTAL_ARCHE_BREATH, false);
                    if (breath == null || breath >= 0) {
                        damage = 0;
                    } else {
                        damage = -breath / 40F;
                    }
                }
                if (p.isCreative()) {
                    damage = 0;
                }
            }
            if (damage > 0) {
                e.hurt(e.damageSources().fellOutOfWorld(), damage);
            }
        }
    }
}
