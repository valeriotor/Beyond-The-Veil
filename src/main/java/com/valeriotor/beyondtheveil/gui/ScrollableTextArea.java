package com.valeriotor.beyondtheveil.gui;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraft.client.gui.Gui.drawRect;

public class ScrollableTextArea {
    private int screenHeight;
    private int screenWidth;
    private int height;
    private int width;
    private final List<String> paragraphs = new ArrayList<>();
    private final List<List<String>> text = new ArrayList<>();
    private int fullTextHeight;
    private int textLineHeight;
    private int paragraphBreakHeight;
    private int yScrolled;
    private int scrollAmount = 3;

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenDimensions(int height, int width) {
        this.screenHeight = height;
        this.screenWidth = width;
    }

    public void setHeightWidthRatio(float heightRatio, float widthRatio) {
        this.height = (int) (screenHeight * heightRatio);
        this.width = (int) (screenWidth * widthRatio);
    }

    public void setScreenDimensionsAndRatios(int height, int width, float heightRatio, float widthRatio) {
        this.screenHeight = height;
        this.screenWidth = width;
        this.height = (int) (screenHeight * heightRatio);
        this.width = (int) (screenWidth * widthRatio);
    }

    public void setText(String text) {
        setText(Arrays.asList(text.split("<BR>")));
    }

    public void setText(List<String> paragraphs) {
        this.paragraphs.clear();
        this.paragraphs.addAll(paragraphs);
        this.text.clear();
    }

    public void setTextLineHeight(int textLineHeight) {
        this.textLineHeight = textLineHeight;
    }

    public void setParagraphBreakHeight(int paragraphBreakHeight) {
        this.paragraphBreakHeight = paragraphBreakHeight;
    }

    public void setScrollAmount(int scrollAmount) {
        this.scrollAmount = scrollAmount;
    }

    public void recompute() {
        text.clear();
        fullTextHeight = 0;
        for (String paragraph : paragraphs) {
            List<String> splitParagraph = GuiHelper.splitStringsByWidth(paragraph, width - 18, Minecraft.getMinecraft().fontRenderer);
            text.add(splitParagraph);
            fullTextHeight += splitParagraph.size() * textLineHeight + paragraphBreakHeight;
        }
        handleMouseInput();
    }

    public void handleMouseInput() {
        int textAreaToTextDifference = getTextAreaToTextDifference();
        yScrolled = MathHelperBTV.clamp(0, Math.max(0, textAreaToTextDifference), yScrolled - scrollAmount * (int) Math.signum(Mouse.getDWheel()));
    }

    private int getTextAreaToTextDifference() {
        return fullTextHeight - height;
    }

    private int getTextYStart() {
        return -yScrolled;
    }

    public void drawScreen(GuiScreen gui, int hidingRectangleColor, int stringColor, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        drawRect(0, 0, width, textLineHeight, hidingRectangleColor);
        GlStateManager.color(1, 1, 1, 1);
        int yOffset = getTextYStart()+textLineHeight;
        for (List<String> paragraph : text) {
            for (String line : paragraph) {
                if (yOffset > 0) {
                    gui.drawString(gui.mc.fontRenderer, line, 0, yOffset, stringColor);
                }
                yOffset += textLineHeight;
                if (yOffset > height) {
                    break;
                }
            }
            yOffset += paragraphBreakHeight;
            if (yOffset > height) {
                break;
            }
        }
        int textAreaToTextDifference = getTextAreaToTextDifference();
        if (textAreaToTextDifference > 0) {
            int barX = width - 14;
            int scrollSectionHeight = height * 9 / 10;
            drawRect(barX, 0, barX + 8, scrollSectionHeight, 0xFF333333);
            int barHeight = scrollSectionHeight * height / fullTextHeight;
            int barY = (scrollSectionHeight - barHeight) * yScrolled / textAreaToTextDifference;
            drawRect(barX, barY, barX+8, barY+barHeight, 0xFFBBBBBB);
            if (barHeight > 8) {
                drawRect(barX+1, barY+1, barX+7, barY+barHeight-1, 0xFF777777);
                drawRect(barX+1, barY+barHeight/2-3, barX+7, barY+barHeight/2-2, 0xFF333333);
            }
            drawRect(barX+1, barY+barHeight/2+2, barX+7, barY+barHeight/2+3, 0xFF333333);
        }
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.popMatrix();
    }
}
