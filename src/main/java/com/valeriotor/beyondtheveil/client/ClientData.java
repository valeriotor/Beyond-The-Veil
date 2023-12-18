package com.valeriotor.beyondtheveil.client;

import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.WaypointType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientData {

    private static ClientData instance = new ClientData();

    public static ClientData getInstance() {
        return instance;
    }

    public static void newInstance() {
        instance = new ClientData();
    }

    //@SubscribeEvent
    //public static void logoutEvent(ClientPlayerNetworkEvent.LoggedOutEvent event) {
    //    instance = new ClientData();
    //}

    public final List<Waypoint> waypoints = new ArrayList<>();

    public void addWaypoint(CompoundTag tag) {
        WaypointType type = WaypointType.valueOf(tag.getString("type"));
        for (Waypoint waypoint : waypoints) {
            if (waypoint.type == type) {
                return;
            }
        }
        waypoints.add(new Waypoint(type, BlockPos.of(tag.getLong("pos")), type.unlocalizedName));
    }

    public void addWaypoint(WaypointType type, BlockPos pos, String unlocalizedName) {
        waypoints.add(new Waypoint(type, pos, unlocalizedName));
    }

    public void removeWaypoint(CompoundTag tag) {
        WaypointType type = WaypointType.valueOf(tag.getString("type"));
        Iterator<Waypoint> waypointIterator = waypoints.listIterator();
        while (waypointIterator.hasNext()) {
            Waypoint wp = waypointIterator.next();
            if (wp.type == type) {
                waypointIterator.remove();
                break;
            }
        }
    }

    public HitResult getClientHitResult() {
        return Minecraft.getInstance().hitResult;
    }


    public static class Waypoint {
        private WaypointType type;
        public final BlockPos pos;
        public final String localizedName;

        public Waypoint(WaypointType type, BlockPos pos, String unlocalizedName) {
            this.type = type;
            this.pos = pos;
            this.localizedName = I18n.get(unlocalizedName);
        }
    }
}

