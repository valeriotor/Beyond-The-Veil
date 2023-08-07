package com.valeriotor.beyondtheveil.client.reminiscence;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.client.gui.research.NecronomiconGui;
import com.valeriotor.beyondtheveil.dreaming.dreams.ReminiscenceWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class ReminiscenceClientWaypoint extends ReminiscenceClient{

    private final ReminiscenceWaypoint reminiscenceWaypoint;

    public ReminiscenceClientWaypoint(ReminiscenceWaypoint reminiscenceWaypoint) {
        this.reminiscenceWaypoint = reminiscenceWaypoint;
    }

    @Override
    protected void render(RenderGameOverlayEvent event) {
        PoseStack poseStack = event.getMatrixStack();
        float partialTicks = event.getPartialTicks();
        LocalPlayer player = Minecraft.getInstance().player;
        Window window = Minecraft.getInstance().getWindow();
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, NecronomiconGui.RESEARCH_HIGHLIGHT);
        GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
        int color = reminiscenceWaypoint.getColor();
        float r = (color >> 16) / 255F, g = ((color >> 8) & 255) / 255F, b = (color & 255) / 255F;

        // Note: this does not account for FOV changes such as sprinting, nor effects such as bobbing. It only takes into account the FOV value from the options
        Vec3 vec3 = gameRenderer.getMainCamera().getPosition();
        BlockPos pos = reminiscenceWaypoint.getPos();
        Quaternion q = gameRenderer.getMainCamera().rotation().copy();
        q.conj();
        Vector3f vector = new Vector3f((float) (pos.getX() - vec3.x), (float) (pos.getY() - vec3.y), (float) (pos.getZ() - vec3.z));
        vector.transform(q);
        double distanceFromScreen = window.getGuiScaledHeight() / Math.tan((Minecraft.getInstance().options.fov / 2) * Math.PI / 180);
        double scaleFactor = distanceFromScreen / vector.z() / 2; // I should have divided the camera angle by two I guess, but idk how to do it so this works too, in this context at least
        int x = (int) -(vector.x() * scaleFactor);
        int y = (int) -(vector.y() * scaleFactor);
        if (vector.z() >= 0) {
            RenderSystem.setShaderColor(1,1,1,1);
            float ticks = player.tickCount % 20 + partialTicks;
            while (ticks < 70) {
                poseStack.pushPose();
                poseStack.translate(x + window.getGuiScaledWidth() / 2 - 12 * (ticks) * 30/ 70, y + window.getGuiScaledHeight() / 2 - 12 * (ticks) * 30/ 70, 0);
                poseStack.scale(ticks * 30/ 70, ticks * 30 / 70, 1);
                RenderSystem.setShaderColor(r,g,b,1 - ticks / 70);
                GuiComponent.blit(poseStack, 0,0, 0, 0, 24, 24, 24, 24);
                poseStack.popPose();
                ticks += 20;
            }
        }


    }
}
