package com.valeriotor.beyondtheveil.capability;

import com.valeriotor.beyondtheveil.capability.research.ResearchData;
import com.valeriotor.beyondtheveil.capability.research.ResearchProvider;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.valeriotor.beyondtheveil.capability.PlayerDataProvider.PLAYER_DATA;
import static com.valeriotor.beyondtheveil.capability.research.ResearchProvider.RESEARCH;


@Mod.EventBusSubscriber
public class CapabilityEvents {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PLAYER_DATA).isPresent()) {
                event.addCapability(new ResourceLocation(References.MODID, "player_data"), new PlayerDataProvider());
                event.addCapability(new ResourceLocation(References.MODID, "research_data"), new ResearchProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(PLAYER_DATA).ifPresent(oldData -> {
            event.getPlayer().getCapability(PLAYER_DATA).ifPresent(oldData::copyToNewStore);
        });
        event.getOriginal().getCapability(RESEARCH).ifPresent(oldData -> {
            event.getPlayer().getCapability(RESEARCH).ifPresent(oldData::copyToNewStore);
        });
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerData.class);
        event.register(ResearchData.class);
    }

}
