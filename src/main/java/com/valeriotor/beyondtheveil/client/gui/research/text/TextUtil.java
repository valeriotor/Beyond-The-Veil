package com.valeriotor.beyondtheveil.client.gui.research.text;

import com.valeriotor.beyondtheveil.client.gui.research.text.property.CaptionProperty;
import com.valeriotor.beyondtheveil.client.gui.research.text.property.LinkProperty;
import com.valeriotor.beyondtheveil.client.gui.research.text.property.Property;
import net.minecraft.client.gui.Font;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.IntFunction;
import java.util.function.Supplier;

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

    public List<Element> parseText2(String localized, int width, Font f) {
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
                                        properties.add(getProperty(currentProperty, propertyStart, propertyStart + propertyWidth));
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

    public static List<Element> parseText(String localized, int width, Font f) {
        List<StringBuilder> builders = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builders.add(builder);
        List<PropertyToBe> properties = new ArrayList<>();
        int propertyStart = -1, propertyEnd = -1;
        StringBuilder propertyBuilder = new StringBuilder();
        int phase = 0; // 0 is normal, 1 when reading property text, 2 when reading property
        boolean escaping = false, skippingCounter = false;
        int i = 0;
        for (int c : localized.chars().toArray()) {
            if (phase == 0) {
                if (escaping) {
                    if (c == '\\') {
                        builder.append((char) c);
                        i++;
                    } else if (c == 'n') {
                        builder = new StringBuilder(" ");
                        i++;
                        builders.add(builder);
                        escaping = false;
                        //i++;
                    } else if (c == 'p') {
                        // new page line
                    }
                } else if (c == '{') {
                    phase = 1;
                    propertyStart = i;
                } else if (c == '[') {
                    phase = 2;
                } else if (c == '\\') {
                    escaping = true;
                } else {
                    builder.append((char) c);
                    i++;
                }
            } else if (phase == 1) {
                if (c == '}') {
                    phase = 0;
                    propertyEnd = i;
                } else {
                    builder.append((char) c);
                    i++;
                }
            } else if (phase == 2) {
                if (c == ']') {
                    phase = 0;
                    properties.add(new PropertyToBe(propertyBuilder.toString(), propertyStart, propertyEnd));
                    propertyBuilder = new StringBuilder();
                } else {
                    propertyBuilder.append((char) c);
                }
            }
        }
        List<String> strings = new ArrayList<>();
        List<Style> styles = new ArrayList<>();
        for (StringBuilder builder1 : builders) {
            f.getSplitter().splitLines(builder1.toString(), width, Style.EMPTY, true, (pStyle, pCurrentPos, pContentWidth) -> {
                strings.add(builder1.substring(pCurrentPos, pContentWidth));
                styles.add(pStyle);
            });
        }
        List<Element> textLines = new ArrayList<>();
        adjustFormatting(strings, properties);

        i = 0;
        int lengthSoFar = 0;
        properties.add(new PropertyToBe("dummy", Integer.MAX_VALUE, Integer.MAX_VALUE));
        List<Property> propertiesForCurrentLine = new ArrayList<>();
        for (int j = 0; j < properties.size(); j++) {
            if (i < strings.size()) {
                PropertyToBe propertyToBe = properties.get(j);
                String currentLine = strings.get(i);
                int currentLineLength = currentLine.length();
                while (i < strings.size() && lengthSoFar + currentLineLength <= propertyToBe.start) {
                    textLines.add(new TextLine(Language.getInstance().getVisualOrder(FormattedText.of(strings.get(i))), propertiesForCurrentLine.stream().toList(), f));
                    propertiesForCurrentLine.clear();
                    lengthSoFar += currentLineLength;
                    i++;
                    if (i < strings.size()) {
                        currentLine = strings.get(i);
                        currentLineLength = currentLine.length();
                    }
                }
                if (i < strings.size()) {
                    int start = propertyToBe.start - lengthSoFar;
                    int end = propertyToBe.end - lengthSoFar;
                    if (end > currentLineLength) {
                        properties.add(j + 1, new PropertyToBe(propertyToBe.value, (int) (lengthSoFar + currentLineLength), propertyToBe.end));
                        end = (int) currentLineLength;
                    }

                    propertiesForCurrentLine.add(createProperty(currentLine, styles.get(i), propertyToBe.value, start, end, f));
                }
            }
        }

        return textLines;
    }

    private static void adjustFormatting(List<String> lines, List<PropertyToBe> properties) {
        List<String> carryOver = new ArrayList<>();
        int currentLength = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!carryOver.isEmpty()) {
                line = "§" + String.join("§", carryOver) + line;
                lines.set(i, line);
                for (PropertyToBe property : properties) {
                    if (property.start < Integer.MAX_VALUE && property.start >= currentLength) {
                        property.start += 2 * carryOver.size();
                        property.end += 2 * carryOver.size();
                    }
                }
                carryOver.clear();
            }
            boolean marker = false;
            for (char c : line.toCharArray()) {
                if (c == '§') {
                    marker = true;
                } else if (marker) {
                    marker = false;
                    carryOver.add(String.valueOf(c));
                    if (c == 'r') {
                        carryOver.clear();
                    }
                }
            }
            if (!carryOver.isEmpty()) {
                String newLine = line.concat("§r");
                lines.set(i, newLine);
                for (PropertyToBe property : properties) {
                    if (property.start < Integer.MAX_VALUE && property.start > currentLength + line.length()) {
                        property.start += 2;
                        property.end += 2;
                    }
                }
            }
            currentLength += line.length();
        }
    }


    private static Property createProperty(String line, Style style, String value, int start, int end, Font f) {
        int i = 0;
        int phase = 0;
        int pixelStart = 0, pixelEnd = 0;
        StringBuilder builder = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (i >= start && phase == 0) {
                phase = 1;
                pixelStart = f.width(FormattedText.of(builder.toString(), style));
            } else if ((i == line.length() - 1 || i >= end) && phase == 1) {
                pixelEnd = f.width(FormattedText.of(builder.toString(), style));
                break;
            }
            i++;
            builder.append(c);
        }
        return getProperty(value, pixelStart, pixelEnd);
    }

    @NotNull
    private static Property getProperty(String value, int pixelStart, int pixelEnd) {
        String[] split = value.split(":");
        return switch (split[0]) {
            case "link" -> new LinkProperty(pixelStart, pixelEnd, split[1]);
            case "caption" -> new CaptionProperty(pixelStart, pixelEnd, split[1]);
            default -> throw new IllegalStateException("Unexpected value: " + split[0]);
        };
    }

    private static final class PropertyToBe {
        private final String value;
        private int start;

        private int end;

        private PropertyToBe(String value, int start, int end) {
            this.value = value;
            this.start = start;
            this.end = end;
        }


    }

}
