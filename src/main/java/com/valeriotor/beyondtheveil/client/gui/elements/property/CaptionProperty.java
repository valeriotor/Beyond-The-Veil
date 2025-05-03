package com.valeriotor.beyondtheveil.client.gui.elements.property;

import com.valeriotor.beyondtheveil.client.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class CaptionProperty extends Property {

    private final Component caption;

    public CaptionProperty(int xStart, int xEnd, String caption) {
        super(xStart, xEnd);
        caption = "caption." + caption;
        if (caption.equals("caption.reminisce")) {
            this.caption = Component.translatable(caption, KeyBindings.reminisce.getTranslatedKeyMessage().getString());
        } else {
            this.caption = Component.translatable(caption);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int relativeMouseX, int relativeMouseY) {
        graphics.renderTooltip(Minecraft.getInstance().font, caption, relativeMouseX, relativeMouseY);
    }
}
