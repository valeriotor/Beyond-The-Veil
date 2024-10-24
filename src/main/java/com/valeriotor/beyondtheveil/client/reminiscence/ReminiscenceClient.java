package com.valeriotor.beyondtheveil.client.reminiscence;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.KeyBindings;
import com.valeriotor.beyondtheveil.client.event.RenderEvents;
import com.valeriotor.beyondtheveil.client.util.DataUtilClient;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.dreaming.dreams.Reminiscence;
import com.valeriotor.beyondtheveil.dreaming.dreams.ReminiscenceUnderground;
import com.valeriotor.beyondtheveil.dreaming.dreams.ReminiscenceWaypoint;
import com.valeriotor.beyondtheveil.networking.GenericToServerPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public abstract class ReminiscenceClient {

    private static int reminisceTimePressed = 0;
    private static boolean preventScreenOpen = false;
    private static int selectedReminiscence = -1;
    private static final List<ReminiscenceClient> reminiscences = new ArrayList<>();

    protected abstract void render(RenderGuiOverlayEvent event);
    protected void tick() {}
    protected void reset() {}
    protected void mouseScroll(InputEvent.MouseScrollingEvent event) {}

    public static void renderReminiscence(RenderGuiOverlayEvent  event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (reminisceTimePressed > 0) {
                PoseStack poseStack = event.getGuiGraphics().pose();
                poseStack.pushPose();
                Matrix4f matrix4f = poseStack.last().pose();
                Window window = Minecraft.getInstance().getWindow();
                int alpha = reminisceTimePressed >= 20 ? 255 : (int) (Math.pow((reminisceTimePressed - 1 + event.getPartialTick()) / 40, 2) * 255);

                RenderEvents.innerFill(matrix4f, 0, 0, window.getGuiScaledWidth(), window.getGuiScaledHeight(), alpha << 24);

                if (!reminiscences.isEmpty() && reminisceTimePressed >= 20) {
                    reminiscences.get(selectedReminiscence).render(event);
                }

                poseStack.popPose();
            }
        }

    }


    public static void tickEvent(TickEvent.ClientTickEvent event) {

        if (reminisceTimePressed < 20) {
            if (KeyBindings.reminisce.isDown() && Minecraft.getInstance().screen == null) {
                reminisceTimePressed++;
                preventScreenOpen = false;
                if (reminisceTimePressed == 20) {
                    Messages.sendToServer(new GenericToServerPacket(GenericToServerPacket.MessageType.REMINISCING_START));
                    if (!reminiscences.isEmpty()) {
                        reminiscences.get(selectedReminiscence).reset();
                    }
                }
            } else if (reminisceTimePressed > 0) {
                reminisceTimePressed--;
                if (reminisceTimePressed < 15) {
                    preventScreenOpen = false;
                }
            }
        } else if (reminiscences.size() > 0) {
            reminiscences.get(selectedReminiscence).tick();
        }
    }

    public static void keyInputEvent(InputEvent.Key event) {
        if(event.getAction() == GLFW.GLFW_PRESS && reminisceTimePressed >= 20) {
            if ((event.getKey() == GLFW.GLFW_KEY_ESCAPE || event.getKey() == Minecraft.getInstance().options.keyInventory.getKey().getValue())) {
                reminisceTimePressed = 19;
                Minecraft.getInstance().setScreen(null);
                preventScreenOpen = true;
                Messages.sendToServer(new GenericToServerPacket(GenericToServerPacket.MessageType.REMINISCING_STOP));
            } else if (event.getKey() == KeyBindings.reminisce.getKey().getValue()) {
                if (reminiscences.size() > 1) {
                    reminiscences.get(selectedReminiscence).reset();
                    selectedReminiscence++;
                    selectedReminiscence %= reminiscences.size();
                }
            }
        }
    }

    public static void mouseScrollEvent(InputEvent.MouseScrollingEvent event) {
        if (reminiscences.size() > 0) {
            reminiscences.get(selectedReminiscence).mouseScroll(event);
        }
    }

    public static void screenOpenEvent(ScreenEvent.Opening event) {
        if (preventScreenOpen && event.getScreen() != null) {
            event.setCanceled(true);
            preventScreenOpen = false;
        }
    }

    /** Should be called everytime reminiscences change, i.e. every time NBT data is loaded or synced
     */
    public static void reloadReminiscences() {
        EnumMap<Memory, Reminiscence> reminiscenceMap = DataUtil.getReminiscences(DataUtilClient.getPlayer());
        reminiscences.clear();
        for (Map.Entry<Memory, Reminiscence> e : reminiscenceMap.entrySet().stream().sorted(Comparator.comparingInt(entry -> entry.getKey().ordinal())).toList()) {
            ReminiscenceClient r;
            if (e.getValue() instanceof ReminiscenceUnderground ru) {
                r = new ReminiscenceClientUnderground(ru);
            } else if (e.getValue() instanceof ReminiscenceWaypoint rw) {
                r = new ReminiscenceClientWaypoint(rw);
            } else if (e.getValue() instanceof Reminiscence.EmptyReminiscence rw) {
                r = new EmptyReminiscenceClient();
            } else {
                throw new IllegalArgumentException("Unknown reminiscence " + e.getValue().toString());
            }
            reminiscences.add(r);
        }
        if (reminiscences.size() > 0) {
            selectedReminiscence = 0;
        } else {
            selectedReminiscence = -1;
        }

    }

    public static class EmptyReminiscenceClient extends ReminiscenceClient {

        private int counter = 1;
        @Override
        protected void render(RenderGuiOverlayEvent event) {
            String translatable = Component.translatable("reminiscence.EMPTY").getString();
            String sub = translatable.substring(0, Math.min(counter, translatable.length()));
            int guiScaledWidth = event.getWindow().getGuiScaledWidth();
            int guiScaledHeight = event.getWindow().getGuiScaledHeight();
            GuiGraphics guiGraphics = event.getGuiGraphics();
            PoseStack pose = guiGraphics.pose();
            pose.pushPose();
            pose.translate(guiScaledWidth / 2, guiScaledHeight / 2, 0);
            float factor = guiScaledWidth / 300F;
            List<FormattedCharSequence> split = Minecraft.getInstance().font.split(Component.literal(sub), (int) (guiScaledWidth / factor));
            pose.scale(factor, factor, 1);
            for (int i = 0; i < split.size(); i++) {
                guiGraphics.drawCenteredString(Minecraft.getInstance().font, split.get(i), 0, 20 * i - 10 * (split.size() - 1), 0xFFFFFFFF);
            }
            pose.popPose();
        }

        @Override
        protected void tick() {
            counter++;
        }
    }



}
