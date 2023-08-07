package com.valeriotor.beyondtheveil.client.event;

import com.valeriotor.beyondtheveil.client.KeyBindings;
import com.valeriotor.beyondtheveil.client.util.DataUtilClient;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.dreaming.dreams.Reminiscence;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToServerPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.EnumMap;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InputEvents {

    private static int reminisceTimePressed = 0;
    private static boolean preventScreenOpen = false;
    private static Reminiscence selectedReminiscence;

    @SubscribeEvent
    public static void clientTickEvent(TickEvent.ClientTickEvent event) {
        if (reminisceTimePressed < 20) {
            if (KeyBindings.reminisce.isDown() && Minecraft.getInstance().screen == null) {
                reminisceTimePressed++;
                preventScreenOpen = false;
                if (reminisceTimePressed == 20) {
                    Messages.sendToServer(new GenericToServerPacket(GenericToServerPacket.MessageType.REMINISCING_START));
                    EnumMap<Memory, Reminiscence> reminiscences = DataUtil.getReminiscences(DataUtilClient.getPlayer());
                    if (!reminiscences.isEmpty()) {
                        selectedReminiscence = reminiscences.get(reminiscences.keySet().stream().sorted().toList().get(0));
                    }
                }
            } else if (reminisceTimePressed > 0) {
                reminisceTimePressed--;
                if (reminisceTimePressed < 15) {
                    preventScreenOpen = false;
                }
            }
        }
    }

    @SubscribeEvent
    public static void keyInputEvent(InputEvent.KeyInputEvent event) {
        if ((event.getKey() == GLFW.GLFW_KEY_ESCAPE || event.getKey() == Minecraft.getInstance().options.keyInventory.getKey().getValue()) && event.getAction() == GLFW.GLFW_PRESS && reminisceTimePressed >= 20) {
            reminisceTimePressed = 19;
            selectedReminiscence = null;
            Minecraft.getInstance().setScreen(null);
            preventScreenOpen = true;
            Messages.sendToServer(new GenericToServerPacket(GenericToServerPacket.MessageType.REMINISCING_STOP));
        }
    }

    @SubscribeEvent
    public static void screenOpenEvent(ScreenOpenEvent event) {
        if (preventScreenOpen && event.getScreen() != null) {
            event.setCanceled(true);
            preventScreenOpen = false;
        }
    }

    public static int getReminisceTimePressed() {
        return reminisceTimePressed;
    }

    public static Reminiscence getSelectedReminiscence() {
        return selectedReminiscence;
    }
}
