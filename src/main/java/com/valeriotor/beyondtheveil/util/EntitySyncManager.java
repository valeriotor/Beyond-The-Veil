package com.valeriotor.beyondtheveil.util;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntitySyncManager {
    // TODO finish this

    private static final Map<Level, Map<Integer, SynchedData>> data = new HashMap<>();

    @SubscribeEvent
    public static void entityJoinEvent(EntityJoinLevelEvent event) {

    }

    @SubscribeEvent
    public static void serverCloseEvent(ServerStoppedEvent event) {

    }

    public static class SynchedData {

        private final List<DataHolder> data = new ArrayList<>();

    }

    private static class DataHolder {
    }



}
