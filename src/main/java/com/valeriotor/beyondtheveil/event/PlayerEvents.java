package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {

    @SubscribeEvent
    public static void wakeUpEvent(PlayerWakeUpEvent event) {
        Player p = event.getPlayer();
        if (p != null && !p.level.isClientSide() && !event.wakeImmediately() && p.level.getDayTime() > 23900) {
            // TODO Choose Dream
            //
            if(ResearchUtil.getResearchStage(p, "FIRSTDREAMS") == 0)
                DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.DIDDREAM, true, false);
        }
    }

    @SubscribeEvent
    public static void changeDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player p = event.getPlayer();

        if (event.getFrom() == Level.NETHER && event.getTo() == Level.OVERWORLD && !DataUtil.getBoolean(p, PlayerDataLib.THEBEGINNING)) {
            boolean added = p.addItem(new ItemStack(Registration.NECRONOMICON.get()));
            if(added) {
                DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.THEBEGINNING, true, false);
                p.sendMessage(new TranslatableComponent("beginning.netherreturn"), p.getUUID());
            }
        }
    }


}
