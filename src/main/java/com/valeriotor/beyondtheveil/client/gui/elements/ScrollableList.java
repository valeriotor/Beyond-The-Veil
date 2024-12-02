package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

import java.util.List;

public class ScrollableList extends Element {

    private List<? extends Element> rows;
    private final int rowHeight;
    private final int scrollbarWidth;
    private final int renderedRows;
    private int currentFirstRow = 0;
    private int maxFirstRow;
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

    public void changeElements(List<Element> rows) {
        this.rows = rows;
        this.maxFirstRow = renderedRows >= rows.size() ? 0 : rows.size() - renderedRows;
        this.currentFirstRow = Math.min(currentFirstRow, rows.size());
    }

    public int getMaxFirstRow() {
        return maxFirstRow;
    }

    public int getCurrentFirstRow() {
        return currentFirstRow;
    }

    public void setCurrentFirstRow(int currentFirstRow) {
        this.currentFirstRow = Mth.clamp(currentFirstRow, 0, maxFirstRow);
    }


    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
        render(poseStack, graphics, color, relativeMouseX, relativeMouseY, 0);
    }


    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, int startFrom) {
        if (maxFirstRow > 0) {
            graphics.fill(getWidth() - scrollbarWidth, 0, getWidth(), getHeight(), 0x99000000);
            int thumbY = getThumbY();
            renderThumb(poseStack, graphics, thumbY);
        }
        for (int i = currentFirstRow + startFrom; i < currentFirstRow + renderedRows && i < rows.size(); i++) {
            int y = (i - currentFirstRow) * rowHeight;
            poseStack.pushPose();
            poseStack.translate(0, y, 0);
            renderElement(i, poseStack, graphics, color, relativeMouseX, relativeMouseY, y);
            poseStack.popPose();
        }
    }

    protected void renderElement(int element, PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, int y) {
        rows.get(element).render(poseStack, graphics, color, relativeMouseX, relativeMouseY - y);
    }

    private int getThumbY() {
        return maxFirstRow == 0 ? 0 : (getHeight() - thumbHeight) * currentFirstRow / maxFirstRow;
    }

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        int hoveredElement = getHoveredElement(relativeMouseX, relativeMouseY);
        if (hoveredElement != -1) {
            clickElement(hoveredElement, relativeMouseX, relativeMouseY, mouseButton);
            return true;
        } else if (relativeMouseX > getWidth() - scrollbarWidth && relativeMouseX < getWidth() && relativeMouseY >= getThumbY() && relativeMouseY <= getThumbY() + thumbHeight) {
            draggingThumb = true;
            return true;
        }
        return false;
    }

    protected void clickElement(int element, double relativeMouseX, double relativeMouseY, int mouseButton) {
        rows.get(element).mouseClicked(relativeMouseX, relativeMouseY - (element - currentFirstRow) * rowHeight, mouseButton);
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
        if (insideBounds(relativeMouseX, relativeMouseY)) {
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

    protected int getHoveredElement(double relativeMouseX, double relativeMouseY) {
        if (relativeMouseX >= 0 && relativeMouseX < getWidth() - scrollbarWidth && relativeMouseY >= 0 && relativeMouseY <= getHeight()) {
            int i = (int) (relativeMouseY / rowHeight + currentFirstRow);
            if (i < rows.size()) {
                return i;
            }
        }
        return -1;
    }
}
