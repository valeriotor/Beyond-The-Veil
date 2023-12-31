package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.util.CounterType;
import com.valeriotor.beyondtheveil.util.WaypointType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTickEvents {

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
            });
        }
    }
}
