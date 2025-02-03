package com.valeriotor.beyondtheveil.client.gui;

import com.valeriotor.beyondtheveil.container.LetterBoxContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class LetterBoxGui extends AbstractContainerScreen<LetterBoxContainer> {

    public LetterBoxGui(LetterBoxContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {

    }
}
