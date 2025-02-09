package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.dialogue.DialogueBranch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public class DialogueOptions extends OptionList<DialogueOptions> {

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

    private final List<DialogueBranch.DialogueOption> types;

    private DialogueOptions(int width, int height, List<List<TextLine>> options, List<DialogueBranch.DialogueOption> types, int scrollbarWidth, BiConsumer<DialogueOptions, Integer> listener) {
        super(width, height, options, scrollbarWidth, listener);
        this.types = types;
    }

    @Override
    protected String getStarter(int element) {
        return switch (getType(element)) {
            case NORMAL, CONTINUE -> ">";
            case TRADE -> "□";
            case END -> "•";
        };
    }

    @Override
    protected void acceptListener(int element) {
        listener.accept(this, elementIndexToOptionIndex.get(element));
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
