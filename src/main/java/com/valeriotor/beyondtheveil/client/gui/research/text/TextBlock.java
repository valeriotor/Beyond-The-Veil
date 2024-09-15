package com.valeriotor.beyondtheveil.client.gui.research.text;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class TextBlock extends Element{

    private final List<? extends Element> lines;
    private final Font f;

    public TextBlock(String localized, int width, int height, Font f) {
        this(TextUtil.parseText(localized, width, f), width, height, f);
    }

    public TextBlock(List<TextLine> lines, int width, int height, Font f) {
        super(width, height);
        this.lines = lines;
        this.f = f;
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
        int yOffset = 0;
        for (int i = 0; i < lines.size(); i++) {
            poseStack.pushPose();
            poseStack.translate(0, yOffset, 0);
            lines.get(i).render(poseStack, graphics, color, relativeMouseX, relativeMouseY - 15 * i);
            yOffset += lines.get(i).getHeight();
            poseStack.popPose();
        }
    }

}
