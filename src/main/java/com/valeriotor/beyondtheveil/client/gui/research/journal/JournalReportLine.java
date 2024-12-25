package com.valeriotor.beyondtheveil.client.gui.research.journal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.gui.elements.EditableList;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class JournalReportLine extends Element implements EditableList.EditableListElement, ScrollableList.NumberedListElement {

    private Type type;

    public static JournalReportLine makeReportLine(int width, int height) {
        return new JournalReportLine(width, height);
    }

    protected JournalReportLine(int width, int height) {
        super(width, height);
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
        //graphics.drawString(Minecraft.getInstance().font, "§oInjection: 75 mB Sedative", 50, 6, 0xFFE0D5B3);
        if (insideBounds(relativeMouseX, relativeMouseY)) {
            graphics.fill(34, 0, getWidth()-35, getHeight(), 0x4499875A);
        }
        //graphics.fill(52, 2, 101, getHeight() - 2, 0x88000000);
        graphics.fill(34, 0, getWidth()-35, 1, 0xFF352E1C);
        graphics.fill(34, getHeight() - 1, getWidth()-35, getHeight(), 0xFF352E1C);
        graphics.fill(34, 0, 35, getHeight(), 0xFF352E1C);
        graphics.fill(getWidth()-35-1, 0, getWidth()-35, getHeight(), 0xFF352E1C);
    }

    @Override
    public void renderIndexed(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, int index) {
        ScrollableList.NumberedListElement.super.renderIndexed(poseStack, graphics, color, relativeMouseX, relativeMouseY, index);
        graphics.drawString(Minecraft.getInstance().font, String.format("§l%d.", index + 1), 40, 8, 0xFFE0D5B3);
        /*String test = switch (index) {
            case 0 -> "Injection";
            case 2 -> "Incision";
            case 1 -> "Insertion";
            case 3 -> "Status";
            case 5 -> "Death";
            default -> "Extraction";
        };
        String test2 = switch (index) {
            case 0 -> "§o75 mB Sedative";
            case 2 -> "§oComplete";
            case 1 -> "§oPlucked Eye, Complete";
            case 3 -> "§oIn pain";
            case 5 -> "";
            default -> "§oComplete";
        };
        graphics.drawString(Minecraft.getInstance().font, test, 54, 8, 0xFFE0D5B3);
        graphics.drawString(Minecraft.getInstance().font, test2, 115, 8, 0xFFE0D5B3);*/
    }

    private void selectType(Type type) {
        this.type = type;
    }

    private enum Type {
        NONE, POSITION, EXTRACTION, INCISION, INJECTION, INSERTION, STITCHING, PAIN, DEATH
    }

}
