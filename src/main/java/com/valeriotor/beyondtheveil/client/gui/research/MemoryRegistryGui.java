package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToServerPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.recipes.GearBenchRecipe;
import com.valeriotor.beyondtheveil.research.Research;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DataUtil;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
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
    private static final int BACKGROUND_BASE_WIDTH = 1400 * 4 / 10;
    private static final int BACKGROUND_BASE_HEIGHT = 1200 * 4 / 10;
    private static final int LIST_LEFT_X = 30;
    private static final int LIST_TOP_Y = 30;
    private static final int LIST_WIDTH = 450 * 4 / 10;
    private static final int LIST_HEIGHT = 1000 * 4 / 10;
    private static final int RIGHT_PAGE_WIDTH = BACKGROUND_BASE_WIDTH - LIST_WIDTH;
    private static final int RIGHT_PAGE_HEIGHT = LIST_HEIGHT;

    private float scaleFactor = 1;
    private int imageWidth;
    private int imageHeight;
    private ScrollableList<MemoryEntry> memoryList;

    private final List<Memory> knownMemories;
    private TextureAtlasSprite stillSprite;
    private MemoryPage memoryPage;

    protected MemoryRegistryGui(ResearchStatus status) {
        super(Component.translatable(status.res.getName()));
        knownMemories = ResearchUtil.getKnownMemories(Minecraft.getInstance().player);
    }

    @Override
    protected void init() {
        scaleFactor = 1;
        imageWidth = BACKGROUND_BASE_WIDTH;
        imageHeight = BACKGROUND_BASE_HEIGHT;
        //entryListWidth = 343;
        //entryListHeight = 357;

        float heightRatio = 95F;
        if (imageHeight > height * heightRatio / 100) {
            scaleFactor = height * heightRatio / 100 / imageHeight;
        }
        float widthRatio = 95F;
        if (imageWidth > width * widthRatio / 100) {
            scaleFactor = Math.min(width * widthRatio / 100 / imageWidth, scaleFactor);
        }

        //int blackPageMargin = Math.min(100 * width / 1400, 200);
        //pageTopY = height * blackPageMargin / 1440;
        //pageBottomY = (height * (1440 - blackPageMargin)) / 1440;
        //this.pageHeight = pageBottomY - pageTopY;
        //this.pageWidth = pageHeight * PAGE_WIDTH / PAGE_HEIGHT;
        //pageLeftX = width / 2 - pageWidth / 2;
        //pageRightX = width / 2 + pageWidth / 2;
        //if (pageLeftX < 20) {
        //    pageLeftX = 20;
        //    pageRightX = width - 20;
        //    pageWidth = width - 40;
        //    pageHeight = pageWidth * PAGE_HEIGHT / PAGE_WIDTH;
        //    pageTopY = height / 2 - pageHeight / 2;
        //    pageBottomY = height / 2 + pageHeight / 2;
        //}

        //entryListWidth = 450 * pageWidth / PAGE_WIDTH;
        //entryListHeight = 1060 * pageHeight / PAGE_HEIGHT;
        //scrollbarWidth = 15 * pageWidth / PAGE_WIDTH;
//
        //listLeftX = pageLeftX + 37 * pageWidth / PAGE_WIDTH;
        //listLeftY = pageTopY + 72 * pageHeight / PAGE_HEIGHT;
//
        //listBackgroundLeftX = pageLeftX + 30 * pageWidth / PAGE_WIDTH;
        //listBackgroundLeftY = pageTopY + 29 * pageHeight / PAGE_HEIGHT;
        //listBackgroundWidth = entryListWidth;
        //listBackgroundHeight = 1143 * pageHeight / PAGE_HEIGHT;

        initList();

        TextureAtlasSprite[] fluidSprites = ForgeHooksClient.getFluidSprites(minecraft.level, minecraft.player.getOnPos(), Fluids.WATER.defaultFluidState());//Registration.SOURCE_FLUID_COAGULANT.get().defaultFluidState());
        stillSprite = fluidSprites[0];
    }

    private void initList() {
        List<MemoryEntry> entries = new ArrayList<>();
        for (Memory knownMemory : knownMemories) {
            entries.add(new MemoryEntry(knownMemory));
        }
        entries.sort(Comparator.comparing(m -> m.memory.getTranslationComponent().getString()));
        memoryList = new ScrollableList<>(LIST_WIDTH, LIST_HEIGHT, entries, LIST_HEIGHT / MEMORIES_PER_PAGE, LIST_WIDTH * 5 / 100);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);

        PoseStack pose = guiGraphics.pose();
        //guiGraphics.blit(BACKGROUND, 0, 0, 0, 0, width, height, 2560, 1440);
        guiGraphics.blit(BACKGROUND, 0, 0, width, height, 0, 0, 2560, 1440, 2560, 1440);

        pose.pushPose();
        pose.translate(width / 2F, height / 2F, 0);
        pose.scale(scaleFactor, scaleFactor, 1);

        pose.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(0, 0.2F, 1, 0.2F);
        pose.translate(-BACKGROUND_BASE_WIDTH / 2F, -BACKGROUND_BASE_HEIGHT / 2F, 0);
        int offX = 12, offY = 10;
        guiGraphics.blit(offX, offY, 0, BACKGROUND_BASE_WIDTH - 2 * offX, BACKGROUND_BASE_HEIGHT - 2 * offY, stillSprite);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        guiGraphics.blit(PAGE, 0, 0, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT, 0, 0, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT);

        pose.pushPose();
        pose.translate(LIST_LEFT_X, LIST_TOP_Y, 0);
        int width = memoryList.getNumberOfElements() > MEMORIES_PER_PAGE ? LIST_WIDTH : LIST_WIDTH * 95 / 100;
        guiGraphics.fill(0, 0, width, LIST_HEIGHT, 0x55000000);
        IntIntPair listMouse = listMouse(pMouseX, pMouseY);
        memoryList.render(pose, guiGraphics, 0xFFFFFFFF, listMouse.firstInt(), listMouse.secondInt(), pPartialTick);
        pose.popPose();

        if (memoryPage != null) {
            pose.pushPose();
            pose.translate(LIST_LEFT_X + LIST_WIDTH, LIST_TOP_Y, 0);
            IntIntPair rightPageMouse = rightPageMouse(pMouseX, pMouseY);
            memoryPage.render(pose, guiGraphics, 0xFFFFFFFF, rightPageMouse.firstInt(), rightPageMouse.secondInt(), pPartialTick);
            pose.popPose();
        }

        pose.popPose();

        pose.popPose();


        //int offX = 21, offY = 21;
        //guiGraphics.blit(pageLeftX + pageWidth * offX / 1000, pageTopY + pageHeight * offY / 1000, 0, pageWidth * (1000 - 2 * offX) / 1000, pageHeight * (1000 - 2 * offY) / 1000, stillSprite);//.blit(new ResourceLocation("textures/block/water_still.png"), pageLeftX, pageTopY, pageWidth, pageHeight, 0, 0, 16, 16, pageWidth, pageHeight);
        ////minecraft.textureManager.
        //RenderSystem.setShaderColor(1, 1, 1, 1);
        //guiGraphics.blit(PAGE, pageLeftX, pageTopY, pageWidth, pageHeight, 0, 0, pageWidth, pageHeight, pageWidth, pageHeight);
