package com.valeriotor.beyondtheveil.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.capability.util.LetterDataProvider;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import com.valeriotor.beyondtheveil.client.gui.research.JournalGui;
import com.valeriotor.beyondtheveil.client.gui.research.journal.JournalCategory;
import com.valeriotor.beyondtheveil.container.LetterBoxContainer;
import com.valeriotor.beyondtheveil.letters.Letter;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

public class LetterBoxGui extends AbstractContainerScreen<LetterBoxContainer> {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/letters/letter_box.png");
    private static final ResourceLocation LETTER_ENTRY = new ResourceLocation(References.MODID, "textures/gui/letters/letter_entry.png");
    private static final ResourceLocation LETTER_OPEN = new ResourceLocation(References.MODID, "textures/gui/letters/letter_open.png");
    private static final ResourceLocation LETTER_CLOSED = new ResourceLocation(References.MODID, "textures/gui/letters/letter_closed.png");
    private static final ResourceLocation RED_LETTER_OPEN = new ResourceLocation(References.MODID, "textures/gui/letters/red_letter_open.png");
    private static final ResourceLocation RED_LETTER_CLOSED = new ResourceLocation(References.MODID, "textures/gui/letters/red_letter_closed.png");
    private static final int ENTRY_LIST_BASE_WIDTH = 345;
    private static final int ENTRY_LIST_BASE_HEIGHT = 357;
    private static final int BACKGROUND_BASE_WIDTH = 525;
    private static final int BACKGROUND_BASE_HEIGHT = 307;
    private static final int ENTRY_BASE_WIDTH = 340;
    private static final int ENTRY_BASE_HEIGHT = 59;
    private static final int ENTRY_LIST_BASE_LEFT_X = -BACKGROUND_BASE_WIDTH / 2 + 43;
    private static final int ENTRY_LIST_BASE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2 + 100;
    private int imageWidth;
    private int imageHeight;
    private float scaleFactor = 1;
    private ScrollableList<LetterEntry> receivedLetters;
    private ScrollableList<LetterEntry> sentLetters;
    private Letter shownLetter;
    private boolean showingReceived = true;


    public LetterBoxGui(LetterBoxContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        scaleFactor = 1;
        imageWidth = BACKGROUND_BASE_WIDTH;
        imageHeight = BACKGROUND_BASE_HEIGHT;
        //entryListWidth = 343;
        //entryListHeight = 357;

        if (imageHeight > height * 99 / 100) {
            scaleFactor = height * 99F / 100 / imageHeight;
        }
        if (imageWidth > width * 95 / 100) {
            scaleFactor = Math.min(width * 95F / 100 / imageWidth, scaleFactor);
        }

        Minecraft.getInstance().player.getCapability(LetterDataProvider.LETTER_DATA).ifPresent(c -> {
            receivedLetters = new ScrollableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, c.getReceivedInOrder().stream().map(LetterEntry::new).toList(), ENTRY_BASE_HEIGHT, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH);
            sentLetters = new ScrollableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, c.getSentInOrder().stream().map(LetterEntry::new).toList(), ENTRY_BASE_HEIGHT, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH);
        });
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.fill(0, 0, width, height, 0xCC111111);
        PoseStack pose = pGuiGraphics.pose();
        pose.pushPose();
        pose.translate(width / 2F, height / 2F, 0);
        pose.scale(scaleFactor, scaleFactor, 1);
        RenderSystem.enableBlend();
        pGuiGraphics.blit(BACKGROUND, -imageWidth / 2, -imageHeight / 2, imageWidth, imageHeight, 0, 0, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT);
        ScrollableList<LetterEntry> toRender = currentList();
        if (toRender != null) {
            pose.pushPose();
            pose.translate(ENTRY_LIST_BASE_LEFT_X, ENTRY_LIST_BASE_TOP_Y, 0);
            pGuiGraphics.fill(-4, -3, ENTRY_LIST_BASE_WIDTH + 2, ENTRY_LIST_BASE_HEIGHT + 2, 0x11111111);
            pGuiGraphics.fill(-0, -3, ENTRY_LIST_BASE_WIDTH - 2, 0, 0x44111111);
            pGuiGraphics.fill(-0, ENTRY_LIST_BASE_HEIGHT - 2, ENTRY_LIST_BASE_WIDTH - 2, ENTRY_LIST_BASE_HEIGHT + 2, 0x44111111);
            pGuiGraphics.fill(-4, -3, 0, ENTRY_LIST_BASE_HEIGHT + 2, 0x44111111);
            pGuiGraphics.fill(ENTRY_LIST_BASE_WIDTH - 2, -3, ENTRY_LIST_BASE_WIDTH + 2, ENTRY_LIST_BASE_HEIGHT + 2, 0x44111111);
            toRender.render(pose, pGuiGraphics, 0xFFFFFFFF, listMouseX(pMouseX), listMouseY(pMouseY), pPartialTick);
            pose.popPose();
        }
        pose.popPose();

    }

    @Nullable
    private ScrollableList<LetterEntry> currentList() {
        return showingReceived ? receivedLetters : sentLetters;
    }

    private double scaledMouseX(double mouseX) {
        return (mouseX - width / 2D) * scaleFactor;
    }

    private double scaledMouseY(double mouseY) {
        return (mouseY - height / 2D) * scaleFactor;
    }

    private int listMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - ENTRY_LIST_BASE_LEFT_X * scaleFactor) / scaleFactor);
    }

    private int listMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - ENTRY_LIST_BASE_TOP_Y * scaleFactor) / scaleFactor);
    }

    private static class LetterEntry extends Element {
        private final Letter letter;

        protected LetterEntry(Letter letter) {
            super(ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            this.letter = letter;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            graphics.blit(LETTER_ENTRY, 0, 0, getWidth(), getHeight(), 0, 0, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(0, 0, getWidth(), getHeight(), 0x44604533);
            }

            poseStack.pushPose();
            poseStack.translate(28, 28, 0);
            poseStack.scale(2.2F, 2.2F, 1);
            //poseStack.scale(scaleFactor, scaleFactor, 1);
            graphics.blit(LETTER_CLOSED, -8, -8, 16, 16, 0, 0, 60, 60, 60, 60);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(56 + 140, getHeight() / 5F - 4, 0);
            //poseStack.scale(2.5F, 2.5F, 1);
            //poseStack.scale(scaleFactor, scaleFactor, 1);
            //renderTitle(graphics);
            poseStack.popPose();
        }
    }


}
