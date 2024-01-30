package com.valeriotor.beyondtheveil.client.event;

import com.valeriotor.beyondtheveil.client.reminiscence.ReminiscenceClient;
import com.valeriotor.beyondtheveil.entity.NautilusEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {

    @SubscribeEvent
    public static void clientTickEvent(TickEvent.ClientTickEvent event) {
        ReminiscenceClient.tickEvent(event);
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.getVehicle() instanceof NautilusEntity nautilus && event.phase == TickEvent.Phase.END) {
                nautilus.setInput(player.input.left, player.input.right, player.input.up, player.input.down, player.input.jumping);
            }
        }
    }

    @SubscribeEvent
    public static void keyInputEvent(InputEvent.Key event) {
        ReminiscenceClient.keyInputEvent(event);
    }


    @SubscribeEvent
    public static void mouseScrollEvent(InputEvent.MouseScrollingEvent event) {
        ReminiscenceClient.mouseScrollEvent(event);
    }

    @SubscribeEvent
    public static void screenOpenEvent(ScreenEvent.Opening event) {
        ReminiscenceClient.screenOpenEvent(event);
    }

}
