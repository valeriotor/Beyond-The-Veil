package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tile.HeartBE;
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
            HeartBE.cleanDamned();
        }
    }

}
