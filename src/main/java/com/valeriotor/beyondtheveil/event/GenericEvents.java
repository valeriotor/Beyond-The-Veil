package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.commands.BTVDataCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GenericEvents {

    @SubscribeEvent
    public static void registerCommandsEvent(RegisterCommandsEvent event) {
        BTVDataCommand.register(event.getDispatcher());
    }

}
