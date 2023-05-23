package com.valeriotor.beyondtheveil.client;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientData {

    private static ClientData instance = new ClientData();

    public static ClientData getInstance() {
        return instance;
    }

    @SubscribeEvent
    public static void logoutEvent(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        instance = new ClientData();
    }

    public final List<Waypoint> waypoints = new ArrayList<>();

    public void addWaypoint(CompoundTag tag) {
        waypoints.add(new Waypoint(BlockPos.of(tag.getLong("pos")), tag.getString("name")));
    }


    public static class Waypoint {
        public final BlockPos pos;
        public final String localizedName;

        public Waypoint(BlockPos pos, String unlocalizedName) {
            this.pos = pos;
            this.localizedName = I18n.get(unlocalizedName);
        }
    }
}

