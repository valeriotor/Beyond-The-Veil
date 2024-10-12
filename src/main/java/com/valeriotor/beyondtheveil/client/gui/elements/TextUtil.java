package com.valeriotor.beyondtheveil.client.gui.elements;

import com.valeriotor.beyondtheveil.client.gui.elements.property.CaptionProperty;
import com.valeriotor.beyondtheveil.client.gui.elements.property.LinkProperty;
import com.valeriotor.beyondtheveil.client.gui.elements.property.Property;
import net.minecraft.client.gui.Font;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.IntFunction;

public class TextUtil {


    private List<List<Word>> words = new ArrayList<>();
    private List<BreakType> breakTypes = new ArrayList<>();
    private List<Word> currentWords = new ArrayList<>();
    private List<Word> wordsWithProperty = new ArrayList<>();
    private StringBuilder wordBuilder = new StringBuilder();
    private StringBuilder propertyValueTextBuilder = new StringBuilder();
    private Set<String> currentFormattings = new HashSet<>();
    private String prevSeparator = "";
    private ParsingPhase phase = ParsingPhase.NORMAL;
    private boolean backslashEscape = false;
    private boolean formatEscape = false;

    public List<Element> parseText(String localized, int width, Font f) {
        words.add(currentWords);

        int[] localizedChars = localized.chars().toArray();
        for (int i = 0; i < localizedChars.length; i++) {
            int c = localizedChars[i];
            processCharacter(c, i == localizedChars.length - 1);
        }

        List<Element> returnValue = new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {
            List<Word> wordBlock = words.get(i);
            List<String> lines = new ArrayList<>();
            StringBuilder text = new StringBuilder();
            wordBlock.forEach(w -> text.append(w.toFormattedString(true)));
            f.getSplitter().splitLines(text.toString(), width, Style.EMPTY, true, (pStyle, pCurrentPos, pContentWidth) -> {
                lines.add(text.substring(pCurrentPos, pContentWidth));
            });

            int currentWord = 0;
            for (String line : lines) {
                List<Property> properties = new ArrayList<>();
                int firstWordInLine = currentWord;
                List<Word> wordsInLine = new ArrayList<>();
                String currentProperty = null;
                int currentPropertyStart = 0;
                boolean formatEscape = false;
                int[] array = line.chars().toArray();
                for (int j = 0; j < array.length; j++) {
                    int c = array[j];
                    if (currentWord >= wordBlock.size()) {
                        break;
                    } else {
                        boolean done = false;
                        if (formatEscape) {
                            formatEscape = false;
                        } else if (c == '§') {
                            formatEscape = true;
                        } else {
                            done = wordBlock.get(currentWord).traverseWord(c);
                        }
                        if (done || j == array.length - 1) {
                            if (done) {
                                currentWord++;
                            }
                            if (currentWord < wordBlock.size() || currentProperty != null) {
                                String newWordProperty = currentWord < wordBlock.size() ? wordBlock.get(currentWord).getProperty() : null;
                                if (!Objects.equals(newWordProperty, currentProperty) || !done) {
                                    if (currentProperty != null) {
                                        StringBuilder precedingText = new StringBuilder();
                                        StringBuilder propertyText = new StringBuilder();
                                        List<Word> subList = wordBlock.subList(firstWordInLine, currentPropertyStart);
                                        for (int k = 0; k < subList.size(); k++) {
                                            Word word = subList.get(k);
                                            precedingText.append(word.toFormattedString(k != 0));
                                        }
                                        wordBlock.subList(currentPropertyStart, currentWord).forEach(w -> propertyText.append(w.toFormattedString(true)));
                                        int propertyStart = f.width(FormattedText.of(precedingText.toString()));
                                        int propertyWidth = f.width(FormattedText.of(propertyText.toString()));
                                        properties.add(makeProperty(currentProperty, propertyStart, propertyStart + propertyWidth));
                                    }
                                    if (done) {
                                        currentProperty = newWordProperty;
                                        currentPropertyStart = currentWord;
                                    }
                                }
                            }
                        }
                    }
                }
                returnValue.add(new TextLine(Language.getInstance().getVisualOrder(FormattedText.of(line)), properties, f));
            }
            if (i < words.size() - 1) {
                returnValue.add(breakTypes.get(i).separator.apply(width));
            }
        }

        return returnValue;

    }

