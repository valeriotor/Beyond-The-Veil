package com.valeriotor.beyondtheveil.client.reminiscence;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.gui.research.NecronomiconGui;
import com.valeriotor.beyondtheveil.client.util.DataUtilClient;
import com.valeriotor.beyondtheveil.dreaming.dreams.ReminiscenceWaypoint;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ReminiscenceClientWaypoint extends ReminiscenceClient.TextReminiscenceClient {

    private final ReminiscenceWaypoint reminiscenceWaypoint;
    private static final ResourceLocation MOUSE_MOVE = new ResourceLocation(References.MODID, "textures/gui/mouse_move.png");
    private boolean movedMouse = false; // just to avoid constant string checks in PlayerData

    public ReminiscenceClientWaypoint(ReminiscenceWaypoint reminiscenceWaypoint) {
        super(Component.translatable("reminiscence.waypoint_other_dimension"));
        this.reminiscenceWaypoint = reminiscenceWaypoint;
        if (Minecraft.getInstance().player != null) {
            movedMouse = DataUtil.getBoolean(Minecraft.getInstance().player, PlayerDataLib.moved_waypoint.name());
        }
    }

    @Override
    protected void render(RenderGuiOverlayEvent event) {
        if (Minecraft.getInstance().level != null && reminiscenceWaypoint.getDimension() != Minecraft.getInstance().level.dimension()) {
            super.render(event);
        } else {
            GuiGraphics guiGraphics = event.getGuiGraphics();
            PoseStack poseStack = guiGraphics.pose();
            float partialTicks = event.getPartialTick();
            LocalPlayer player = Minecraft.getInstance().player;
            Window window = Minecraft.getInstance().getWindow();
            if (player == null) {
                return;
            }
            RenderSystem.enableBlend();
            GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
            int color = reminiscenceWaypoint.getColor();
            float r = (color >> 16) / 255F, g = ((color >> 8) & 255) / 255F, b = (color & 255) / 255F;

            // Note: this does not account for FOV changes such as sprinting, nor effects such as bobbing. It only takes into account the FOV value from the options
            Vec3 vec3 = gameRenderer.getMainCamera().getPosition();
            BlockPos pos = reminiscenceWaypoint.getPos();
            Quaternionf q = new Quaternionf(gameRenderer.getMainCamera().rotation());
            q.conjugate();
            Vector3f vector = new Vector3f((float) (pos.getX() - vec3.x), (float) (pos.getY() - vec3.y), (float) (pos.getZ() - vec3.z));
            vector.rotate(q);
            double distanceFromScreen = window.getGuiScaledHeight() / Math.tan((Minecraft.getInstance().options.fov().get() / 2F) * Math.PI / 180);
            double scaleFactor = distanceFromScreen / vector.z() / 2; // I should have divided the camera angle by two I guess, but idk how to do it so this works too, in this context at least
            int x = (int) -(vector.x() * scaleFactor);
            int y = (int) -(vector.y() * scaleFactor);
            if (vector.z() >= 0) {
                guiGraphics.setColor(1, 1, 1, 1);
                float ticks = player.tickCount % 20 + partialTicks;
                while (ticks < 70) {
                    poseStack.pushPose();
                    poseStack.translate(x + window.getGuiScaledWidth() / 2D - 12 * (ticks) * 30 / 70, y + window.getGuiScaledHeight() / 2D - 12 * (ticks) * 30 / 70, 0);
                    poseStack.scale(ticks * 30 / 70, ticks * 30 / 70, 1);
                    guiGraphics.setColor(r, g, b, 1 - ticks / 70);
                    guiGraphics.blit(NecronomiconGui.RESEARCH_HIGHLIGHT, 0, 0, 0, 0, 24, 24, 24, 24);
                    poseStack.popPose();
                    ticks += 20;
                }
            }
            showHint(guiGraphics, poseStack, player, window, x, y, vector.z() >= 0);
        }


    }

    private void showHint(GuiGraphics guiGraphics, PoseStack poseStack, LocalPlayer player, Window window, int x, int y, boolean z) {
        guiGraphics.setColor(1, 1, 1, 1);
        if (!movedMouse) {
            if (!z || Mth.abs(x) > window.getGuiScaledWidth() / 2 || Mth.abs(y) > window.getGuiScaledHeight() / 2) {
                poseStack.pushPose();
                poseStack.translate(52, window.getGuiScaledHeight() - 52, 0);
                guiGraphics.blit(MOUSE_MOVE, -48, -48, 0, 0, 96, 96, 96, 96);
                poseStack.popPose();
            } else {
                boolean flag = DataUtil.getBoolean(player, PlayerDataLib.moved_waypoint.name());
                if (!flag) {
                    DataUtilClient.setBooleanAndSync(PlayerDataLib.moved_waypoint.name(), true, false);
                }
                movedMouse = true;
            }
        }
    }
}
