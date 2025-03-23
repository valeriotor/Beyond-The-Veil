package com.valeriotor.beyondtheveil.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.ClientUtil;
import com.valeriotor.beyondtheveil.container.DreamBottleContainer;
import com.valeriotor.beyondtheveil.lib.BTVFluids;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class DreamBottleGui extends AbstractContainerScreen<DreamBottleContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/gui/dream_bottle.png");
    private static final ResourceLocation TEXTURE_CHARGES = new ResourceLocation(References.MODID, "textures/gui/dream_bottle_charges.png");
    private final TextureAtlasSprite stillSprite;

    public DreamBottleGui(DreamBottleContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 192;

        FluidState defaultFluidState = BTVFluids.FLUID_TEARS.getA().get().defaultFluidState();
        IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(defaultFluidState);
        ResourceLocation stillTexture = props.getStillTexture();
        stillSprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(stillTexture);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        //super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        PoseStack pose = guiGraphics.pose();
        RenderSystem.enableBlend();
        int relX = (this.width - this.imageWidth) / 2;
        int relY = this.height / 2 - 116;
        guiGraphics.blit(TEXTURE, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        FluidStack fluidInTank = menu.getFluidHandler().getFluidInTank(0);
        if (!fluidInTank.isEmpty()) {
            ClientUtil.blit(TEXTURE_CHARGES, relX + 140, relY + 35, 34, 46, 0, 0, 17, 23, 32, 32, pose);
            float ratio = fluidInTank.getAmount() / 4000F;
            ClientUtil.blit(TEXTURE_CHARGES, relX + 142, relY + 35 + 42 - 2 * 20 * ratio, 28, 2 * 20 * ratio, 17, 21 - 20 * ratio, 15, 20 * ratio, 32, 32, pose);

        }
        RenderSystem.disableBlend();

    }
}
