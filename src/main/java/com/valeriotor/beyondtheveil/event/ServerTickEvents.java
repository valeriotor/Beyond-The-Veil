package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.world.dimension.ArcheCycleData;
import com.valeriotor.beyondtheveil.world.dimension.BTVDimensions;
import com.valeriotor.beyondtheveil.world.saved.LifeEconomyData;
import com.valeriotor.beyondtheveil.world.saved.PlayerSavedData;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerTickEvents {

    static long counter = 0;

    @SubscribeEvent
    public static void tickEvent(TickEvent.ServerTickEvent event) {
        counter++;
        if ((counter & 255) == 0) {
            //HeartBE.cleanDamned();
        }
    }

    @SubscribeEvent
    public static void levelTickEvent(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (event.level.dimension() == BTVDimensions.ARCHE_LEVEL && event.level instanceof ServerLevel sl) {
                ArcheCycleData arche = sl.getDataStorage().computeIfAbsent(ArcheCycleData::new, ArcheCycleData::new, "arche");
                arche.tick(true);
                if ((arche.getCycle() & 4095) == 0) {
                    Messages.sendToDimension(GenericToClientPacket.syncArcheData(arche), BTVDimensions.ARCHE_LEVEL);
                }
            }
        }
        if (event.level instanceof ServerLevel sl) {
            LifeEconomyData.getInstance(sl).tick();
            if (event.phase == TickEvent.Phase.END) {
                PlayerSavedData.getInstance(sl).tick(sl);
            }
        }
    }

}
