package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

import java.util.List;

public class ScrollableList<T extends Element> extends Element {

    private List<T> rows;
    private final int rowHeight;
    private final int scrollbarWidth;
    private final int renderedRows;
    private int currentFirstRow = 0;
    private int maxFirstRow;
    private int thumbHeight = 15;
    private boolean draggingThumb = false;
    private boolean variableSize;

    public ScrollableList(int width, int height, List<T> rows, int rowHeight, int scrollbarWidth) {
        super(width, height);
        this.rows = rows;
        this.rowHeight = rowHeight;
        this.scrollbarWidth = scrollbarWidth;
        this.renderedRows = height / rowHeight;
        this.maxFirstRow = renderedRows >= rows.size() ? 0 : rows.size() - renderedRows;
        thumbHeight = 40;
    }

    public void setVariableSize(boolean variableSize) {
        this.variableSize = variableSize;
    }

    public void changeElements(List<T> rows) {
        this.rows = rows;
        this.maxFirstRow = renderedRows >= rows.size() ? 0 : rows.size() - renderedRows;
        this.currentFirstRow = Math.min(currentFirstRow, rows.size()); // TODO wait shouldn't it be maxFirstRow as second arg?
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

    public int getNumberOfElements() {
        return rows.size();
    }


    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
        render(poseStack, graphics, color, relativeMouseX, relativeMouseY, pPartialTick, 0, true);
    }


    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick, int startFrom, boolean drawScrollbar) {
        if (maxFirstRow > 0 && drawScrollbar) {
            graphics.fill(getWidth() - scrollbarWidth, 0, getWidth(), getHeight(), 0x99000000);
            int thumbY = getThumbY();
            renderThumb(poseStack, graphics, thumbY);
        }
        for (int i = currentFirstRow + startFrom; i < currentFirstRow + renderedRows && i < rows.size(); i++) {
            int y = relativeYForElement(i);
            poseStack.pushPose();
            poseStack.translate(0, y, 0);
            renderElement(i, poseStack, graphics, color, relativeMouseX, relativeMouseY, y, pPartialTick);
            poseStack.popPose();
        }
    }

    protected void renderElement(int element, PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, int y, float pPartialTick) {
        T e = rows.get(element);
        if (e instanceof NumberedListElement ne) {
            ne.renderIndexed(poseStack, graphics, color, relativeMouseX, relativeMouseY - y, pPartialTick, element);
        } else {
            e.render(poseStack, graphics, color, relativeMouseX, relativeMouseY - y, pPartialTick);
        }
    }

    private int getThumbY() {
        return maxFirstRow == 0 ? 0 : (getHeight() - thumbHeight) * currentFirstRow / maxFirstRow;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        for (int i = currentFirstRow; i < currentFirstRow + renderedRows && i < rows.size(); i++) {
            if (rows.get(i).keyPressed(pKeyCode, pScanCode, pModifiers)) {
                return true;
            }
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        for (int i = currentFirstRow; i < currentFirstRow + renderedRows && i < rows.size(); i++) {
            if (rows.get(i).charTyped(pCodePoint, pModifiers)) {
                return true;
            }
        }
        return super.charTyped(pCodePoint, pModifiers);
    }

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        int hoveredElement = getHoveredElement(relativeMouseX, relativeMouseY);
        if (!variableSize && hoveredElement != -1) {
            clickElement(hoveredElement, relativeMouseX, relativeMouseY, mouseButton);
            return true;
        } else {
            if (variableSize) {
                for (int i = currentFirstRow; i < currentFirstRow + renderedRows && i < rows.size(); i++) {
                    if (clickElement(i, relativeMouseX, relativeMouseY, mouseButton)) {
                        return true;
                    }
                }
            }
            if (relativeMouseX > getWidth() - scrollbarWidth && relativeMouseX < getWidth() && relativeMouseY >= getThumbY() && relativeMouseY <= getThumbY() + thumbHeight) {
                draggingThumb = true;
                return true;
            }
        }
        return false;
    }

    protected boolean clickElement(int element, double relativeMouseX, double relativeMouseY, int mouseButton) {
        return rows.get(element).mouseClicked(relativeMouseX, relativeMouseY - relativeYForElement(element), mouseButton);
    }

    protected boolean scrollElement(int element, double relativeMouseX, double relativeMouseY, double pDelta) {
        return rows.get(element).mouseScrolled(relativeMouseX, relativeMouseY - relativeYForElement(element), pDelta);
    }

    protected boolean dragElement(int element, double relativeMouseX, double relativeMouseY, int pButton, double pDragX, double pDragY) {
        return rows.get(element).mouseDragged(relativeMouseX, relativeMouseY - relativeYForElement(element), pButton, pDragX, pDragY);
    }

    protected boolean releaseElement(int element, double relativeMouseX, double relativeMouseY, int mouseButton) {
        return rows.get(element).mouseReleased(relativeMouseX, relativeMouseY - relativeYForElement(element), mouseButton);
    }

    protected int relativeYForElement(int element) {
        return (element - currentFirstRow) * rowHeight;
    }

    protected List<T> rows() {
        return rows;
    }

    @Override
    public boolean mouseDragged(double relativeMouseX, double relativeMouseY, int pButton, double pDragX, double pDragY) {
        for (int i = currentFirstRow; i < currentFirstRow + renderedRows && i < rows.size(); i++) {
            if (dragElement(i, relativeMouseX, relativeMouseY, pButton, pDragX, pDragY)) {
                return true;
            }
        }
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
        for (int i = currentFirstRow; i < currentFirstRow + renderedRows && i < rows.size(); i++) {
            if (releaseElement(i, relativeMouseX, relativeMouseY, pButton)) {
                return true;
            }
        }
        if (draggingThumb) {
            draggingThumb = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double relativeMouseX, double relativeMouseY, double pDelta) {
        for (int i = currentFirstRow; i < currentFirstRow + renderedRows && i < rows.size(); i++) {
            if (scrollElement(i, relativeMouseX, relativeMouseY, pDelta)) {
                return true;
            }
        }
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

    @Override
    public boolean insideBounds(double relativeMouseX, double relativeMouseY) {
        return super.insideBounds(relativeMouseX, relativeMouseY);
    }

    public interface NumberedListElement {
        default void renderIndexed(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick, int index) {
            render(poseStack, graphics, color, relativeMouseX, relativeMouseY, pPartialTick);
        }

        void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick);

    }

}
