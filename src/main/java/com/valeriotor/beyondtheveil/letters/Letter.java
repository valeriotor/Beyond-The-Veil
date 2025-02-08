package com.valeriotor.beyondtheveil.letters;

import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Letter {
    private final ExchangeTemplate.LetterTemplate template;
    private boolean opened;
    private boolean canReply;
    private List<String> chosenOptions = new ArrayList<>();

    public static Letter received(ExchangeTemplate.LetterTemplate template) {
        Letter letter = new Letter(template);
        for (List<String> options : template.optionsPerLine) {
            letter.chosenOptions.add(options.get(0));
        }
        return letter;
    }

    public static Letter sent(ExchangeTemplate.LetterTemplate template, List<Integer> chosen) {
        Letter letter = new Letter(template);
        List<List<String>> optionsPerLine = template.optionsPerLine;
        for (int i = 0; i < optionsPerLine.size(); i++) {
            List<String> options = optionsPerLine.get(i);
            letter.chosenOptions.add(options.get(chosen.size() > i ? chosen.get(i) : 0));
        }
        return letter;
    }

    public static Letter fromNBT(CompoundTag tag) {
        ExchangeTemplate template = ExchangeRegistry.byName(tag.getString("exchange_template"));
        if (template != null) {
            Letter letter = new Letter(template.getTemplate(tag.getInt("index")));
            CompoundTag options = tag.getCompound("options");
            options.getAllKeys().stream().sorted(Comparator.comparingInt(Integer::valueOf)).forEach(s -> letter.chosenOptions.add(options.getString(s)));
            letter.canReply = tag.getBoolean("canReply");
            letter.opened = tag.getBoolean("opened");
            return letter;
        }
        return null;
    }

    private Letter(ExchangeTemplate.LetterTemplate template) {
        this.template = template;
    }

    boolean isOpened() {
        return opened;
    }

    public boolean canReply() {
        return canReply;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public CompoundTag saveToNBT(CompoundTag tag) {
        tag.putString("exchange_template", template.getParent().getName());
        tag.putInt("index", template.getIndex());
        tag.putBoolean("opened", opened);
        tag.putBoolean("canReply", canReply);
        CompoundTag options = new CompoundTag();
        for (int i = 0; i < chosenOptions.size(); i++) {
            options.putString(String.valueOf(i), chosenOptions.get(i));
        }
        tag.put("options", options);
        return tag;
    }



}
