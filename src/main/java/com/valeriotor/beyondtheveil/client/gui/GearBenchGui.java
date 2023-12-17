package com.valeriotor.beyondtheveil.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.container.GearBenchContainer;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GearBenchGui extends AbstractContainerScreen<GearBenchContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/gui/gear_bench.png");

    public GearBenchGui(GearBenchContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 192;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = this.height / 2 - 116;
        guiGraphics.blit(TEXTURE, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
