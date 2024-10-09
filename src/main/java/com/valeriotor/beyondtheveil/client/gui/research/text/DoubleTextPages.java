package com.valeriotor.beyondtheveil.client.gui.research.text;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;

public class DoubleTextPages extends Element {

    private final List<TextBlock> blocks;
    private final int blockWidth;
    private final int secondBlockX;
    private int index;

    public static DoubleTextPages makePages(String localized, int width, int height, int blockWidth, Font f) {
        TextUtil textUtil = new TextUtil();
        List<Element> elements = textUtil.parseText(localized, blockWidth, f);
        List<TextBlock> blocks = new ArrayList<>();
        int index = 0;
        while (index < elements.size()) {
            Tuple<TextBlock, Integer> tuple = TextBlock.fillBlockWithElements(elements, index, blockWidth, height, f);
            blocks.add(tuple.getA());
            index = tuple.getB();
        }
        return new DoubleTextPages(blocks, width, height, blockWidth);
    }

    private DoubleTextPages(List<TextBlock> blocks, int width, int height, int blockWidth) {
        super(width, height);
        this.blocks = ImmutableList.copyOf(blocks);
        this.blockWidth = blockWidth;
        this.secondBlockX = width - blockWidth;
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
        TextBlock firstBlock = index * 2 >= blocks.size() ? null : blocks.get(index * 2);
        TextBlock secondBlock = index * 2 + 1 >= blocks.size() ? null : blocks.get(index * 2 + 1);

        if (firstBlock != null) {
            firstBlock.render(poseStack, graphics, color, relativeMouseX, relativeMouseY);
        }
        if (secondBlock != null) {
            poseStack.pushPose();
            poseStack.translate(secondBlockX, 0, 0);
            secondBlock.render(poseStack, graphics, color, relativeMouseX, relativeMouseY);
            poseStack.popPose();
        }
    }

    public void previousScreen() {
        if (index > 0) {
            index--;
        }
    }

    public void nextScreen() {
        if (index < (blocks.size() - 1) / 2) {
            index++;
        }
    }

    public boolean isFirstScreen() {
        return index == 0;
    }

    public boolean isLastScreen() {
        return index == (blocks.size() - 1) / 2;
    }

}
