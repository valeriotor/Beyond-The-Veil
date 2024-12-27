package com.valeriotor.beyondtheveil.client.gui.research.journal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.gui.elements.EditableList;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import com.valeriotor.beyondtheveil.client.gui.elements.TextLine;
import com.valeriotor.beyondtheveil.client.gui.elements.property.Property;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class JournalReportLine extends Element implements EditableList.EditableListElement, ScrollableList.NumberedListElement {

    private Type type;
    private EditBox typeSelector;
    private ScrollableList<TypeOption> typeSelectorList;
    private static final int SELECTOR_LIST_LEFT_X = 54;
    private static final int SELECTOR_LIST_TOP_Y = 25;


    public static JournalReportLine makeReportLine(int width, int height) {
        return new JournalReportLine(width, height);
    }

    protected JournalReportLine(int width, int height) {
        super(width, height);
        typeSelector = new EditBox(Minecraft.getInstance().font, 54, 2, 75, getHeight()-4, Component.translatable("gui.journal.journal.select"));
        typeSelector.active = false;
        typeSelector.setValue(Type.NONE.name());
        //typeSelector.setBordered(false);
        List<TypeOption> typeLines = Arrays.stream(Type.values()).map(t -> new TypeOption(75, t, Minecraft.getInstance().font)).toList();
        typeSelectorList = new ScrollableList<>(86, 68, typeLines, 17, 10);
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
        //graphics.drawString(Minecraft.getInstance().font, "§oInjection: 75 mB Sedative", 50, 6, 0xFFE0D5B3);
        if (insideBounds(relativeMouseX, relativeMouseY)) {
            graphics.fill(52, 0, getWidth() - 53, getHeight(), 0x4499875A);
        }
        //graphics.fill(52, 2, 101, getHeight() - 2, 0x88000000);
        graphics.fill(52, 0, getWidth() - 53, 1, 0xFF352E1C);
        graphics.fill(52, getHeight() - 1, getWidth() - 53, getHeight(), 0xFF352E1C);
        graphics.fill(52, 0, 53, getHeight(), 0xFF352E1C);
        graphics.fill(getWidth() - 53 - 1, 0, getWidth() - 53, getHeight(), 0xFF352E1C);
        typeSelector.render(graphics, relativeMouseX, relativeMouseY, pPartialTick);
        if (typeSelector.isActive()) {
            poseStack.pushPose();
            poseStack.translate(SELECTOR_LIST_LEFT_X, SELECTOR_LIST_TOP_Y, 100);
            typeSelectorList.render(poseStack, graphics, color, (int) typeSelectorListMouseX(relativeMouseX), (int) typeSelectorListMouseY(relativeMouseY), pPartialTick);
            poseStack.popPose();
        }
    }

    @Override
    public void renderIndexed(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick, int index) {
        ScrollableList.NumberedListElement.super.renderIndexed(poseStack, graphics, color, relativeMouseX, relativeMouseY, pPartialTick, index);
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

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        double typeSelectorListMouseX = typeSelectorListMouseX(relativeMouseX);
        double typeSelectorListMouseY = typeSelectorListMouseY(relativeMouseY);
        if (typeSelector.isMouseOver(relativeMouseX, relativeMouseY)) {
            typeSelector.active = true;
            typeSelector.setFocused(true);
            typeSelector.setValue("");
            return true;
        } else if (typeSelector.isActive() && typeSelectorList.insideBounds(typeSelectorListMouseX, typeSelectorListMouseY)) {
            return typeSelectorList.mouseClicked(typeSelectorListMouseX, typeSelectorListMouseY, mouseButton);
        } else {
            typeSelector.active = false;
            typeSelector.setFocused(false);
        }
        //typeSelector.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
        return super.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (typeSelector.isActive()) {
            String old = typeSelector.getValue();
            System.out.println(typeSelector.isFocused());
            if (typeSelector.keyPressed(pKeyCode, pScanCode, pModifiers)) {
                String newValue = typeSelector.getValue();
                if (!Objects.equals(old, newValue)) {
                    List<TypeOption> typeLines = Arrays.stream(Type.values()).filter(t -> t.name().contains(typeSelector.getValue())).map(t -> new TypeOption(75, t, Minecraft.getInstance().font)).toList();
                    typeSelectorList.changeElements(typeLines);
                    return true;
                }
            }
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        if (typeSelector.isActive()) {
            String old = typeSelector.getValue();
            System.out.println(typeSelector.isFocused());
            if (typeSelector.charTyped(pCodePoint, pModifiers)) {
                String newValue = typeSelector.getValue();
                if (!Objects.equals(old, newValue)) {
                    List<TypeOption> typeLines = Arrays.stream(Type.values()).filter(t -> t.name().toLowerCase().contains(typeSelector.getValue().toLowerCase())).map(t -> new TypeOption(75, t, Minecraft.getInstance().font)).toList();
                    typeSelectorList.changeElements(typeLines);
                    return true;
                }
            }
        }
        return super.charTyped(pCodePoint, pModifiers);
    }

    @Override
    public boolean mouseScrolled(double relativeMouseX, double relativeMouseY, double pDelta) {
        double typeSelectorListMouseX = typeSelectorListMouseX(relativeMouseX);
        double typeSelectorListMouseY = typeSelectorListMouseY(relativeMouseY);
        if (typeSelector.isActive() && typeSelectorList.insideBounds(typeSelectorListMouseX, typeSelectorListMouseY)) {
            typeSelectorList.mouseScrolled(typeSelectorListMouseX, typeSelectorListMouseY, pDelta);
        }
        return super.mouseScrolled(relativeMouseX, relativeMouseY, pDelta);
    }

    @Override
    public boolean mouseDragged(double relativeMouseX, double relativeMouseY, int pButton, double pDragX, double pDragY) {
        double typeSelectorListMouseX = typeSelectorListMouseX(relativeMouseX);
        double typeSelectorListMouseY = typeSelectorListMouseY(relativeMouseY);
        if (typeSelector.isActive()) {
            typeSelectorList.mouseDragged(typeSelectorListMouseX, typeSelectorListMouseY, pButton, pDragX, pDragY);
        }
        return super.mouseDragged(relativeMouseX, relativeMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double relativeMouseX, double relativeMouseY, int pButton) {
        double typeSelectorListMouseX = typeSelectorListMouseX(relativeMouseX);
        double typeSelectorListMouseY = typeSelectorListMouseY(relativeMouseY);
        if (typeSelector.isActive()) {
            typeSelectorList.mouseReleased(typeSelectorListMouseX, typeSelectorListMouseY, pButton);
        }
        return super.mouseReleased(relativeMouseX, relativeMouseY, pButton);
    }

    private double typeSelectorListMouseY(double relativeMouseY) {
        return relativeMouseY - SELECTOR_LIST_TOP_Y;
    }

    private double typeSelectorListMouseX(double relativeMouseX) {
        return relativeMouseX - SELECTOR_LIST_LEFT_X;
    }

    @Override
    protected boolean insideBounds(double relativeMouseX, double relativeMouseY) {
        double typeSelectorListMouseX = typeSelectorListMouseX(relativeMouseX);
        double typeSelectorListMouseY = typeSelectorListMouseY(relativeMouseY);
        if (typeSelector.isActive() && typeSelectorList.insideBounds(typeSelectorListMouseX, typeSelectorListMouseY)) {
            return true;
        }
        return super.insideBounds(relativeMouseX, relativeMouseY);
    }

    private void selectType(Type type) {
        this.type = type;
    }

    private enum Type {
        NONE, POSITION, EXTRACTION, INCISION, INJECTION, INSERTION, STITCHING, PAIN, DEATH
    }

    private class TypeOption extends Element {

        private final String line;
        private final Type type;
        private final Font font;

        public TypeOption(int width, Type type, Font font) {
            super(width, 17);
            this.line = type.name();
            this.type = type;
            this.font = font;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            Minecraft mc = Minecraft.getInstance();
            graphics.fill(0, 0, getWidth(), getHeight(), 0xFFAAAAAA);
            graphics.fill(1, 1, getWidth() - 1, getHeight() - 1, 0xFF352E1C);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(1, 1, getWidth() - 1, getHeight() - 1, 0xFF554E3C);
            }
            graphics.drawString(font, line, 0, 1, color);
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            typeSelector.setValue(line);
            typeSelector.active = false;
            typeSelector.setFocused(false);
            selectType(type);
            return true;
        }
    }

}
