package com.valeriotor.beyondtheveil.client.gui.research.text;

import com.valeriotor.beyondtheveil.client.gui.research.text.property.Property;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class TextLine {

    private final FormattedCharSequence text;
    private final List<Property> properties;

    public TextLine(FormattedCharSequence text, List<Property> properties) {
        this.text = text;
        this.properties = properties;
    }

    public void render(GuiGraphics guiGraphics, int color, int relativeMouseX, int relativeMouseY) {
        Minecraft mc = Minecraft.getInstance();
        guiGraphics.drawString(mc.font, text, 0, 0, color);
        if (relativeMouseY >= 0 && relativeMouseY < 15) {
            for (Property property : properties) {
                if (relativeMouseX >= property.getXStart() && relativeMouseX < property.getXEnd()) {
                    property.render(guiGraphics, relativeMouseX, relativeMouseY);
                }
            }
        }
        //for (Property property : properties) {
        //    guiGraphics.fill(property.getXStart(), 0, property.getXEnd(), 15, 0xFFFF0000);
        //}
    }



}