    private void processCharacter(int c, boolean finalCharacter) {
        if (backslashEscape) {
            if (c == 'n' || c == 'p') {
                createWord(c);
                prevSeparator = "";
                currentWords = new ArrayList<>();
                words.add(currentWords);
                breakTypes.add(switch (c) {
                    case 'n' -> BreakType.PARAGRAPH;
                    case 'p' -> BreakType.PAGE;
                    default -> throw new IllegalStateException("Unexpected value: " + c);
                });
            } else {
                wordBuilder.append((char) c);
            }
            backslashEscape = false;
        } else if (formatEscape) {
            if (c == 'r') {
                currentFormattings.clear();
            } else {
                currentFormattings.add(String.valueOf((char) c));
            }
            formatEscape = false;
        } else if (c == '{' && phase == ParsingPhase.NORMAL) {
            createWord(c);
            phase = ParsingPhase.PROPERTY_TEXT;
        } else if (c == '[' && phase == ParsingPhase.AWAITING_VALUE) {
            phase = ParsingPhase.PROPERTY_VALUE;
        } else if (c == '}' && phase == ParsingPhase.PROPERTY_TEXT) {
            createWord(c);
            phase = ParsingPhase.AWAITING_VALUE;
        } else if (c == ']' && phase == ParsingPhase.PROPERTY_VALUE) {
            phase = ParsingPhase.NORMAL;
            String propertyText = propertyValueTextBuilder.toString();
            wordsWithProperty.forEach(w -> w.setProperty(propertyText));
            wordsWithProperty.clear();
            propertyValueTextBuilder = new StringBuilder();
        } else if (c == '\\') {
            backslashEscape = true;
        } else if (c == '§') {
            formatEscape = true;
        } else if (c == ' ') {
            createWord(c);
            prevSeparator = " ";
        } else if (phase != ParsingPhase.PROPERTY_VALUE) {
            wordBuilder.append((char) c);
            if (finalCharacter) {
                createWord(c);
            }
        } else if (phase == ParsingPhase.PROPERTY_VALUE) {
            propertyValueTextBuilder.append((char) c);
        }
    }

    private Word createWord(int c) {
        Word word = null;
        if (!wordBuilder.isEmpty()) {
            word = new Word(wordBuilder.toString(), prevSeparator, new HashSet<>(currentFormattings));
            wordBuilder = new StringBuilder();
            currentWords.add(word);
            if (phase == ParsingPhase.PROPERTY_TEXT) {
                wordsWithProperty.add(word);
            }
            if (c == '{' || c == '}') {
                prevSeparator = "";
            } else {
                prevSeparator = String.valueOf(c);
            }
        } else {
            if (c == ' ') {
                prevSeparator = " ";
            }
        }
        return word;
    }

    @NotNull
    private static Property makeProperty(String value, int pixelStart, int pixelEnd) {
        String[] split = value.split(":");
        return switch (split[0]) {
            case "link" -> new LinkProperty(pixelStart, pixelEnd, split[1]);
            case "caption" -> new CaptionProperty(pixelStart, pixelEnd, split[1]);
            default -> throw new IllegalStateException("Unexpected value: " + split[0]);
        };
    }
    private enum ParsingPhase {
        NORMAL, PROPERTY_TEXT, AWAITING_VALUE, PROPERTY_VALUE

    }

    private enum BreakType {

        PARAGRAPH(Separators::paragraphSeparator), PAGE(Separators::pageSeparator);
        private IntFunction<Element> separator;
        BreakType(IntFunction<Element> separator) {
            this.separator = separator;
        }

    }

    private static class Word {

        private final Set<String> formattings;
        private final String word;
        private String property;
        private final String prevWordSeparator;

        private int counter = 0;

        public Word(String word, String prevWordSeparator, Set<String> formattings) {
            this.word = word;
            this.prevWordSeparator = prevWordSeparator;
            this.formattings = formattings;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getProperty() {
            return property;
        }

        public String toFormattedString(boolean includeSeparator) {
            StringBuilder builder = new StringBuilder(includeSeparator ? prevWordSeparator : "");
            formattings.forEach(s -> builder.append("§").append(s));
            builder.append(word);
            if (!formattings.isEmpty()) {
                builder.append("§r");
            }
            return builder.toString();
        }

        public boolean traverseWord(int c) {
            if (c == word.charAt(counter)) {
                counter++;
                if (counter == word.length()) {
                    return true;
                }
            }
            return false;
        }

    }

}
