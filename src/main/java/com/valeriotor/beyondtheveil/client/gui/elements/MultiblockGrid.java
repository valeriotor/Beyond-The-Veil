package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.util.multiblocks.MultiblockSchematic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class MultiblockGrid extends Element{

    private final MultiblockSchematic schematic;
    private final Component translationComponent;
    private Component layerComponent;
    private int layerIndex;
    private int layerComponentWidth;
    private static final int CHARACTER_WIDTH = 11;

    public MultiblockGrid(int width, int height, MultiblockSchematic schematic) {
        super(width, height);
        this.schematic = schematic;
        translationComponent = schematic.getTranslationComponent();
        layerIndex = 0;
        setLayerComponent();
    }

    public MultiblockSchematic getSchematic() {
        return schematic;
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
        poseStack.pushPose();
        poseStack.translate(getWidth() / 2D, 0, 0);
        poseStack.scale(1.5F, 1.5F, 1);
        graphics.drawCenteredString(Minecraft.getInstance().font, translationComponent, 0, 0, 0xFFFFFFFF);
        poseStack.popPose();

        graphics.drawCenteredString(Minecraft.getInstance().font, layerComponent, getWidth() / 2, 32, 0xFFFFFFFF);

        if (layerIndex >= 0 && layerIndex < schematic.getSchematic().length) {
            ItemStack[][] layer = schematic.getSchematic()[this.layerIndex];
            for (int i = 0; i < schematic.getSideSize(); i++) {
                for (int j = 0; j < schematic.getSideSize(); j++) {
                    int x = (int) (getWidth() / 2 + 21 * (i - schematic.getSideSize() / 2D));
                    int y = (int) (j * 21 + 50);
                    ItemStack itemStack = layer[i][j];
                    if (!itemStack.isEmpty()) {
                        graphics.renderItem(itemStack, x, y);
                        if (relativeMouseX >= x && relativeMouseX <= x + 21 && relativeMouseY >= y && relativeMouseY <= y + 21) {
                            graphics.renderTooltip(Minecraft.getInstance().font, itemStack.getTooltipLines(Minecraft.getInstance().player, TooltipFlag.NORMAL), itemStack.getTooltipImage(), relativeMouseX, relativeMouseY);
                        }
                    }
                }
            }
        }
        renderPlusOrMinus(poseStack, graphics, color, relativeMouseX, relativeMouseY, pPartialTick, true);
        renderPlusOrMinus(poseStack, graphics, color, relativeMouseX, relativeMouseY, pPartialTick, false);
        //if (insideBounds(relativeMouseX, relativeMouseY)) {
        //    graphics.drawString(Minecraft.getInstance().font, String.format("X: %d, Y: %d", relativeMouseX, relativeMouseY), 0, 100, 0xFFFFFFFF);
        //}
    }

    private void renderPlusOrMinus(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick, boolean plus) {
        if ((plus && layerIndex == schematic.getSchematic().length - 1) || (!plus && layerIndex == 0)) {
            return;
        }
        poseStack.pushPose();
        double pX = getXForPlusOrMinus(plus);
        int pY = 32;
        poseStack.translate(pX, pY, 0);
        if (hoveringPlusOrMinus(plus, relativeMouseX, relativeMouseY)) {
            poseStack.scale(1.5F, 1.5F, 1);
        }
        graphics.drawCenteredString(Minecraft.getInstance().font, plus ? "+" : "-", 0, 0, 0xFFFFFFFF);
        poseStack.popPose();
    }

    private boolean hoveringPlusOrMinus(boolean plus, double relativeMouseX, double relativeMouseY) {
        double pX = getXForPlusOrMinus(plus);
        int pY = 30;
        return relativeMouseX > pX - CHARACTER_WIDTH && relativeMouseX < pX + CHARACTER_WIDTH && relativeMouseY > pY - CHARACTER_WIDTH && relativeMouseY < pY + CHARACTER_WIDTH;
    }

    private double getXForPlusOrMinus(boolean plus) {
        return getWidth() / 2D + (plus ? 1 : -1) * layerComponentWidth * 3.1D / 3D;
    }

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        if (hoveringPlusOrMinus(true, relativeMouseX, relativeMouseY)) {
            increaseLayer();
        } else if (hoveringPlusOrMinus(false, relativeMouseX, relativeMouseY)) {
            decreaseLayer();
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double relativeMouseX, double relativeMouseY, double pDelta) {
        if (insideBounds(relativeMouseX, relativeMouseY)) {
            if (pDelta > 0) {
                increaseLayer();
            } else if (pDelta < 0) {
                decreaseLayer();
            } else {
                return false;
            }
            return true;
        }
        return super.mouseScrolled(relativeMouseX, relativeMouseY, pDelta);
    }

    private void increaseLayer() {
        int newIndex = Math.min(schematic.getSchematic().length - 1, layerIndex + 1);
        if (layerIndex != newIndex) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
        }
        layerIndex = newIndex;
        setLayerComponent();
    }

    private void decreaseLayer() {
        int newIndex = Math.max(0, layerIndex - 1);
        if (layerIndex != newIndex) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
        }
        layerIndex = newIndex;
        setLayerComponent();
    }

    private void setLayerComponent() {
        layerComponent = Component.translatable("gui.multiblock.layer", layerIndex);
        layerComponentWidth = Minecraft.getInstance().font.width(layerComponent);
    }

}
