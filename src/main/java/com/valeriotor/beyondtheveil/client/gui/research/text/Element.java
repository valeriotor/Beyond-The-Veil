package com.valeriotor.beyondtheveil.client.gui.research.text;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

public abstract class Element {

    protected final int width;
    protected final int height;

    protected Element(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
