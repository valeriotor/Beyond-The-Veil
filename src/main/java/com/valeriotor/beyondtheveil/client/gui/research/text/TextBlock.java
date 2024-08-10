package com.valeriotor.beyondtheveil.client.gui.research.text;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class TextBlock {

    private final List<TextLine> lines;
    private final Font f;
    private final int width;
    private final int height;

    public TextBlock(String localized, int width, int height, Font f) {
        this(TextUtil.parseText(localized, width, f), width, height, f);
    }

    public TextBlock(List<TextLine> lines, int width, int height, Font f) {
        this.width = width;
        this.height = height;
        this.lines = lines;
        this.f = f;
    }

    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
        for (int i = 0; i < lines.size(); i++) {
            poseStack.pushPose();
            poseStack.translate(0, i * 15, 0);
            lines.get(i).render(graphics, color, relativeMouseX, relativeMouseY - 15 * i);
            poseStack.popPose();
        }
    }

}
