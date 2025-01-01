package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

public class ElementHolder extends Element {

    private final List<ElementAndCoords> elementList = new ArrayList<>();

    public static ElementHolder makeHolder(int width, int height) {
        return new ElementHolder(width, height);
    }

    protected ElementHolder(int width, int height) {
        super(width, height);
    }

    public <T extends Element> T addElement(int x, int y, T element) {
        elementList.add(new ElementAndCoords(x, y, element));
        return element;
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
        for (ElementAndCoords elementAndCoords : elementList) {
            poseStack.pushPose();
            poseStack.translate(elementAndCoords.x, elementAndCoords.y, 0);
            elementAndCoords.element.render(poseStack, graphics, color, relativeMouseX - elementAndCoords.x, relativeMouseY - elementAndCoords.y, pPartialTick);
            poseStack.popPose();
        }
    }

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        boolean flag = false;
        for (ElementAndCoords elementAndCoords : elementList) {
            if (elementAndCoords.element.mouseClicked(relativeMouseX - elementAndCoords.x, relativeMouseY - elementAndCoords.y, mouseButton)) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        boolean flag = false;
        for (ElementAndCoords elementAndCoords : elementList) {
            if (elementAndCoords.element.keyPressed(pKeyCode, pScanCode, pModifiers)) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        boolean flag = false;
        for (ElementAndCoords elementAndCoords : elementList) {
            if (elementAndCoords.element.charTyped(pCodePoint, pModifiers)) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean mouseDragged(double relativeMouseX, double relativeMouseY, int pButton, double pDragX, double pDragY) {
        boolean flag = false;
        for (ElementAndCoords elementAndCoords : elementList) {
            if (elementAndCoords.element.mouseDragged(relativeMouseX - elementAndCoords.x, relativeMouseY - elementAndCoords.y, pButton, pDragX, pDragY)) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean mouseReleased(double relativeMouseX, double relativeMouseY, int pButton) {
        boolean flag = false;
        for (ElementAndCoords elementAndCoords : elementList) {
            if (elementAndCoords.element.mouseReleased(relativeMouseX - elementAndCoords.x, relativeMouseY - elementAndCoords.y, pButton)) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean mouseScrolled(double relativeMouseX, double relativeMouseY, double pDelta) {
        boolean flag = false;
        for (ElementAndCoords elementAndCoords : elementList) {
            if (elementAndCoords.element.mouseScrolled(relativeMouseX - elementAndCoords.x, relativeMouseY - elementAndCoords.y, pDelta)) {
                flag = true;
            }
        }
        return flag;
    }

    private record ElementAndCoords(int x, int y, Element element) {
    }

}
