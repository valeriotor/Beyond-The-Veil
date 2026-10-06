package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import static net.minecraft.client.gui.components.AbstractWidget.WIDGETS_LOCATION;

public class ShortcutHint extends Element {

    private final Component hint;
    private final Component keyName;
    private final int buttonOffset;
    private final int buttonWidth;

    public ShortcutHint(int height, Component hint, KeyMapping key, int buttonOffset, int buttonWidth) {
        this(height, hint, key.getTranslatedKeyMessage(), buttonOffset, buttonWidth);
    }

    public ShortcutHint(int height, Component hint, Component keyName, int buttonOffset, int buttonWidth) {
        super(buttonWidth + buttonOffset, height);
        this.hint = hint;
        this.keyName = keyName;
        this.buttonOffset = buttonOffset;
        this.buttonWidth = buttonWidth;
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
        graphics.drawString(Minecraft.getInstance().font, hint, 0, 5, color);
        graphics.blitNineSliced(WIDGETS_LOCATION, buttonOffset, 0, buttonWidth, this.getHeight(), 20, 4, 200, 20, 0, 66);
        graphics.drawCenteredString(Minecraft.getInstance().font, keyName, buttonOffset + buttonWidth / 2, 5, 0xFFFFFFFF);
    }
}
