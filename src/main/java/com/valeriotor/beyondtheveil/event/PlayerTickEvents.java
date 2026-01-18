package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerData;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.dreaming.dreams.Reminiscence;
import com.valeriotor.beyondtheveil.dreaming.dreams.ReminiscenceWaypoint;
import com.valeriotor.beyondtheveil.entity.CanoeEntity;
import com.valeriotor.beyondtheveil.entity.NautilusEntity;
import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.item.SlugItem;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.surgery.surgeon.BellData;
import com.valeriotor.beyondtheveil.tile.SacrificeAltarBE;
import com.valeriotor.beyondtheveil.util.*;
import com.valeriotor.beyondtheveil.world.dimension.BTVDimensions;
import com.valeriotor.beyondtheveil.world.saved.PlayerSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTickEvents {

    public static final int TOTAL_ARCHE_BREATH = 30 * 20;

    @SubscribeEvent
    public static void tickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER) {
            Player p = event.player;
            p.getCapability(PlayerDataProvider.PLAYER_DATA, null).ifPresent(playerData -> {
                List<CounterType> counterTypes = playerData.tickCounters();
                for (CounterType type : counterTypes) {
                    if (type instanceof WaypointType waypointType) {
                        Messages.sendToPlayer(GenericToClientPacket.removeWaypoint(waypointType), (ServerPlayer) p);
                    }
                }
                Long altarLong = playerData.getLong(PlayerDataLib.sacrifice_altar.name());
                if (altarLong != null && altarLong != -1) {
                    BlockPos altarPos = BlockPos.of(altarLong);
                    Level level = p.level();
                    if (level.isLoaded(altarPos) && altarPos.distSqr(p.blockPosition()) < SacrificeAltarBE.MAX_PLAYER_DISTANCE_SQR && level.getBlockEntity(altarPos) instanceof SacrificeAltarBE sacrificeAltar) {
                        UUID playerInitiating = sacrificeAltar.getPlayerInitiating();
                        if (!p.getUUID().equals(playerInitiating)) {
                            playerData.setLong(PlayerDataLib.sacrifice_altar.name(), -1, false);
                        }
                    } else {
                        playerData.setLong(PlayerDataLib.sacrifice_altar.name(), -1, false);
                    }
                }
            });
            checkDiscoveredWaypoint(event);
            p.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).ifPresent(c -> c.tick(p));
            resetTimesDreamt(event);
            rainBeforeContact(event);
            decrementArcheBreath(event);
            sendOtherPlayerDeathCoords(event);
            surgeonBellParticles(event);
            transformationTickEvents(event);
        }
    }

    private static void transformationTickEvents(TickEvent.PlayerTickEvent event) {
        event.player.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).ifPresent(crossSyncData -> {
            CrossSync crossSync = crossSyncData.getCrossSync();
            if ((event.player.tickCount & 3) == 0 && crossSync.getTransformation() != null && event.player instanceof ServerPlayer sp) {
                ItemStack selected = event.player.getInventory().getSelected();
                if (!selected.isEmpty()) {
                    if (sp.isUsingItem() && sp.getUsedItemHand() == InteractionHand.MAIN_HAND) sp.stopUsingItem();
                    sp.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    sp.drop(selected, false, false);
                }
                ItemStack offHandItem = sp.getItemInHand(InteractionHand.OFF_HAND);
                if (!offHandItem.isEmpty()) {
                    sp.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    sp.drop(offHandItem, false, false);
                }
            }
        });
    }

    private static void surgeonBellParticles(TickEvent.PlayerTickEvent event) {

        if (event.player instanceof ServerPlayer sp && sp.tickCount % 5 == 0) {
            ItemStack mainHand = sp.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack offHand = sp.getItemInHand(InteractionHand.OFF_HAND);
            ItemStack stack = mainHand.getItem() == Registration.SURGEON_BELL.get() ? mainHand : (offHand.getItem() == Registration.SURGEON_BELL.get() ? offHand : ItemStack.EMPTY);
            if (!stack.isEmpty()) {
                CompoundTag tag = stack.getOrCreateTag();
                if (tag.contains("surgeon")) {
                    UUID uuid = tag.getUUID("surgeon");
                    RandomSource r = sp.getRandom();
                    ServerLevel serverLevel = sp.serverLevel();
                    Entity entity = serverLevel.getEntity(uuid);
                    if (entity instanceof SurgeonEntity surgeon) {
                        BellData data = surgeon.bellData;
                        Stream.concat(Stream.concat(data.inputPods().stream(), data.inputContainers().stream()), data.inputSpots().stream()).forEach(pos -> {
                            serverLevel.sendParticles(sp, new DustParticleOptions(new Vector3f(0,1,0), 1), false, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20, r(r), r(r), r(r), 0);
                        });
                        Stream.concat(Stream.concat(data.outputPods().stream(), data.outputContainers().stream()), data.outputSpots().stream()).forEach(pos -> {
                            serverLevel.sendParticles(sp, new DustParticleOptions(new Vector3f(1,0,0), 1), false, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20, r(r), r(r), r(r), 0);
                        });
                        BlockPos surgicalBE = data.getSurgicalBE();
                        if (surgicalBE != null) {
                            serverLevel.sendParticles(sp, new DustParticleOptions(new Vector3f(0,0,1), 1), false, surgicalBE.getX() + 0.5, surgicalBE.getY() + 0.5, surgicalBE.getZ() + 0.5, 20, r(r), r(r), r(r), 0);
                        }
                        serverLevel.sendParticles(sp, new DustParticleOptions(new Vector3f(0,1,1), 1), false, surgeon.getX() + 0.5, surgeon.getY() + 1.5, surgeon.getZ() + 0.5, 20, 2 * r(r), 2 * r(r), 2 * r(r), 0);
                    }

                }

            }
        }
    }

    private static double r(RandomSource r) {
        return (r.nextDouble() - 0.5) * 0.95;
    }

    private static void sendOtherPlayerDeathCoords(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer sp && sp.tickCount % 20 == 0 && sp.getServer() != null) {
            if (sp.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Registration.SIGIL_PLAYER.get() || sp.getItemInHand(InteractionHand.OFF_HAND).getItem() == Registration.SIGIL_PLAYER.get()) {
                PlayerSavedData data = PlayerSavedData.getInstance(sp.getServer().overworld());
                List<BlockPos> poss = new ArrayList<>(data.deathsInRange(sp, 40));
                poss.addAll(data.respawnsInRange(sp, 40));
                Messages.sendToPlayer(GenericToClientPacket.addClosestDeath(poss), sp);
                if (!poss.isEmpty()) {
                }
            }
        }
    }

    private static void checkDiscoveredWaypoint(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if ((player.tickCount & 15) == 0 && player instanceof ServerPlayer sp) {
            Map<String, Reminiscence> reminiscences = DataUtil.getReminiscences(player);
            for (Map.Entry<String, Reminiscence> entry : reminiscences.entrySet()) {
                String foundKey = PlayerDataLib.FOUND_WAYPOINT.apply(entry.getKey());
                if (entry.getValue() instanceof ReminiscenceWaypoint rw && !DataUtil.getBoolean(player, foundKey)) {
                    BlockPos playerPos = player.blockPosition();
                    if (Math.abs(playerPos.getX() - rw.getPos().getX()) < 50 && Math.abs(playerPos.getZ() - rw.getPos().getZ()) < 50) {
                        DataUtil.setBooleanOnServerAndSync(player, foundKey, true, false);
                        if (entry.getKey().equals(Memory.DARKNESS.getDataName(false))) {
                            if (DataUtil.getBoolean(player, PlayerDataLib.spoke_keeper.name())) {
                                DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.unlocked_hamlet.name(), true, false);
                            }
                            DataUtil.getMemoryStatus(player, Memory.DARKNESS).increaseTo(2, Memory.Target.BASE, false);
                            DataUtil.syncMemories(sp);
                        } else if (entry.getKey().equals(Memory.SENTIENCE.getDataName(false))) {
                            DataUtil.getMemoryStatus(player, Memory.SENTIENCE).increaseTo(2, Memory.Target.BASE, false);
                            DataUtil.syncMemories(sp);
                        }
                    }
                }
            }
        }
    }

    /**
     * Safety check in case something related to the playertimer (defined in DreamHandler.markEvent) goes haywire
     */
    private static void resetTimesDreamt(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (!player.level().isClientSide && player.level().getDayTime() <= 10) {
            player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(c -> {
                c.setInteger(PlayerDataLib.TIMES_DREAMT.apply("sleep_chamber"), 0, false);
                c.setInteger(PlayerDataLib.TIMES_DREAMT.apply("dream_bottle"), 0, false);
            });
            PlayerTimerData.for_(player).removeTimer("sleep_chamber");
            PlayerTimerData.for_(player).removeTimer("dream_bottle");
        }
    }

    private static void rainBeforeContact(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level l = player.level();
        if (!l.isClientSide && player instanceof ServerPlayer sp && SlugItem.hasResearchForContact(sp) && sp.getVehicle() instanceof CanoeEntity) {
            long t = l.getDayTime();
            if (t >= 16000 && t < 20000 && l.getBiome(sp.getOnPos()).is(BiomeTags.IS_OCEAN) && !l.isRaining()) {
                ((ServerLevel) l).setWeatherParameters(0, 3000, true, true);
            }
        }
    }

    private static void decrementArcheBreath(TickEvent.PlayerTickEvent event) {
        Player p = event.player;
        if (p.isDeadOrDying() || p.getVehicle() instanceof NautilusEntity) {
            return;
        }
        if (p.level().dimension() == BTVDimensions.ARCHE_LEVEL && p.isUnderWater() && !(p.getVehicle() instanceof NautilusEntity)) {
            if (DataUtil.getBoolean(p, PlayerDataLib.baptized.name())) {
                DataUtil.incrementOrSetInteger(p, PlayerDataLib.arche_breath.name(), -1, TOTAL_ARCHE_BREATH, false);
            }
        } else {
            if (DataUtil.getOrSetInteger(p, PlayerDataLib.arche_breath.name(), TOTAL_ARCHE_BREATH, false) < 0) {
                DataUtil.setInt(p, PlayerDataLib.arche_breath.name(), 0, false);
            }
            if (DataUtil.getOrSetInteger(p, PlayerDataLib.arche_breath.name(), TOTAL_ARCHE_BREATH, false) < TOTAL_ARCHE_BREATH) {
                DataUtil.incrementOrSetInteger(p, PlayerDataLib.arche_breath.name(), 1, TOTAL_ARCHE_BREATH, false);
            }
        }
    }
}
