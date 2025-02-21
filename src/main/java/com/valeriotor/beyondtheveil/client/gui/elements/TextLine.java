package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.gui.elements.property.Property;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class TextLine extends Element{

    private final FormattedCharSequence text;
    private final List<Property> properties;
    private final boolean alignRight;
    private int start;

    public TextLine(FormattedCharSequence text, List<Property> properties, Font font) {
        this(text, properties, font, false, font.width(text));
    }

    public TextLine(FormattedCharSequence text, List<Property> properties, Font font, boolean alignRight, int fullWidth) {
        super(font.width(text), 15);
        this.text = text;
        this.properties = properties;
        this.alignRight = alignRight;
        if (alignRight) {
            start = fullWidth - getWidth();
        }
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics guiGraphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
        Minecraft mc = Minecraft.getInstance();
        if (alignRight) {
            poseStack.pushPose();
            poseStack.translate(start, 0, 0);
            relativeMouseX -= start;
        }
        guiGraphics.drawString(mc.font, text, 0, 0, color);
        if (relativeMouseY >= 0 && relativeMouseY < 15) {
            for (Property property : properties) {
                if (relativeMouseX >= property.getXStart() && relativeMouseX < property.getXEnd()) {
                    property.render(guiGraphics, relativeMouseX, relativeMouseY);
                }
            }
        }
        if (alignRight) {
            poseStack.popPose();
        }
        //for (Property property : properties) {
        //    guiGraphics.fill(property.getXStart(), 0, property.getXEnd(), 15, 0xFFFF0000);
        //}
    }

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        if (alignRight) {
            relativeMouseX -= start;
        }
        if (relativeMouseY >= 0 && relativeMouseY < 15) {
            for (Property property : properties) {
                if (relativeMouseX >= property.getXStart() && relativeMouseX < property.getXEnd()) {
                    if (property.mouseClicked(relativeMouseX - property.getXStart(), relativeMouseY, mouseButton)) {
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
    }
}
