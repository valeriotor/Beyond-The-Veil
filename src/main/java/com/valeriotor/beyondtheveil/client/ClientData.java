package com.valeriotor.beyondtheveil.client;

import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.WaypointType;
import com.valeriotor.beyondtheveil.world.dimension.ArcheSavedData;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
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
    public ArcheSavedData archeSavedData = new ArcheSavedData();
    private BloodPoolData bloodPoolData = new BloodPoolData();
    private int contactTimer = 0;
    private int contactFogLevel = 0;

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

    public void syncArcheData(CompoundTag tag) {
        archeSavedData = new ArcheSavedData(tag.getCompound("data"));
    }

    public void tick() {
        if(!Minecraft.getInstance().isPaused()) {
            if (contactTimer > 0) {
                if (contactFogLevel < 200) {
                    contactFogLevel++;
                }
                contactTimer--;
            } else if (contactFogLevel > 0) {
                contactFogLevel--;
            }
        }
    }

    public void renewContact() {
        contactTimer = 50;
    }

    public int getContactFogLevel() {
        return contactFogLevel;
    }

    public void syncPoolData(CompoundTag tag) {
        bloodPoolData = BloodPoolData.load(tag);
    }

    public BloodPoolData getBloodPoolData() {
        return bloodPoolData;
    }

    public void modifyPoolData(CompoundTag tag) {
        bloodPoolData.modifyPool(Minecraft.getInstance().level, tag);
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