//
        //guiGraphics.fill(listBackgroundLeftX, listBackgroundLeftY, listBackgroundLeftX + listBackgroundWidth, listBackgroundLeftY + listBackgroundHeight, 0x55000000);

    }

    private IntIntPair listMouse(double mouseX, double mouseY) {
        double x = ((mouseX - width / 2D) + (BACKGROUND_BASE_WIDTH / 2D - LIST_LEFT_X) * scaleFactor) / scaleFactor;
        double y = ((mouseY - height / 2D) + (BACKGROUND_BASE_HEIGHT / 2D - LIST_TOP_Y) * scaleFactor) / scaleFactor;
        return IntIntPair.of((int) x, (int) y);
    }

    private IntIntPair rightPageMouse(double mouseX, double mouseY) {
        double x = ((mouseX - width / 2D) + (BACKGROUND_BASE_WIDTH / 2D - LIST_LEFT_X - LIST_WIDTH) * scaleFactor) / scaleFactor;
        double y = ((mouseY - height / 2D) + (BACKGROUND_BASE_HEIGHT / 2D - LIST_TOP_Y) * scaleFactor) / scaleFactor;
        return IntIntPair.of((int) x, (int) y);
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
        IntIntPair listMouse = listMouse(pMouseX, pMouseY);
        if (memoryList.mouseClicked(listMouse.firstInt(), listMouse.secondInt(), pButton)) {
            return true;
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        IntIntPair listMouse = listMouse(pMouseX, pMouseY);
        memoryList.mouseDragged(listMouse.firstInt(), listMouse.secondInt(), pButton, pDragX, pDragY);
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        IntIntPair listMouse = listMouse(pMouseX, pMouseY);
        memoryList.mouseReleased(listMouse.firstInt(), listMouse.secondInt(), pButton);
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        IntIntPair listMouse = listMouse(pMouseX, pMouseY);
        memoryList.mouseScrolled(listMouse.firstInt(), listMouse.secondInt(), pDelta);
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    private void selectEntry(Memory memory) {
        memoryPage = new MemoryPage(memory);
    }

    private class MemoryEntry extends Element {

        private final Memory memory;
        private final ItemStack icon;
        private final float scaleFactor;
        private boolean updated;

        protected MemoryEntry(Memory memory) {
            super(LIST_WIDTH * 95 / 100, LIST_HEIGHT / MEMORIES_PER_PAGE);
            this.memory = memory;
            icon = new ItemStack(Registration.MEMORY_PHIAL.get());
            icon.getOrCreateTag().putString("memory", memory.getDataName());
            scaleFactor = Math.min(1, getHeight() / 40F);
            this.updated = DataUtil.getMemoryStatus(getMinecraft().player, memory).isChanged();
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
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
            poseStack.translate(getWidth() / 3, getHeight() * 4 / 10F, 0);
            //poseStack.scale(2.5F, 2.5F, 1);
            poseStack.scale(scaleFactor * 1.75F, scaleFactor * 1.75F, 1);
            graphics.drawString(minecraft.font, memory.getTranslationComponent(), 0, 0, 0xFF697D57);
            poseStack.popPose();

            if (updated) {
                poseStack.pushPose();
                poseStack.translate(getWidth() * 90 / 100F, getHeight() * 0.2F / 4, 0);
                poseStack.scale(0.7F, 0.7F, 1);
                graphics.blit(NecronomiconGui.RESEARCH_UPDATED_MARKER, 0, 0, 24, 24, 0, 0, 24, 24, 24, 24);
                poseStack.popPose();
            }
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                selectEntry(memory);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                if (updated) {
                    updated = false;
                    Messages.sendToServer(GenericToServerPacket.readMemory(memory));
                    DataUtil.getMemoryStatus(getMinecraft().player, memory).setChanged(false);
                }
                return true;
            }
            return false;
        }
    }

    private static class MemoryPage extends Element {

        private final Memory memory;
        private final MutableComponent title;
        private final List<FormattedCharSequence> brief;
        private final ItemStack sieve = new ItemStack(Registration.MEMORY_SIEVE.get());
        private final ItemStack ingredient;
        private final List<List<FormattedCharSequence>> lines;

        protected MemoryPage(Memory memory) {
            super(RIGHT_PAGE_WIDTH, RIGHT_PAGE_HEIGHT);
            this.memory = memory;
            title = Component.translatable(memory.getLocalizationKey());
            brief = Minecraft.getInstance().font.split(Component.translatable("memory." + memory.name().toLowerCase() + ".brief"), 170);
            ingredient = memory.getItem();
            LocalPlayer player = Minecraft.getInstance().player;
            lines = new ArrayList<>();
            if (player != null) {
                PlayerData.MemoryStatus status = DataUtil.getMemoryStatus(player, memory);
                int[] values = status.getValues();
                for (int i = 0; i < values.length; i++) {
                    int value = values[i];
                    if (value > 0 || (i % 2 == 0 && values[i + 1] > 0)) {
                        if (i % 2 == 0 && values[i + 1] > 0) {
                            lines.add(new ArrayList<>());
                            lines.add(new ArrayList<>());
                            lines.add(new ArrayList<>());
                        }
                        MutableComponent translatable = Component.translatable("memory." + memory.name().toLowerCase() + "." + i + "." + Math.max(1, value));
                        List<FormattedCharSequence> split = Minecraft.getInstance().font.split(translatable, 300);
                        lines.add(split);
                    }
                }
            }

        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            graphics.fill(0, 0, 327, 160, 0x33000000);
            graphics.fill(0, 0, 5, 160, 0x06FFFFFF);
            graphics.fill(5, 0, 327, 5, 0x06FFFFFF);
            graphics.fill(322, 0, 327, 160, 0x13000000);
            graphics.fill(5, 155, 327, 160, 0x13000000);
            graphics.fill(0, 160, 327, getHeight(), 0x3A222222);
            poseStack.pushPose();
            poseStack.translate(getWidth() * 45 / 100F, 22, 0);
            poseStack.scale(2.5F, 2.5F, 1);
            graphics.drawCenteredString(Minecraft.getInstance().font, title, 0, 0, 0xFF697D57);
            poseStack.popPose();

            int i = 0;
            for (FormattedCharSequence formattedCharSequence : brief) {
                graphics.drawString(Minecraft.getInstance().font, formattedCharSequence, 15, 70 + i, 0xFF896D77);
                i += 15;
            }

            poseStack.pushPose();

            poseStack.translate(220, 75, 0);
            poseStack.pushPose();
            poseStack.scale(4, 4, 1);
            graphics.renderFakeItem(sieve, 0, 0);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(16, -16, 100);
            poseStack.scale(2, 2, 1);
            graphics.renderFakeItem(ingredient, 0, 0);
            poseStack.popPose();

            poseStack.popPose();
            if (relativeMouseX > 220 && relativeMouseX < 290 && relativeMouseY > 60 && relativeMouseY < 100) {
                graphics.renderTooltip(Minecraft.getInstance().font, ingredient, relativeMouseX, relativeMouseY);
            }

            poseStack.pushPose();
            poseStack.translate(20, 200, 0);
            i = 0;
            for (List<FormattedCharSequence> par : lines) {
                for (FormattedCharSequence line : par) {
                    graphics.drawString(Minecraft.getInstance().font, line, 0, i, 0xFF896D77);
                    i += 15;
                }
                i += 3;
            }
            poseStack.popPose();

        }
    }


}
