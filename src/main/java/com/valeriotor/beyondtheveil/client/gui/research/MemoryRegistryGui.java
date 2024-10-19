package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.ForgeHooksClient;

public class MemoryRegistryGui extends Screen {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/research/memory_registry_background.png");
    private static final ResourceLocation PAGE = new ResourceLocation(References.MODID, "textures/gui/research/memory_registry_page.png");
    private static final int PAGE_WIDTH = 1400, PAGE_HEIGHT = 1200;

    private int pageLeftX;
    private int pageTopY;
    private int pageRightX;
    private int pageBottomY;
    private int pageHeight;
    private int pageWidth;

    protected MemoryRegistryGui(ResearchStatus status) {
        super(Component.translatable(status.res.getName()));
    }

    @Override
    protected void init() {
        int blackPageMargin = Math.min(100 * width / 1400, 200);
        pageTopY = height * blackPageMargin / 1440;
        pageBottomY = (height * (1440 - blackPageMargin)) / 1440;
        this.pageHeight = pageBottomY - pageTopY;
        this.pageWidth = pageHeight * PAGE_WIDTH / PAGE_HEIGHT;
        pageLeftX = width / 2 - pageWidth / 2;
        pageRightX = width / 2 + pageWidth / 2;
        if (pageLeftX < 20) {
            pageLeftX = 20;
            pageRightX = width - 20;
            pageWidth = width - 40;
            pageHeight = pageWidth * PAGE_HEIGHT / PAGE_WIDTH;
            pageTopY = height / 2 - pageHeight / 2;
            pageBottomY = height / 2 + pageHeight / 2;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);

        PoseStack pose = guiGraphics.pose();
        //guiGraphics.blit(BACKGROUND, 0, 0, 0, 0, width, height, 2560, 1440);
        guiGraphics.blit(BACKGROUND, 0, 0, width, height, 0, 0, 2560, 1440, 2560, 1440);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(0, 0.2F, 1, 0.2F);
        TextureAtlasSprite[] fluidSprites = ForgeHooksClient.getFluidSprites(minecraft.level, minecraft.player.getOnPos(), Fluids.WATER.defaultFluidState());//Registration.SOURCE_FLUID_COAGULANT.get().defaultFluidState());
        TextureAtlasSprite stillSprite = fluidSprites[0];
        int offX = 21, offY = 21;
        guiGraphics.blit(pageLeftX + pageWidth * offX / 1000, pageTopY + pageHeight * offY / 1000, 0, pageWidth * (1000 - 2 * offX) / 1000, pageHeight * (1000 - 2 * offY) / 1000, stillSprite);//.blit(new ResourceLocation("textures/block/water_still.png"), pageLeftX, pageTopY, pageWidth, pageHeight, 0, 0, 16, 16, pageWidth, pageHeight);
        //minecraft.textureManager.
        RenderSystem.setShaderColor(1, 1, 1, 1);
        guiGraphics.blit(PAGE, pageLeftX, pageTopY, pageWidth, pageHeight, 0, 0, pageWidth, pageHeight, pageWidth, pageHeight);

    }
}
