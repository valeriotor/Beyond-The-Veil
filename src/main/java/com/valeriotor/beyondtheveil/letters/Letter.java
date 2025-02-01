package com.valeriotor.beyondtheveil.letters;

import java.util.ArrayList;
import java.util.List;

public class Letter {
    private final ExchangeTemplate.LetterTemplate template;
    private boolean opened;
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

    private Letter(ExchangeTemplate.LetterTemplate template) {
        this.template = template;
    }

    boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
