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
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class JournalReportLine extends Element implements EditableList.EditableListElement, ScrollableList.NumberedListElement {

    private Type type = Type.NONE;
    private final int typeSelectorWidth;
    private final EditBox typeSelector;
    private final ScrollableList<TypeOption> typeSelectorList;
    private static final int SELECTOR_LIST_LEFT_X = 54;
    private static final int SELECTOR_LIST_TOP_Y = 25;


    public static JournalReportLine makeReportLine(int width, int height) {
        return new JournalReportLine(width, height);
    }

    protected JournalReportLine(int width, int height) {
        super(width, height);
        typeSelectorWidth = Arrays.stream(Type.values()).map(t -> Minecraft.getInstance().font.width(t.getText())).max(Comparator.comparingInt(i -> i)).orElse(75) + 10;
        typeSelector = new EditBox(Minecraft.getInstance().font, SELECTOR_LIST_LEFT_X, 8, typeSelectorWidth, 15, Component.translatable("gui.journal.journal.type.none"));
        typeSelector.active = false;
        typeSelector.setValue(Type.NONE.getText().getString());
        typeSelector.setBordered(false);
        List<TypeOption> typeLines = Arrays.stream(Type.values()).map(t -> new TypeOption(typeSelectorWidth, t, Minecraft.getInstance().font, typeSelector, this::selectType)).toList();
        typeSelectorList = new ScrollableList<>(86, 68, typeLines, 17, 10);
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
        //graphics.drawString(Minecraft.getInstance().font, "§oInjection: 75 mB Sedative", 50, 6, 0xFFE0D5B3);
        if (insideBounds(relativeMouseX, relativeMouseY)) {
            graphics.fill(52, 0, getWidth() - 53, getHeight(), 0x4499875A);
        }
        graphics.fill(SELECTOR_LIST_LEFT_X - 3, 1, SELECTOR_LIST_LEFT_X + typeSelectorWidth, getHeight() - 1, 0xFF7D633F);
        graphics.fill(SELECTOR_LIST_LEFT_X + typeSelectorWidth, 1, SELECTOR_LIST_LEFT_X + typeSelectorWidth + 1, getHeight() - 1, 0xFF352E1C);
        //graphics.fill(52, 2, 101, getHeight() - 2, 0x88000000);
        graphics.fill(52, 0, getWidth() - 53, 1, 0xFF352E1C);
        graphics.fill(52, getHeight() - 1, getWidth() - 53, getHeight(), 0xFF352E1C);
        graphics.fill(52, 0, 53, getHeight(), 0xFF352E1C);
        graphics.fill(getWidth() - 53 - 1, 0, getWidth() - 53, getHeight(), 0xFF352E1C);
        typeSelector.render(graphics, relativeMouseX, relativeMouseY, pPartialTick);
        poseStack.pushPose();
        poseStack.translate(SELECTOR_LIST_LEFT_X + typeSelectorWidth - 7, 0.5, 0);
        poseStack.scale(2, 2, 1);
        graphics.drawString(Minecraft.getInstance().font, "⌄", 0, 0, 0xFFFFFFFF);
        poseStack.popPose();
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
            typeSelector.setValue(type.getText().getString());
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
                    List<TypeOption> typeLines = Arrays.stream(Type.values()).filter(filterText()).map(t -> new TypeOption(typeSelectorWidth, t, Minecraft.getInstance().font, typeSelector, this::selectType)).toList();
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
                    List<TypeOption> typeLines = Arrays.stream(Type.values()).filter(filterText()).map(t -> new TypeOption(typeSelectorWidth, t, Minecraft.getInstance().font, typeSelector, this::selectType)).toList();
                    typeSelectorList.changeElements(typeLines);
                    return true;
                }
            }
        }
        return super.charTyped(pCodePoint, pModifiers);
    }

    @NotNull
    private Predicate<Type> filterText() {
        return t -> t.getText().getString().toLowerCase().contains(typeSelector.getValue().toLowerCase());
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

    private void selectType(Option type) {
        if (type instanceof Type t) {
            this.type = t;
        }
    }

    private interface Option {
        Component getText();
    }


    private enum Type implements Option {
        NONE, POSITION, EXTRACTION, INCISION, INJECTION, INSERTION, STITCHING, PAIN, DEATH;

        public Component getText() {
            return Component.translatable("gui.journal.journal.type." + name().toLowerCase());
        }

    }

    private static class TypeOption extends Element {

        private final String line;
        private final Option type;
        private final Font font;
        private final EditBox typeSelector;
        private final Consumer<Option> optionConsumer;

        public TypeOption(int width, Option type, Font font, EditBox typeSelector, Consumer<Option> optionConsumer) {
            super(width, 17);
            this.line = type.getText().getString();
            this.type = type;
            this.font = font;
            this.typeSelector = typeSelector;
            this.optionConsumer = optionConsumer;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            Minecraft mc = Minecraft.getInstance();
            graphics.fill(0, 0, getWidth(), getHeight(), 0xFFAAAAAA);
            graphics.fill(1, 1, getWidth() - 1, getHeight() - 1, 0xFF352E1C);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(1, 1, getWidth() - 1, getHeight() - 1, 0xFF554E3C);
            }
            graphics.drawString(font, line, 2, 1, color);
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            typeSelector.setValue(line);
            typeSelector.active = false;
            typeSelector.setFocused(false);
            optionConsumer.accept(type);
            return true;
        }
    }

    private class PositionEntry {
    }


}
