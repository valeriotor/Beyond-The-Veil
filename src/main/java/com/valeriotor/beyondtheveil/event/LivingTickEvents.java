package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.capability.util.ProcessionDataProvider;
import com.valeriotor.beyondtheveil.client.ClientMethods;
import com.valeriotor.beyondtheveil.entity.NautilusEntity;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.world.dimension.ArcheSavedData;
import com.valeriotor.beyondtheveil.world.dimension.BTVDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LivingTickEvents {
    @SubscribeEvent
    public static void tickEvent(LivingEvent.LivingTickEvent event) {
        doArcheCurrent(event);
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
        doArcheDrownDamage(event);

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

    private static void doArcheDrownDamage(LivingEvent.LivingTickEvent event) {
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
                if (p.isCreative() || p.isSpectator()) {
                    damage = 0;
                }
            }
            if (damage > 0) {
                e.hurt(e.damageSources().fellOutOfWorld(), damage);
            }
        }
    }

    private static void doArcheCurrent(LivingEvent.LivingTickEvent event) {
        LivingEntity e = event.getEntity();
        if (e.isDeadOrDying()) {
            return;
        }
        if (e.level().dimension() == BTVDimensions.ARCHE_LEVEL) {
            if (e.level() instanceof ServerLevel sl) {
                ArcheSavedData arche = sl.getDataStorage().computeIfAbsent(ArcheSavedData::new, ArcheSavedData::new, "arche");
                long ticks = arche.ticksInCycle();
                if (ticks < 20 * 10) {
                    return;
                }
                float currentIntensity = arche.getCurrentIntensity();

                doArcheMovement(currentIntensity, e);
                if (ticks % 20 == 0) {
                    if (e instanceof Player p && p.getVehicle() instanceof NautilusEntity nautilus) {
                        //nautilus.setDamage(nautilus.getDamage() + NautilusEntity.TOTAL_HEALTH * currentIntensity / 20);
                        nautilus.hurt(e.damageSources().fellOutOfWorld(), NautilusEntity.TOTAL_HEALTH / 45F * currentIntensity);
                    } else if (e instanceof Player p) {
                        if (!p.isCreative() && !p.isSpectator()) {
                            e.hurt(e.damageSources().fellOutOfWorld(), e.getMaxHealth() / 2 * currentIntensity);
                        }
                    } else {
                        e.hurt(e.damageSources().fellOutOfWorld(), e.getMaxHealth() / 2 * currentIntensity);
                    }
                }
            } else if (e.level().isClientSide) { // Client side stuff
                ClientMethods.doArcheEffects(event);
            }
        }
    }

    public static void doArcheMovement(float intensity, Entity entity) {
        if (!entity.isUnderWater()) {
            return;
        }
        boolean flag = false;
        BlockPos.MutableBlockPos blockPos = entity.blockPosition().mutable();
        int xo = blockPos.getX();
        int consecutiveWaterBlocks = 0;
        final int REQUIRED = 7;
        for (int x = 0; x < REQUIRED; x++) {
            blockPos.setX(xo + x);
            if (entity.level().getBlockState(blockPos).getBlock() != Blocks.WATER) {
                break;
            }
            consecutiveWaterBlocks++;
        }
        for (int x = -1; x > -REQUIRED; x--) {
            blockPos.setX(xo + x);
            if (entity.level().getBlockState(blockPos).getBlock() != Blocks.WATER) {
                break;
            }
            consecutiveWaterBlocks++;
            if (consecutiveWaterBlocks >= REQUIRED) {
                flag = true;
            }
        }
        if (!flag) {
            return;
        }
        if (entity instanceof Player p && p.getVehicle() instanceof NautilusEntity nautilus) {
            //intensity = (float) Math.log(intensity);
            double y = intensity > 0.5 && nautilus.tickCount % 2 == 0 ? (p.getRandom().nextFloat() - 0.5) * (intensity - 0.5) * 2 : 0;
            nautilus.move(MoverType.SELF, new Vec3(-2*Mth.square(intensity), y, 0));
            return;
        }
        if (entity instanceof Player p && p.isSpectator()) {
            return;
        }
        Vec3 currentMovement = entity.getDeltaMovement();
        if (currentMovement.x > -10) {
            double y = intensity > 0.5 && entity.tickCount % 2 == 0 ? (entity.level().getRandom().nextFloat() - 0.5) * (intensity - 0.5) * 2 : 0;
            entity.move(MoverType.SELF, new Vec3(-2*Mth.square(intensity), y, 0));
//            entity.setDeltaMovement(currentMovement.x - intensity * 0.08F, currentMovement.y, currentMovement.z);
        }
    }
}
