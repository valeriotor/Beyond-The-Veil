package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EditableDropdownBox<T extends EditableDropdownBox.Option> extends Element {

    private T chosen;
    private final EditBox typeSelector;
    private final T[] originalOptions;
    private final int backgroundColor;
    private final int listFrameColor;
    private final int listBackgroundColor;
    private final int listBackgroundHighlightColor;
    private final ScrollableList<TypeOption<T>> typeSelectorList;
    private Consumer<T> onSelect;

    public static <T extends Option> EditableDropdownBox<T> makeBox(T[] options, int height, int stringY, int backgroundColor, int listFrameColor, int listBackgroundColor, int listBackgroundHighlightColor) {
        int width = Arrays.stream(options).map(t -> Minecraft.getInstance().font.width(t.getText())).max(Comparator.comparingInt(i -> i)).orElse(75) + 13;
        return new EditableDropdownBox<>(width, height, stringY, options, backgroundColor, listFrameColor, listBackgroundColor, listBackgroundHighlightColor);
    }

    private EditableDropdownBox(int width, int height, int stringY, T[] options, int backgroundColor, int listFrameColor, int listBackgroundColor, int listBackgroundHighlightColor) {
        super(width, height);
        typeSelector = new EditBox(Minecraft.getInstance().font, 3, stringY, getWidth(), 15, options[0].getText());
        originalOptions = options;
        this.backgroundColor = backgroundColor;
        this.listFrameColor = listFrameColor;
        this.listBackgroundColor = listBackgroundColor;
        this.listBackgroundHighlightColor = listBackgroundHighlightColor;
        typeSelector.active = false;
        typeSelector.setBordered(false);
        typeSelector.setValue(options[0].getText().getString());
        List<TypeOption<T>> typeLines = Arrays.stream(options).map(t -> new TypeOption<>(getWidth(), t, Minecraft.getInstance().font, typeSelector, this::selectType, listFrameColor, listBackgroundColor, listBackgroundHighlightColor)).toList();
        typeSelectorList = new ScrollableList<>(width + 10, 68, typeLines, 17, 10);
        chosen = options[0];
    }

    private void selectType(T option) {
        if(chosen != option) {
            chosen = option;
            if (onSelect != null) {
                onSelect.accept(chosen);
            }
        }
    }

    public void setOnSelect(Consumer<T> onSelect) {
        this.onSelect = onSelect;
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
        graphics.fill(0, 0, getWidth(), getHeight(), backgroundColor);
        if (insideBounds(relativeMouseX, relativeMouseY)) {
            graphics.fill(0, 0, getWidth(), getHeight(), 0x0AFFFFFF);
        }
        if (chosen.getText().getString().equals("Death")) {
            graphics.fill(-1, 0, getWidth(), getHeight(), 0x33FF0000);
        }
        typeSelector.render(graphics, relativeMouseX, relativeMouseY, pPartialTick);
        poseStack.pushPose();
        poseStack.translate(getWidth() - 8, 0.5, 0);
        poseStack.scale(2, 2, 1);
        graphics.drawString(Minecraft.getInstance().font, "⌄", 0, 0, 0xFFFFFFFF);
        poseStack.popPose();
        if (typeSelector.isActive()) {
            poseStack.pushPose();
            poseStack.translate(0, getHeight(), 100);
            typeSelectorList.render(poseStack, graphics, color, relativeMouseX, relativeMouseY - getHeight(), pPartialTick);
            poseStack.popPose();
        }
    }

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        double typeSelectorListMouseY = relativeMouseY - getHeight();
        if (relativeMouseX >= 0 && relativeMouseX < getWidth() && relativeMouseY >= 0 && relativeMouseY < getHeight()) {
            typeSelector.active = true;
            typeSelector.setFocused(true);
            typeSelector.setValue("");
            updateList();
            return true;
        } else if (typeSelector.isActive() && typeSelectorList.insideBounds(relativeMouseX, typeSelectorListMouseY)) {
            return typeSelectorList.mouseClicked(relativeMouseX, typeSelectorListMouseY, mouseButton);
        } else {
            typeSelector.active = false;
            typeSelector.setFocused(false);
            typeSelector.setValue(chosen.getText().getString());
        }
        return super.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
    }

    @Override
    public void defocus(double relativeMouseX, double relativeMouseY, int mouseButton) {
        double typeSelectorListMouseY = relativeMouseY - getHeight();
        if((relativeMouseX < 0 || relativeMouseX >= getWidth() || relativeMouseY < 0 || relativeMouseY >= getHeight()) && (!typeSelector.isActive() || !typeSelectorList.insideBounds(relativeMouseX, typeSelectorListMouseY))) {
            typeSelector.active = false;
            typeSelector.setFocused(false);
            typeSelector.setValue(chosen.getText().getString());
        }
    }

    private void updateList() {
        List<TypeOption<T>> typeLines = Arrays.stream(originalOptions).filter(filterText()).map(t -> new TypeOption<>(getWidth(), t, Minecraft.getInstance().font, typeSelector, this::selectType, listFrameColor, listBackgroundColor, listBackgroundHighlightColor)).toList();
        typeSelectorList.changeElements(typeLines);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (typeSelector.isActive()) {
            String old = typeSelector.getValue();
            if (typeSelector.keyPressed(pKeyCode, pScanCode, pModifiers)) {
                String newValue = typeSelector.getValue();
                if (!Objects.equals(old, newValue)) {
                    updateList();
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
            if (typeSelector.charTyped(pCodePoint, pModifiers)) {
                String newValue = typeSelector.getValue();
                if (!Objects.equals(old, newValue)) {
                    updateList();
                    return true;
                }
            }
        }
        return super.charTyped(pCodePoint, pModifiers);
    }

    @NotNull
    private Predicate<T> filterText() {
        return t -> t.getText().getString().toLowerCase().contains(typeSelector.getValue().toLowerCase());
    }

    @Override
    public boolean mouseScrolled(double relativeMouseX, double relativeMouseY, double pDelta) {
        double typeSelectorListMouseY = relativeMouseY - getHeight();
        if (typeSelector.isActive() && typeSelectorList.insideBounds(relativeMouseX, typeSelectorListMouseY)) {
            typeSelectorList.mouseScrolled(relativeMouseX, typeSelectorListMouseY, pDelta);
        }
        return super.mouseScrolled(relativeMouseX, relativeMouseY, pDelta);
    }

    @Override
    public boolean mouseDragged(double relativeMouseX, double relativeMouseY, int pButton, double pDragX, double pDragY) {
        double typeSelectorListMouseY = relativeMouseY - getHeight();
        if (typeSelector.isActive()) {
            typeSelectorList.mouseDragged(relativeMouseX, typeSelectorListMouseY, pButton, pDragX, pDragY);
        }
        return super.mouseDragged(relativeMouseX, relativeMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double relativeMouseX, double relativeMouseY, int pButton) {
        double typeSelectorListMouseY = relativeMouseY - getHeight();
        if (typeSelector.isActive()) {
            typeSelectorList.mouseReleased(relativeMouseX, typeSelectorListMouseY, pButton);
        }
        return super.mouseReleased(relativeMouseX, relativeMouseY, pButton);
    }

    @Override
    public boolean insideBounds(double relativeMouseX, double relativeMouseY) {
        double typeSelectorListMouseY = relativeMouseY - getHeight();
        if (typeSelector.isActive() && typeSelectorList.insideBounds(relativeMouseX, typeSelectorListMouseY)) {
            return true;
        }
        return super.insideBounds(relativeMouseX, relativeMouseY);
    }

    @Override
    public void tick() {
        super.tick();
        typeSelector.tick();
    }

    public interface Option {
        Component getText();
    }

    private static class TypeOption<T extends Option> extends Element {

        private final String line;
        private final T type;
        private final Font font;
        private final EditBox typeSelector;
        private final Consumer<T> optionConsumer;
        private final int frameColor;// = 0xFFAAAAAA;
        private final int backgroundColor;// = 0xFF352E1C;
        private final int backgroundHighlightColor;// = 0xFF554E3C;

        public TypeOption(int width, T type, Font font, EditBox typeSelector, Consumer<T> optionConsumer, int frameColor, int backgroundColor, int backgroundHighlightColor) {
            super(width, 17);
            this.line = type.getText().getString();
            this.type = type;
            this.font = font;
            this.typeSelector = typeSelector;
            this.optionConsumer = optionConsumer;
            this.frameColor = frameColor;
            this.backgroundColor = backgroundColor;
            this.backgroundHighlightColor = backgroundHighlightColor;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            Minecraft mc = Minecraft.getInstance();
            graphics.fill(0, 0, getWidth(), getHeight(), frameColor);
            graphics.fill(1, 1, getWidth() - 1, getHeight() - 1, backgroundColor);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(1, 1, getWidth() - 1, getHeight() - 1, backgroundHighlightColor);
            }
            graphics.drawString(font, line, 3, 3, color);
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

}
