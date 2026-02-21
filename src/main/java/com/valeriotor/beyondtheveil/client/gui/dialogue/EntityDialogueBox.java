package com.valeriotor.beyondtheveil.client.gui.dialogue;

import com.google.common.math.DoubleMath;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import com.valeriotor.beyondtheveil.client.gui.elements.TextLine;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EntityDialogueBox extends ScrollableList<TextLine> {

    private static final double DOT_PAUSE = 10.5;
    private static final double COMMA_PAUSE = 5.7;
    private static final double DASH_PAUSE = 5.7;
    private static final double SLASH_PAUSE = 7.7;
    private double ppc = 0.5;
    private final List<String> lines = new ArrayList<>();
    private StringBuilder currentLine = new StringBuilder();
    private int currentLineIndex = 0;
    double progress = 0;
    private int skipped = 0;
    private int prevLineSize = 0;
    private double pause = 0;
    private double nextAddCharacter = 0;
    private int nextAddCharacterIndex = 0;

    protected EntityDialogueBox(int width, int height, String localizedText) {
        super(width, height, new ArrayList<>(), 15, 5);
        String[] firstSplit = localizedText.split("\n");
        for (String s : firstSplit) {
            Minecraft.getInstance().font.getSplitter().splitLines(s, width * 95 / 100, Style.EMPTY, true, (pStyle, pCurrentPos, pContentWidth) -> {
                lines.add(s.substring(pCurrentPos, pContentWidth));
            });
        }
    }

    @Override
    public void tick() {
        super.tick();
        double newProgress = DoubleMath.isMathematicalInteger(progress) ? progress + 1 : Math.ceil(progress);
        addCharacters(newProgress);
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
        double flooredProgress = Math.floor(progress);
        double newProgress = progress;
        if (pPartialTick > progress - flooredProgress + 0.001) {
            newProgress = flooredProgress + pPartialTick;
            addCharacters(newProgress);
        }
        super.render(poseStack, graphics, color, relativeMouseX, relativeMouseY, pPartialTick);
    }

    private void addCharacters(double newProgress) {
        if (currentLineIndex >= lines.size()) {
            return;
        }
        while (nextAddCharacter < newProgress) {
            int characterIndex = nextAddCharacterIndex + skipped - prevLineSize;
            addCharacter(characterIndex);
            nextAddCharacterIndex++;
            if (currentLineIndex >= lines.size()) {
                return;
            }
        }
        progress = newProgress;
        makeNewLine();
    }

    private void addCharacter(int characterIndex) {
        boolean escape = false;
        boolean finished = false;
        while (!finished) {
            if (currentLineIndex >= lines.size()) {
                return;
            }
            String line = lines.get(currentLineIndex);
            if (characterIndex >= line.length()) {
                characterIndex = 0;
                makeNewLine();
                if(currentLineIndex + 1 < lines.size()) {
                    boolean adjustRow = getCurrentFirstRow() == getMaxFirstRow();
                    List<TextLine> rows = new ArrayList<>(rows());
                    rows.add(new TextLine(FormattedCharSequence.EMPTY, new ArrayList<>(), Minecraft.getInstance().font));
                    changeElements(rows);
                    if (adjustRow) {
                        setCurrentFirstRow(getMaxFirstRow());
                    }
                }
                nextAddCharacter += ppc;
                prevLineSize += line.length();
                currentLineIndex++;
                currentLine = new StringBuilder();
            } else {
                char c = line.charAt(characterIndex);
                if (c == '|') {
                    nextAddCharacter += SLASH_PAUSE + ppc;
                    //skipped++;
                    return;
                }
                if (c == ']') {
                    ppc -= 0.1;
                    //skipped++;
                    return;
                }
                if (c == '[') {
                    ppc += 0.1;
                    //skipped++;
                    return;
                }
                if (escape) {
                    escape = false;
                    skipped++;
                    characterIndex++;
                } else if (c == '§') {
                    escape = true;
                    skipped++;
                    characterIndex++;
                } else {
                    Set<Character> strongPunctuation = Set.of('.', '!', '?', ':');
                    if (strongPunctuation.contains(c)) {
                        if (characterIndex + 1 >= line.length() || !strongPunctuation.contains(line.charAt(characterIndex + 1))) {
                            nextAddCharacter += DOT_PAUSE;
                        }
                    } else if (c == ',') {
                        nextAddCharacter += COMMA_PAUSE;
                    } else if (characterIndex + 1 < line.length() && (line.charAt(characterIndex + 1) == '–' || line.charAt(characterIndex + 1) == '—')) {
                        nextAddCharacter += DASH_PAUSE;
                    }
                    finished = true;
                    if (nextAddCharacterIndex % 5 == 0) {
                        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(BTVSounds.SHOREMAN_DIALOGUE.get(), 0.3F));
                    }
                }
                currentLine.append(c);
                nextAddCharacter += ppc;
            }
        }
    }

    private void makeNewLine() {
        String line = currentLine.toString();
        TextLine newTextLine = new TextLine(Language.getInstance().getVisualOrder(FormattedText.of(line)), new ArrayList<>(), Minecraft.getInstance().font);
        List<TextLine> rows = new ArrayList<>(rows());
        if (rows.isEmpty()) {
            rows.add(newTextLine);
        } else {
            rows.set(rows.size() - 1, newTextLine);
        }
        changeElements(rows);
    }

    public boolean isFinished() {
        return currentLineIndex >= lines.size();
    }
}
