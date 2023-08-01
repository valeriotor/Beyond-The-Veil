package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.EnderManAngerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LivingEvents {

    @SubscribeEvent
    public static void endermanAngerEvent(EnderManAngerEvent event) {
        Player p = event.getPlayer();
        if (p != null) {
            if (DataUtil.getBoolean(p, PlayerDataLib.REMINISCING)) {
                event.setCanceled(true);
            }
        }
    }


}
