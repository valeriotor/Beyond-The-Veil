package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.recipes.GearBenchRecipe;
import com.valeriotor.beyondtheveil.research.Research;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.ForgeHooksClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MemoryRegistryGui extends Screen {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/research/memory_registry_background.png");
    private static final ResourceLocation PAGE = new ResourceLocation(References.MODID, "textures/gui/research/memory_registry_page.png");
    private static final ResourceLocation ENTRY = new ResourceLocation(References.MODID, "textures/gui/research/memory_registry_entry.png");
    private static final int PAGE_WIDTH = 1400, PAGE_HEIGHT = 1200, MEMORIES_PER_PAGE = 10;

    private int pageLeftX;
    private int pageTopY;
    private int pageRightX;
    private int pageBottomY;
    private int pageHeight;
    private int pageWidth;
    private int entryListWidth, entryListHeight, scrollbarWidth, listLeftX, listLeftY, listBackgroundLeftX, listBackgroundLeftY, listBackgroundWidth, listBackgroundHeight;
    private ScrollableList memoryList;

    private final Map<Memory, Research> knownMemories;

    protected MemoryRegistryGui(ResearchStatus status) {
        super(Component.translatable(status.res.getName()));
        knownMemories = ResearchUtil.getKnownMemories(Minecraft.getInstance().player);
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

        entryListWidth = 450 * pageWidth / PAGE_WIDTH;
        entryListHeight = 1060 * pageHeight / PAGE_HEIGHT;
        scrollbarWidth = 15 * pageWidth / PAGE_WIDTH;

        listLeftX = pageLeftX + 37 * pageWidth / PAGE_WIDTH;
        listLeftY = pageTopY + 72 * pageHeight / PAGE_HEIGHT;

        listBackgroundLeftX = pageLeftX + 30 * pageWidth / PAGE_WIDTH;
        listBackgroundLeftY = pageTopY + 29 * pageHeight / PAGE_HEIGHT;
        listBackgroundWidth = entryListWidth;
        listBackgroundHeight = 1143 * pageHeight / PAGE_HEIGHT;

        initList();
    }

    private void initList() {
        List<MemoryEntry> entries = new ArrayList<>();
        for (Memory knownMemory : knownMemories.keySet()) {
            entries.add(new MemoryEntry(knownMemory));
        }
        entries.sort(Comparator.comparing(m -> m.memory.getTranslationComponent().getString()));
        memoryList = new ScrollableList(entryListWidth, entryListHeight, entries, entryListHeight / MEMORIES_PER_PAGE, scrollbarWidth);
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

        guiGraphics.fill(listBackgroundLeftX, listBackgroundLeftY, listBackgroundLeftX + listBackgroundWidth, listBackgroundLeftY + listBackgroundHeight, 0x55000000);

        pose.pushPose();
        pose.translate(listLeftX, listLeftY, 0);
        memoryList.render(pose, guiGraphics, 0xFFFFFFFF, pMouseX - listLeftX, pMouseY - listLeftY);
        pose.popPose();

    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(pKeyCode, pScanCode);
        if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
            minecraft.setScreen(new NecronomiconGui());
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (memoryList.mouseClicked(pMouseX - listLeftX, pMouseY - listLeftY, pButton)) {
            return true;
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        memoryList.mouseDragged(pMouseX - listLeftX, pMouseY - listLeftY, pButton, pDragX, pDragY);
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        memoryList.mouseReleased(pMouseX - listLeftX, pMouseY - listLeftY, pButton);
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        memoryList.mouseScrolled(pMouseX - listLeftX, pMouseY - listLeftY, pDelta);
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    private class MemoryEntry extends Element {

        private final Memory memory;
        private final ItemStack icon;
        private final float scaleFactor;

        protected MemoryEntry(Memory memory) {
            super(entryListWidth - scrollbarWidth, entryListHeight / MEMORIES_PER_PAGE);
            this.memory = memory;
            icon = new ItemStack(Registration.MEMORY_PHIAL.get());
            icon.getOrCreateTag().putString("memory", memory.getDataName());
            scaleFactor = Math.min(1, getHeight() / 40F);
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
            graphics.blit(ENTRY, 0, 0, getWidth(), getHeight(), 0, 0, 400, 104, 400, 104);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(0, 0, getWidth(), getHeight(), 0x44604533);
            }
            poseStack.pushPose();
            poseStack.translate(getWidth() / 7, getHeight() / 2, 0);
            poseStack.scale(2.5F, 2.5F, 1);
            poseStack.scale(scaleFactor, scaleFactor, 1);
            graphics.renderItem(icon, -8, -8);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(getWidth() / 3, getHeight() / 3, 0);
            //poseStack.scale(2.5F, 2.5F, 1);
            poseStack.scale(scaleFactor, scaleFactor, 1);
            graphics.drawString(minecraft.font, memory.getTranslationComponent(), 0, 0, 0xFF697D57);
            poseStack.popPose();
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                //selectEntry(stack.getItem(), recipe);
                //selectedEntry = this;
                //Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                //return true;
            }
            return false;
        }
    }


}
