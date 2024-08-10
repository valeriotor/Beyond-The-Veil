package com.valeriotor.beyondtheveil.client.gui.research.text.property;

import net.minecraft.client.gui.GuiGraphics;

public class LinkProperty extends Property {

    private final String link;

    public LinkProperty(int xStart, int xEnd, String link) {
        super(xStart, xEnd);
        this.link = link;
    }

    @Override
    public void render(GuiGraphics graphics, int relativeMouseX, int relativeMouseY) {

    }
}
