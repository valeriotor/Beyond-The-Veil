package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EditableList<T extends Element & EditableList.EditableListElement> extends ScrollableList<T> {

    private static final ResourceLocation PLUS = new ResourceLocation(References.MODID, "textures/gui/plus.png");
    private static final ResourceLocation CROSS = new ResourceLocation(References.MODID, "textures/gui/cross.png");
    private final Supplier<T> newElement;


    public EditableList(int width, int height, List<T> rows, int rowHeight, int scrollbarWidth, Supplier<T> newElement) {
        super(width, height, rows, rowHeight, scrollbarWidth);
        this.newElement = newElement;
    }

    @Override
    protected void renderElement(int element, PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, int y, float pPartialTick) {
        super.renderElement(element, poseStack, graphics, color, relativeMouseX, relativeMouseY, y, pPartialTick);
        T e = rows().get(element);
        if (e.insideBounds(relativeMouseX, relativeMouseY - y)) {
            e.renderAdd(poseStack, graphics, color, relativeMouseX, relativeMouseY - y);
            e.renderDelete(poseStack, graphics, color, relativeMouseX, relativeMouseY - y);
        }
    }

    @Override
    protected boolean clickElement(int element, double relativeMouseX, double relativeMouseY, int mouseButton) {
        T e = rows().get(element);
        if (e.hoveringAdd(relativeMouseX, relativeMouseY - relativeYForElement(element))) {
            List<T> newList = new ArrayList<>(rows());
            newList.add(element + 1, newElement.get());
            changeElements(newList);
        } else if (e.hoveringDelete(relativeMouseX, relativeMouseY - relativeYForElement(element))) {
            List<T> newList = new ArrayList<>(rows());
            newList.remove(element);
            changeElements(newList);
        } else {
            return super.clickElement(element, relativeMouseX, relativeMouseY, mouseButton);
        }
        return true;
    }

    public interface EditableListElement {

        default void renderAdd(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
            graphics.blit(PLUS, 4, iconY(), 0, 0, 14, 14, 14, 14);
            if (hoveringAdd(relativeMouseX, relativeMouseY)) {
                graphics.fill(4, iconY(), 18, iconY() + 14, 0x5559471A);
            }
        }

        default void renderDelete(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
            graphics.blit(CROSS, 20, iconY(), 0, 0, 14, 14, 14, 14);
            if (hoveringDelete(relativeMouseX, relativeMouseY)) {
                graphics.fill(20, iconY(), 34, iconY() + 14, 0x5559471A);
            }
        }

        default boolean hoveringAdd(double relativeMouseX, double relativeMouseY) {
            return relativeMouseX >= 4 && relativeMouseY >= iconY() && relativeMouseX <= 18 && relativeMouseY <= iconY() + 14;
        }

        default boolean hoveringDelete(double relativeMouseX, double relativeMouseY) {
            return relativeMouseX >= 20 && relativeMouseY >= iconY() && relativeMouseX <= 34 && relativeMouseY <= iconY() + 14;
        }

        private int iconY() {
            return (getHeight() - 14) / 2;
        }

        int getHeight();

    }

}
