package com.valeriotor.beyondtheveil.capability;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerDataProvider;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.capability.research.ResearchData;
import com.valeriotor.beyondtheveil.capability.research.ResearchProvider;
import com.valeriotor.beyondtheveil.client.ClientSetup;
import com.valeriotor.beyondtheveil.entity.AmmunitionEntity;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.*;
import com.valeriotor.beyondtheveil.research.Research;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

import static com.valeriotor.beyondtheveil.capability.PlayerDataProvider.PLAYER_DATA;
import static com.valeriotor.beyondtheveil.capability.research.ResearchProvider.RESEARCH;
import static com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider.CROSS_SYNC_DATA;
import static com.valeriotor.beyondtheveil.capability.arsenal.TriggerDataProvider.TRIGGER_DATA;


@Mod.EventBusSubscriber
public class CapabilityEvents {

    @SubscribeEvent
    public static void logInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        syncCapabilities(event.getEntity());
    }

    @SubscribeEvent
    public static void logOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            sp.getCapability(CROSS_SYNC_DATA).ifPresent(data -> Messages.sendToPlayer(GenericToClientPacket.stopCrossSync(sp), sp));
        }
    }

    @SubscribeEvent
    public static void changeDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        syncCapabilities(event.getEntity());
    }

    public static void syncCapabilities(Player p) {
        syncCapabilities(p, true, true);
    }

    public static void syncCapabilities(Player p, boolean playerData, boolean researchData) {
        if (p instanceof ServerPlayer sp) {
            if (playerData) {
                p.getCapability(PLAYER_DATA).resolve().ifPresent(data -> {
                    CompoundTag dataTag = new CompoundTag();
                    data.saveToNBT(dataTag);
                    SyncAllPlayerDataToClientPacket message = new SyncAllPlayerDataToClientPacket(dataTag);
                    Messages.sendToPlayer(message, (ServerPlayer) p);
                });
            }
            if (researchData) {
                p.getCapability(RESEARCH).resolve().ifPresent(data -> {
                    CompoundTag dataTag = new CompoundTag();
                    data.saveToNBT(dataTag);
                    SyncResearchToClientPacket message = new SyncResearchToClientPacket(ResearchSyncer.allResearchesToClient(dataTag));
                    Messages.sendToPlayer(message, (ServerPlayer) p);
                });
            }
            p.getCapability(CROSS_SYNC_DATA).ifPresent(data -> Messages.sendToPlayer(GenericToClientPacket.crossSync(p, data.getCrossSync()), sp));

        }
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
            if (!event.getObject().getCapability(PLAYER_DATA).isPresent()) {
                event.addCapability(new ResourceLocation(References.MODID, "player_data"), new PlayerDataProvider());
                event.addCapability(new ResourceLocation(References.MODID, "research_data"), new ResearchProvider());
                event.addCapability(new ResourceLocation(References.MODID, "cross_sync_data"), new CrossSyncDataProvider());
            }
            if (event.getObject().level().isClientSide() && ClientSetup.isConnectionPresent()) {
                GenericToServerPacket message = new GenericToServerPacket(GenericToServerPacket.MessageType.ASK_DATA_SYNC);
                Messages.sendToServer(message);
            }
        }
        if (event.getObject() instanceof Villager || event.getObject() instanceof Pillager || event.getObject() instanceof AmmunitionEntity) {
            if (!event.getObject().getCapability(TRIGGER_DATA).isPresent()) {
                event.addCapability(new ResourceLocation(References.MODID, "trigger_data"), new TriggerDataProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        LazyOptional<PlayerData> capability = event.getOriginal().getCapability(PLAYER_DATA);
        LazyOptional<PlayerData> capability2 = event.getEntity().getCapability(PLAYER_DATA);
        event.getOriginal().getCapability(PLAYER_DATA).ifPresent(oldData -> {
            event.getEntity().getCapability(PLAYER_DATA).ifPresent(oldData::copyToNewStore);
        });
        boolean b = capability.isPresent();
        boolean b2 = capability2.isPresent();
        event.getOriginal().getCapability(RESEARCH).ifPresent(oldData -> {
            event.getEntity().getCapability(RESEARCH).ifPresent(oldData::copyToNewStore);
        });
        event.getOriginal().getCapability(CROSS_SYNC_DATA).ifPresent(oldData -> {
            event.getEntity().getCapability(CROSS_SYNC_DATA).ifPresent(newData -> {
                oldData.copyToNewStore(newData);
                newData.getCrossSync().sync(event.getEntity());
            });
        });
        // TODO we probably will want to do this for crosssync as well
        event.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerData.class);
        event.register(ResearchData.class);
        event.register(CrossSyncData.class);
        event.register(TriggerData.class);
    }

    @SubscribeEvent
    public static void startTrackingEvent(PlayerEvent.StartTracking event) {
        if (event.getEntity() instanceof ServerPlayer receiver && event.getTarget() instanceof Player sender) {
            if (!sender.level().isClientSide) {
                sender.getCapability(CROSS_SYNC_DATA).ifPresent(data -> Messages.sendToPlayer(GenericToClientPacket.crossSync(sender, data.getCrossSync()), receiver));
            }
        }
    }

    @SubscribeEvent
    public static void stopTrackingEvent(PlayerEvent.StopTracking event) {
        if (event.getEntity() instanceof ServerPlayer receiver && event.getTarget() instanceof Player sender) {
            if (!sender.level().isClientSide) {
                sender.getCapability(CROSS_SYNC_DATA).ifPresent(data -> Messages.sendToPlayer(GenericToClientPacket.stopCrossSync(sender), receiver));
            }
        }
    }

}
