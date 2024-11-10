package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DialogueOptions extends ScrollableList {

    public static DialogueOptions makeOptions(List<String> localizedOptions, int textWidth, Font font, int width, int height, int scrollbarWidth, Consumer<Integer> listener) {
        List<List<TextLine>> lines = new ArrayList<>();
        for (String localizedOption : localizedOptions) {
            List<Element> elements = new TextUtil().parseText(localizedOption, textWidth, font);
            lines.add(elements.stream().filter(a -> a instanceof TextLine).map(a -> (TextLine) a).toList());
        }
        return new DialogueOptions(width, height, lines, scrollbarWidth, listener);
    }


    private final List<Integer> elementIndexToOptionIndex = new ArrayList<>();
    private final Consumer<Integer> listener;

    private DialogueOptions(int width, int height, List<List<TextLine>> options, int scrollbarWidth, Consumer<Integer> listener) {
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
    protected void renderElement(int element, PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, int y) {
        boolean flag = false;
        if (elementIndexToOptionIndex.get(element) == getHoveredOption(relativeMouseX, relativeMouseY)) {
            flag = true;
        }
        if (flag) {
            poseStack.pushPose();
            poseStack.translate(15, 0, 0);
        }
        super.renderElement(element, poseStack, graphics, color, relativeMouseX, relativeMouseY, y);
        if (flag) {
            poseStack.popPose();
        }
    }

    @Override
    protected void clickElement(int element, double relativeMouseX, double relativeMouseY, int mouseButton) {
        super.clickElement(element, relativeMouseX, relativeMouseY, mouseButton);
        if (listener != null) {
            listener.accept(elementIndexToOptionIndex.get(element));
        }
    }

    private int getHoveredOption(double relativeMouseX, double relativeMouseY) {
        int hoveredElement = getHoveredElement(relativeMouseX, relativeMouseY);
        if (hoveredElement == -1) {
            return -1;
        }
        return elementIndexToOptionIndex.get(hoveredElement);
    }

}
