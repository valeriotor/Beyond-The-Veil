package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.dialogue.DialogueBranch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class LetterOptions extends OptionList<LetterOptions> {

    public static LetterOptions makeOptions(List<String> localizedOptions, int textWidth, Font font, int width, int height, int scrollbarWidth, BiConsumer<LetterOptions, Integer> listener) {
        List<List<TextLine>> lines = new ArrayList<>();
        //List<DialogueBranch.OptionType> types = new ArrayList<>();
        for (String option : localizedOptions) {
            List<Element> elements = new TextUtil().parseText(option, textWidth - 5, font);
            lines.add(elements.stream().filter(a -> a instanceof TextLine).map(a -> (TextLine) a).toList());
        }
        return new LetterOptions(width, height, lines, scrollbarWidth, listener);
    }



    private LetterOptions(int width, int height, List<List<TextLine>> options, int scrollbarWidth, BiConsumer<LetterOptions, Integer> listener) {
        super(width, height, options, scrollbarWidth, listener);
    }

    @Override
    protected String getStarter(int element) {
        return ">";
    }

    @Override
    protected void acceptListener(int element) {
        listener.accept(this, elementIndexToOptionIndex.get(element));
    }

}
