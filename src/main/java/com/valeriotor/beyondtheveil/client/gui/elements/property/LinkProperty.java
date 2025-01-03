package com.valeriotor.beyondtheveil.client.gui.elements.property;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class LinkProperty extends Property {

    private final String link;
    private final Component text;

    public LinkProperty(int xStart, int xEnd, String link) {
        super(xStart, xEnd);
        this.link = link;
        this.text = text(link);
    }

    private Component text(String link) {
        if (link.startsWith("crafting")) {
            return Component.translatable("caption.crafting");
        } else if (link.startsWith("journal")) {
            return Component.translatable("caption.journal");
        }
        return Component.translatable("link." + link);
    }

    @Override
    public void render(GuiGraphics graphics, int relativeMouseX, int relativeMouseY) {
        graphics.renderTooltip(Minecraft.getInstance().font, text, relativeMouseX, relativeMouseY);
    }

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        // TODO
        return true;
    }
}
