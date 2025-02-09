package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public abstract class OptionList<T extends OptionList> extends ScrollableList<TextLine>{

    protected final List<Integer> elementIndexToOptionIndex = new ArrayList<>();
    protected final BiConsumer<T, Integer> listener;

    public OptionList(int width, int height, List<List<TextLine>> options, int scrollbarWidth, BiConsumer<T, Integer> listener) {
        super(width, height, options.stream().flatMap(Collection::stream).collect(Collectors.toList()), 15, scrollbarWidth);
        this.listener = listener;
        for (int i = 0; i < options.size(); i++) {
            List<TextLine> option = options.get(i);
            for (TextLine textLine : option) {
                elementIndexToOptionIndex.add(i);
            }
        }
    }

    @Override
    protected void renderElement(int element, PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, int y, float pPartialTick) {
        boolean flag = false;
        if (elementIndexToOptionIndex.get(element) == getHoveredOption(relativeMouseX, relativeMouseY)) {
            flag = true;
        }
        poseStack.pushPose();
        poseStack.translate(flag ? 20 : 5, 0, 0);
        super.renderElement(element, poseStack, graphics, color, relativeMouseX, relativeMouseY, y, pPartialTick);
        poseStack.popPose();
        if(element == 0 || !Objects.equals(elementIndexToOptionIndex.get(element - 1), elementIndexToOptionIndex.get(element))) {
            poseStack.pushPose();
            poseStack.translate(-2, 0, 0);
            String c = getStarter(element);
            graphics.drawString(Minecraft.getInstance().font, c, 0, 0, color);
            poseStack.popPose();
        }
    }

    protected abstract String getStarter(int element);

    @Override
    protected boolean clickElement(int element, double relativeMouseX, double relativeMouseY, int mouseButton) {
        boolean flag = super.clickElement(element, relativeMouseX, relativeMouseY, mouseButton);
        if (listener != null) {
            acceptListener(element);
            return true;
        }
        return flag;
    }

    protected abstract void acceptListener(int element);

    private int getHoveredOption(double relativeMouseX, double relativeMouseY) {
        int hoveredElement = getHoveredElement(relativeMouseX, relativeMouseY);
        if (hoveredElement == -1) {
            return -1;
        }
        return elementIndexToOptionIndex.get(hoveredElement);
    }

}
