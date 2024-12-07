package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.dialogue.DialogueBranch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DialogueOptions extends ScrollableList {

    public static DialogueOptions makeOptions(List<DialogueBranch.DialogueOption> localizedOptions, int textWidth, Font font, int width, int height, int scrollbarWidth, BiConsumer<DialogueOptions, Integer> listener) {
        List<List<TextLine>> lines = new ArrayList<>();
        //List<DialogueBranch.OptionType> types = new ArrayList<>();
        for (DialogueBranch.DialogueOption option : localizedOptions) {
            String localizedOption = option.line();
            List<Element> elements = new TextUtil().parseText(localizedOption, textWidth - 5, font);
            lines.add(elements.stream().filter(a -> a instanceof TextLine).map(a -> (TextLine) a).toList());
            //types.add(option.type());
        }
        return new DialogueOptions(width, height, lines, localizedOptions, scrollbarWidth, listener);
    }


    private final List<Integer> elementIndexToOptionIndex = new ArrayList<>();
    private final List<DialogueBranch.DialogueOption> types;
    private final BiConsumer<DialogueOptions, Integer> listener;

    private DialogueOptions(int width, int height, List<List<TextLine>> options, List<DialogueBranch.DialogueOption> types, int scrollbarWidth, BiConsumer<DialogueOptions, Integer> listener) {
        super(width, height, options.stream().flatMap(Collection::stream).collect(Collectors.toList()), 15, scrollbarWidth);
        this.types = types;
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
        poseStack.pushPose();
        poseStack.translate(flag ? 20 : 5, 0, 0);
        super.renderElement(element, poseStack, graphics, color, relativeMouseX, relativeMouseY, y);
        poseStack.popPose();
        if(element == 0 || !Objects.equals(elementIndexToOptionIndex.get(element - 1), elementIndexToOptionIndex.get(element))) {
            poseStack.pushPose();
            poseStack.translate(-2, 0, 0);
            String c = switch (getType(element)) {
                case NORMAL, CONTINUE -> ">";
                case TRADE -> "□";
                case END -> "•";
            };
            graphics.drawString(Minecraft.getInstance().font, c, 0, 0, color);
            poseStack.popPose();
        }
    }

    @Override
    protected void clickElement(int element, double relativeMouseX, double relativeMouseY, int mouseButton) {
        super.clickElement(element, relativeMouseX, relativeMouseY, mouseButton);
        if (listener != null) {
            listener.accept(this, elementIndexToOptionIndex.get(element));
        }
    }

    private int getHoveredOption(double relativeMouseX, double relativeMouseY) {
        int hoveredElement = getHoveredElement(relativeMouseX, relativeMouseY);
        if (hoveredElement == -1) {
            return -1;
        }
        return elementIndexToOptionIndex.get(hoveredElement);
    }

    public String getLocalizedOption(int option) {
        return types.get(option).line();
    }

    private DialogueBranch.OptionType getType(int element) {
        return getOptionType(elementIndexToOptionIndex.get(element));
    }

    public DialogueBranch.OptionType getOptionType(int option) {
        return types.get(option).type();
    }




}
