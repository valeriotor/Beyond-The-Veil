package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class TexturedButton extends Element{

    private final ResourceLocation texture;
    private final int highlightColor;
    private Component text;
    private final Consumer<TexturedButton> action;
    public boolean visible = true;
    public boolean active = true;

    public TexturedButton(int width, int height, ResourceLocation texture, int highlightColor, Component text, Consumer<TexturedButton> action) {
        super(width, height);
        this.texture = texture;
        this.highlightColor = highlightColor;
        this.text = text;
        this.action = action;
    }

    public void setText(Component text) {
        this.text = text;
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
        if(visible) {
            //graphics.blitNineSliced(texture, 0, 0, this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, 0);
            //graphics.blit(texture, 0, 0, 0, 0, 20, 20);

            //graphics.blitNineSliced(texture, 0, 0, getWidth(), getHeight(), 10, 10, 10, 10, getWidth(), getHeight(), 0, 0);
            graphics.blit(texture, 0, 0, 5, getHeight(), 0, 0, 5, 20, 200, 20);
            graphics.blitRepeating(texture, 5, 0, getWidth() - 10, getHeight(), 5, 0, 200, 20, 200, 20);
            graphics.blit(texture, getWidth() - 5, 0, 5, getHeight(), 195, 0, 5, 20, 200, 20);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(0, 0, getWidth(), getHeight(), highlightColor);
            }
            graphics.drawCenteredString(Minecraft.getInstance().font, text, getWidth() / 2, getHeight() / 2 - 3, color);
        }
    }

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        if (active && insideBounds(relativeMouseX, relativeMouseY)) {
            action.accept(this);
            return true;
        }
        return false;
    }
}
