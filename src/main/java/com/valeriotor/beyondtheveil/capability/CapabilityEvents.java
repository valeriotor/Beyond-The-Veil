package com.valeriotor.beyondtheveil.capability;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerDataProvider;
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
import static com.valeriotor.beyondtheveil.capability.arsenal.TriggerDataProvider.TRIGGER_DATA;


@Mod.EventBusSubscriber
public class CapabilityEvents {

    @SubscribeEvent
    public static void logInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        syncCapabilities(event.getEntity());
    }

    @SubscribeEvent
    public static void changeDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        syncCapabilities(event.getEntity());
    }

    public static void syncCapabilities(Player p) {
        syncCapabilities(p, true, true);
    }

    public static void syncCapabilities(Player p, boolean playerData, boolean researchData) {
        if (p != null && !p.level().isClientSide) {
            if(playerData) {
                p.getCapability(PLAYER_DATA).resolve().ifPresent(data -> {
                    CompoundTag dataTag = new CompoundTag();
                    data.saveToNBT(dataTag);
                    SyncAllPlayerDataToClientPacket message = new SyncAllPlayerDataToClientPacket(dataTag);
                    Messages.sendToPlayer(message, (ServerPlayer) p);
                });
            }
            if(researchData) {
                p.getCapability(RESEARCH).resolve().ifPresent(data -> {
                    CompoundTag dataTag = new CompoundTag();
                    data.saveToNBT(dataTag);
                    SyncResearchToClientPacket message = new SyncResearchToClientPacket(ResearchSyncer.allResearchesToClient(dataTag));
                    Messages.sendToPlayer(message, (ServerPlayer) p);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
            if (!event.getObject().getCapability(PLAYER_DATA).isPresent()) {
                event.addCapability(new ResourceLocation(References.MODID, "player_data"), new PlayerDataProvider());
                event.addCapability(new ResourceLocation(References.MODID, "research_data"), new ResearchProvider());
            }
            if (event.getObject().level().isClientSide() && ClientSetup.isConnectionPresent()) {
                GenericToServerPacket message = new GenericToServerPacket(GenericToServerPacket.MessageType.ASK_DATA_SYNC);
                Messages.sendToServer(message);
            }
        }
        if (event.getObject() instanceof Villager || event.getObject() instanceof Pillager || event.getObject() instanceof AmmunitionEntity) { // TODO all the others
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
        event.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerData.class);
        event.register(ResearchData.class);
        event.register(TriggerData.class);
    }

}
