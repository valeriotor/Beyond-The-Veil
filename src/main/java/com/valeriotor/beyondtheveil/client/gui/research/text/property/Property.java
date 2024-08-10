package com.valeriotor.beyondtheveil.client.gui.research.text.property;

import net.minecraft.client.gui.GuiGraphics;

public abstract class Property {

    private final int xStart;
    private final int xEnd;

    protected Property(int xStart, int xEnd) {
        this.xStart = xStart;
        this.xEnd = xEnd;
    }

    public int getXStart() {
        return xStart;
    }

    public int getXEnd() {
        return xEnd;
    }

    public abstract void render(GuiGraphics graphics, int relativeMouseX, int relativeMouseY);


}
