package com.valeriotor.beyondtheveil.client.gui.research.text.property;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class CaptionProperty extends Property {

    private final String caption;

    public CaptionProperty(int xStart, int xEnd, String caption) {
        super(xStart, xEnd);
        this.caption = caption;
    }

    @Override
    public void render(GuiGraphics graphics, int relativeMouseX, int relativeMouseY) {
        graphics.renderTooltip(Minecraft.getInstance().font, Component.literal(caption), relativeMouseX, relativeMouseY);
    }
}
