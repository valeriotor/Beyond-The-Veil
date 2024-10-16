package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

public abstract class Element {

    private final int width;
    private final int height;
    protected int counter;

    protected Element(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean insertOutOfBounds() {
        return false;
    }

    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        return false;
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        return false;
    }

    public boolean mouseDragged(double relativeMouseX, double relativeMouseY, int pButton, double pDragX, double pDragY) {
        return false;
    }

    public boolean mouseReleased(double relativeMouseX, double relativeMouseY, int pButton) {
        return false;
    }

    public boolean mouseScrolled(double relativeMouseX, double relativeMouseY, double pDelta) {
        return false;
    }

    protected boolean insideBounds(double relativeMouseX, double relativeMouseY) {
        return relativeMouseX >= 0 && relativeMouseX < getWidth() && relativeMouseY >= 0 && relativeMouseY < getHeight();
    }

    public void tick() {
        counter++;
    }

}
