package com.valeriotor.beyondtheveil.client.gui.research.text;

import com.valeriotor.beyondtheveil.client.gui.research.text.property.CaptionProperty;
import com.valeriotor.beyondtheveil.client.gui.research.text.property.LinkProperty;
import com.valeriotor.beyondtheveil.client.gui.research.text.property.Property;
import net.minecraft.client.gui.Font;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextUtil {

    public static List<TextLine> parseText(String localized, int width, Font f) {
        StringBuilder builder = new StringBuilder();
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
                        builder.append("\n");
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
        f.getSplitter().splitLines(builder.toString(), width, Style.EMPTY, true, (pStyle, pCurrentPos, pContentWidth) -> {
            strings.add(builder.substring(pCurrentPos, pContentWidth));
            styles.add(pStyle);
        });
        List<TextLine> textLines = new ArrayList<>();
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
                    textLines.add(new TextLine(Language.getInstance().getVisualOrder(FormattedText.of(strings.get(i))), propertiesForCurrentLine.stream().toList()));
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
