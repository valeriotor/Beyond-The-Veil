package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.entity.BloodCultistEntity;
import com.valeriotor.beyondtheveil.lib.BTVEffects;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.tile.PillarBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import com.valeriotor.beyondtheveil.util.VanillaUtils;
import com.valeriotor.beyondtheveil.world.saved.LifeEconomyData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.*;

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

    @SubscribeEvent
    public static void trampleTilledSoilEvent(BlockEvent.FarmlandTrampleEvent event) {
        if (event.getEntity() instanceof LivingEntity le && le.level() instanceof ServerLevel sl) {
            boolean diamondBoots = false, goldenPants = false;
            ItemStack boots = null, pants = null;
            for (ItemStack armorSlot : le.getArmorSlots()) {
                if (armorSlot.getItem() == Items.DIAMOND_BOOTS) {
                    diamondBoots = true;
                    boots = armorSlot;
                } else if (armorSlot.getItem() == Items.GOLDEN_LEGGINGS) {
                    goldenPants = true;
                    pants = armorSlot;
                }
            }
            boolean holdsDirt = le.getMainHandItem().getItem() == Items.DIRT;
            if (diamondBoots && goldenPants && holdsDirt) {
                event.setCanceled(true);
                BlockPos cropPos = event.getPos().above();
                BlockState blockState = le.level().getBlockState(cropPos);
                if (blockState.getBlock() instanceof BonemealableBlock bb) {
                    bb.performBonemeal(sl, le.getRandom(), cropPos, blockState);
                    if (le.getRandom().nextBoolean()) {
                        boots.setDamageValue(boots.getDamageValue() + 1);
                    } else {
                        pants.setDamageValue(pants.getDamageValue() + 1);
                    }
                    if (le instanceof ServerPlayer sp && !DataUtil.getBoolean(sp, PlayerDataLib.USED_FARMING_TECHNIQUE)) {
                        DataUtil.setBooleanOnServerAndSync(sp, PlayerDataLib.USED_FARMING_TECHNIQUE, true, false);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void babyEntitySpawnEvent(BabyEntitySpawnEvent event) {
        AgeableMob child = event.getChild();
        if (child != null && child.level() instanceof ServerLevel sl) {
            BlockPos pos = event.getParentA().getOnPos();
            LifeEconomyData instance = LifeEconomyData.getInstance(sl);
            List<LifeEconomyData.PillarData> activePillarsInChunk = instance.getActivePillarsInChunk(pos);
            Optional<LifeEconomyData.PillarData> min = activePillarsInChunk.stream()
                    .filter(data -> data.getLinkPos() != null && sl.isLoaded(data.getCurrentPos())) // we need the offer pillar to be loaded (but not the demand one)
                    .filter(data -> sl.getBlockEntity(data.getCurrentPos()) instanceof PillarBE be && be.isOffer())
                    .min(Comparator.comparing(data -> data.getCurrentPos().distSqr(pos)));
            min.ifPresent(data -> {
                BlockPos linkPos = data.getLinkPos();
                if (!sl.isLoaded(linkPos)) {
                    LifeEconomyData.PodData podData = instance.findClosestEmptyPod(linkPos, 2, 50);
                    if (podData != null) {
                        podData.setPatientAndSync(PatientType.VILLAGER, new CompoundTag(), sl);
                    }
                } else {
                    List<LifeEconomyData.PodData> closestEmptyPods = instance.findClosestEmptyPods(linkPos, 2, 50);
                    for (LifeEconomyData.PodData closestEmptyPod : closestEmptyPods) {
                        if (!instance.isReserved(closestEmptyPod)) {
                            BloodCultistEntity entity = new BloodCultistEntity(BTVEntities.BLOOD_CULTIST.get(), sl);
                            BlockPos podPos = closestEmptyPod.getCurrentPos();
                            BlockPos toTeleport = null;
                            for(int i = 0; i < 30; ++i) {
                                int j = MathHelperBTV.randomIntInclusive(-5, 5, entity.getRandom());
                                int k = MathHelperBTV.randomIntInclusive(-1, 1, entity.getRandom());
                                int l = MathHelperBTV.randomIntInclusive(-5, 5, entity.getRandom());
                                BlockPos candidate = new BlockPos(podPos.getX() + j, podPos.getY() + k, podPos.getZ() + l);
                                boolean flag = VanillaUtils.canTeleportTo(entity, candidate, sl, false);
                                if (flag) {
                                    toTeleport = candidate;
                                    break;
                                }
                            }
                            if (toTeleport == null) {
                                toTeleport = linkPos;
                            }
                            entity.setPos(toTeleport.getCenter());
                            entity.setPodPos(closestEmptyPod.getCurrentPos());
                            VillagerType[] types = new VillagerType[]{VillagerType.DESERT, VillagerType.JUNGLE, VillagerType.PLAINS, VillagerType.SAVANNA, VillagerType.SNOW, VillagerType.SWAMP, VillagerType.TAIGA};
                            entity.setHeldVillagerType(types[entity.getRandom().nextInt(types.length)]);
                            sl.addFreshEntity(entity);
                            instance.reserve(closestEmptyPod, entity.getId());
                            break;
                        }
                    }
                }
                //BloodCultistEntity entity = new BloodCultistEntity(Registration.BLOOD_CULTIST.get(), sl);
                //entity.setPos(linkPos.getCenter());
                //sl.addFreshEntity(entity);
                child.discard();
            });
        }
    }

    @SubscribeEvent
    public static void mountDismountEvent(EntityMountEvent event) {
        if (event.getEntityMounting() instanceof ServerPlayer sp && event.getEntityBeingMounted() instanceof BloodCultistEntity && !event.isMounting()) {
            sp.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).ifPresent(c -> {
                if (c.hasTimer("killedByCultist")) {
                    event.setCanceled(true);
                }
            });
        }
    }


}
