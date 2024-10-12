package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

import java.util.List;

public class ScrollableList extends Element {

    private final List<? extends Element> rows;
    private final int rowHeight;
    private final int scrollbarWidth;
    private final int renderedRows;
    private int currentFirstRow = 0;
    private final int maxFirstRow;
    private int thumbHeight = 15;
    private boolean draggingThumb = false;

    public ScrollableList(int width, int height, List<? extends Element> rows, int rowHeight, int scrollbarWidth) {
        super(width, height);
        this.rows = rows;
        this.rowHeight = rowHeight;
        this.scrollbarWidth = scrollbarWidth;
        this.renderedRows = height / rowHeight;
        this.maxFirstRow = renderedRows >= rows.size() ? 0 : rows.size() - renderedRows;
        thumbHeight = 40;
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
        graphics.fill(getWidth() - scrollbarWidth, 0, getWidth(), getHeight(), 0x99000000);
        if (maxFirstRow > 0) {
            int thumbY = getThumbY();
            renderThumb(poseStack, graphics, thumbY);
        }
        for (int i = currentFirstRow; i < currentFirstRow + renderedRows && i < rows.size(); i++) {
            int y = (i - currentFirstRow) * rowHeight;
            poseStack.pushPose();
            poseStack.translate(0, y, 0);
            rows.get(i).render(poseStack, graphics, color, relativeMouseX, relativeMouseY - y);
            poseStack.popPose();
        }
    }

    private int getThumbY() {
        return maxFirstRow == 0 ? 0 : (getHeight() - thumbHeight) * currentFirstRow / maxFirstRow;
    }

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        int hoveredElement = getHoveredElement(relativeMouseX, relativeMouseY);
        if (hoveredElement != -1) {
            rows.get(hoveredElement).mouseClicked(relativeMouseX, relativeMouseY - (hoveredElement - currentFirstRow) * rowHeight, mouseButton);
            return true;
        } else if (relativeMouseX > getWidth() - scrollbarWidth && relativeMouseX < getWidth() && relativeMouseY >= getThumbY() && relativeMouseY <= getThumbY() + thumbHeight) {
            draggingThumb = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double relativeMouseX, double relativeMouseY, int pButton, double pDragX, double pDragY) {
        if (draggingThumb) {
            if (relativeMouseY < 0) {
                currentFirstRow = 0;
            } else if (relativeMouseY > getHeight() - thumbHeight) {
                currentFirstRow = rows.size() - renderedRows;
            } else {
                double ratio = relativeMouseY / (getHeight() - thumbHeight);
                currentFirstRow = (int) Math.floor(maxFirstRow * ratio + 0.5);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double relativeMouseX, double relativeMouseY, int pButton) {
        if (draggingThumb) {
            draggingThumb = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double relativeMouseX, double relativeMouseY, double pDelta) {
        if(insideBounds(relativeMouseX, relativeMouseY)) {
            int move = (int) Math.signum(-pDelta);
            currentFirstRow = Mth.clamp(currentFirstRow + move, 0, maxFirstRow);
            if (pDelta != 0) {
                return true;
            }
        }
        return super.mouseScrolled(relativeMouseX, relativeMouseY, pDelta);
    }

    private void renderThumb(PoseStack poseStack, GuiGraphics graphics, int y) {
        poseStack.pushPose();
        poseStack.translate(getWidth() - scrollbarWidth, getThumbY(), 0);
        graphics.fill(0, 0, scrollbarWidth, thumbHeight, 0xFF222222);
        graphics.fill(2, 2, scrollbarWidth - 2, thumbHeight - 2, 0xFF111122);
        graphics.fill(3, thumbHeight / 3, scrollbarWidth - 3, thumbHeight / 3 + 1, 0xFF040410);
        graphics.fill(3, 2 * thumbHeight / 3, scrollbarWidth - 3, 2 * thumbHeight / 3 + 1, 0xFF040410);
        poseStack.popPose();
    }

    private int getHoveredElement(double relativeMouseX, double relativeMouseY) {
        if (relativeMouseX >= 0 && relativeMouseX < getWidth() - scrollbarWidth && relativeMouseY >= 0 && relativeMouseY <= getHeight()) {
            int i = (int) (relativeMouseY / rowHeight + currentFirstRow);
            if (i < rows.size()) {
                return i;
            }
        }
        return -1;
    }
}
