package com.valeriotor.beyondtheveil.client.gui;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.event.RenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;

public class MetamorphosisGui extends Screen {
    private static final int WAIT_TICKS = 20;
    private static final int FADE_TICKS = 40;
    private static final int TRANSFORMATION_TICKS = 300;
    private int counter = 0;

    public MetamorphosisGui() {
        super(Component.translatable("gui.metamorphosis.title"));
    }

    @Override
    public void tick() {
        counter++;
        if (counter >= WAIT_TICKS + FADE_TICKS * 2 + TRANSFORMATION_TICKS) {
            onClose();
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        PoseStack poseStack = pGuiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(0, 0, 10);
        Matrix4f matrix4f = poseStack.last().pose();
        Window window = Minecraft.getInstance().getWindow();
        int alpha;
        if (counter < WAIT_TICKS) {
            alpha = 0;
        } else if (counter < WAIT_TICKS + FADE_TICKS) {
            alpha = (int) (Math.pow((counter - WAIT_TICKS) / (double) FADE_TICKS, 2) * 255);
        } else if (counter < WAIT_TICKS + FADE_TICKS + TRANSFORMATION_TICKS) {
            alpha = 255;
        } else {
            alpha = (int) (Math.pow(FADE_TICKS - (counter - WAIT_TICKS - FADE_TICKS - TRANSFORMATION_TICKS) / (double) FADE_TICKS, 2) * 255);
        }
        RenderEvents.innerFill(matrix4f, 0, 0, window.getGuiScaledWidth(), window.getGuiScaledHeight(), alpha << 24);

        poseStack.popPose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        return false;
    }
}
