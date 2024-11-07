package com.valeriotor.beyondtheveil.client.gui.dialogue;

import com.valeriotor.beyondtheveil.container.dialogue.ShoremanDialogueMenu;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ShoremanDialogueGui extends AbstractContainerScreen<ShoremanDialogueMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/gui/dialogue/shoreman.png");


    public ShoremanDialogueGui(ShoremanDialogueMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 512;
        this.imageHeight = 166;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        //this.renderBackground(guiGraphics);
        //super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        //this.renderTooltip(guiGraphics, pMouseX, pMouseY);
        renderBg(guiGraphics, pPartialTick, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = this.height - this.imageHeight;
        guiGraphics.blit(TEXTURE, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        guiGraphics.blit(TEXTURE, relX, relY, 512, 166, 0, 0, 512, 166, 512, 166);
    }
}
