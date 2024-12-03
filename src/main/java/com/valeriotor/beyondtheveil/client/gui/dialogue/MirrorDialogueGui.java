package com.valeriotor.beyondtheveil.client.gui.dialogue;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.gui.elements.DialogueOptions;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import com.valeriotor.beyondtheveil.client.gui.elements.TextUtil;
import com.valeriotor.beyondtheveil.container.dialogue.MirrorDialogueMenu;
import com.valeriotor.beyondtheveil.dialogue.DialogueBranch;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.networking.SendDialogueOptionToServerPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class MirrorDialogueGui extends AbstractContainerScreen<MirrorDialogueMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/gui/dialogue/black_mirror_transparent.png");
    private int branch;
    private int indexInBranch;
    private ScrollableList list;
    private final List<String> localizedLines = new ArrayList<>();
    private float scaleFactor = 1;
    private int yOffset = 0;
    private int totalTicks = 0;
    private int displayedLines = 6;
    private int waitTicks = -2;
    private static final int WAIT_TICKS_MAX = 35;
    private static final int WAIT_TICKS_MOVEMENT = 25;
    private int numberOfLinesToAppear = 0;
    private static final float TEXT_TO_IMAGE_RATIO = 2.5F;
    private DialogueOptions options;
    private boolean optionChosen = false;

    public MirrorDialogueGui(MirrorDialogueMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        //branch = pMenu.getBranch();
        //indexInBranch = pMenu.getIndexInBranch();
    }

    @Override
    protected void containerTick() {
        totalTicks++;
        if (menu.getBranch() != branch || menu.getIndexInBranch() != indexInBranch) {
            branch = menu.getBranch();
            indexInBranch = menu.getIndexInBranch();
            String line = "§e" + menu.getMirrorLine();
            localizedLines.add(line);
            numberOfLinesToAppear = new TextUtil().parseText(line, (int) computeListWidth(), minecraft.font).size();
            optionChosen = false;
            waitTicks = WAIT_TICKS_MAX;
        }
        if (waitTicks > -1) {
            waitTicks--;
            if (waitTicks == -1) {
                updateList();
                updateOptions();
            }
        }
    }

    @Override
    protected void init() {
        super.init();

        imageWidth = width;
        imageHeight = height;
        if (width > height * 16 / 9) {
            imageWidth = height * 16 / 9;
        } else if (height > width * 9 / 16) {
            imageHeight = width * 9 / 16;
        }

        final int MIN_STRING_WIDTH = 200;
        final int MAX_STRING_WIDTH = 450;
        if (MIN_STRING_WIDTH > imageWidth * 1F / TEXT_TO_IMAGE_RATIO) {
            scaleFactor = imageWidth * 1F / TEXT_TO_IMAGE_RATIO / MIN_STRING_WIDTH;
        } else if (MAX_STRING_WIDTH < imageWidth * 1F / TEXT_TO_IMAGE_RATIO) {
            scaleFactor = imageWidth * 1F / TEXT_TO_IMAGE_RATIO / MAX_STRING_WIDTH;
        }
        System.out.println(scaleFactor);
        int listWidth = (int) computeListWidth();
        updateList();

        updateOptions();
        //if (imageWidth > width * 4F / 5) {
        //    scaleFactor = width * 4F / 5 / imageWidth;
        //} else if (imageWidth < width * 3.5F / 5) {
        //    scaleFactor = width * 3.5F / 5 / imageWidth;
        //}
        //// Unless you use widescreen...
        //if (imageHeight * scaleFactor > height / 2F) {
        //    scaleFactor *= (height / 2F) / (imageHeight * scaleFactor);
        //}
        //int textWidth = (int) (imageWidth * TEXT_WIDTH_RATIO);

    }

    private void updateOptions() {
        int listWidth = (int) computeListWidth();
        Optional<PlayerData> resolve = minecraft.player.getCapability(PlayerDataProvider.PLAYER_DATA).resolve();
        if (resolve.isPresent()) {
            PlayerData data = resolve.get();
            List<DialogueBranch.DialogueOption> dialogueOptions = menu.getDialogueOptions(data);
            BiConsumer<DialogueOptions, Integer> optionChosen = (o, i) -> {
                if (!isOptionChosen() && !o.getOptionType(i).isEndsDialogue()) {
                    localizedLines.add(o.getLocalizedOption(i));
                    updateList();
                }
                markOptionChosen();
                Messages.sendToServer(new SendDialogueOptionToServerPacket(i));
            };
            this.options = DialogueOptions.makeOptions(dialogueOptions, listWidth * 8 / 10, minecraft.font, listWidth, 60, (int) (listWidth * 3 / 100), optionChosen);
        }
    }

    private void updateList() {
        boolean wasMaxRow = list == null || list.getCurrentFirstRow() == list.getMaxFirstRow();
        int listWidth = (int) computeListWidth();
        List<Element> lines = new ArrayList<>();//new TextUtil().parseText("of a dream I cannot remember, older yet than darkness, predating reality (or that which we call so). He would then live (exist) down there, deceitful (with or without intent), waiting (outside of time), dreaming.", listWidth, minecraft.font);
        for (String localizedLine : localizedLines) {
            lines.addAll(new TextUtil().parseText(localizedLine, listWidth, minecraft.font));
        }
        list = new ScrollableList(listWidth, 15 * displayedLines, lines, 15, (listWidth * 3 / 100));
        if (wasMaxRow) {
            list.setCurrentFirstRow(list.getMaxFirstRow());
        }

    }

    private float computeListWidth() {
        return imageWidth / TEXT_TO_IMAGE_RATIO / scaleFactor;
    }

    private void markOptionChosen() {
        optionChosen = true;
    }

    public boolean isOptionChosen() {
        return optionChosen;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBg(guiGraphics, pPartialTick, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        guiGraphics.blit(TEXTURE, width / 2 - imageWidth / 2, height / 2 - imageHeight / 2, imageWidth, imageHeight, 0, 0, 2560, 1440, 2560, 1440);
        guiGraphics.drawString(minecraft.font, "%d, %d".formatted(pMouseX, pMouseY), 0, 0, 0xFFFFFFFF);
        guiGraphics.drawString(minecraft.font, "%f, %d".formatted((pMouseX - width / 2 + imageWidth * 2 / 5 / 2) * scaleFactor, (pMouseY - height / 2 + imageHeight / 5)), 0, 15, 0xFFFFFFFF);
        PoseStack pose = guiGraphics.pose();
        if (list != null) {
            pose.pushPose();
            pose.translate(width / 2 - imageWidth / TEXT_TO_IMAGE_RATIO / 2, height / 2 + Math.max(0, 15 * (displayedLines - list.getNumberOfElements())), 0);
            if (waitTicks > -1 && waitTicks <= WAIT_TICKS_MOVEMENT) {
                pose.translate(0, -15 * (WAIT_TICKS_MOVEMENT - waitTicks - pPartialTick) * numberOfLinesToAppear / WAIT_TICKS_MOVEMENT, 0);
            }
            int linesToSkip = waitTicks <= -1 || waitTicks > WAIT_TICKS_MOVEMENT ? 0 : (int) ((WAIT_TICKS_MOVEMENT - waitTicks - pPartialTick) / ((float) WAIT_TICKS_MOVEMENT / numberOfLinesToAppear)) - 1 - Math.max(0, displayedLines - list.getNumberOfElements());
            pose.scale(scaleFactor, scaleFactor, 1);
            list.render(pose, guiGraphics, 0xFFFFFFFF, (int) relativeMouseX(pMouseX), (int) ((pMouseY - height / 2) * scaleFactor), Math.max(0, linesToSkip), waitTicks <= -1);
            pose.popPose();
        }
        if (options != null && optionsEnabled()) {
            pose.pushPose();
            pose.translate(width / 2 - imageWidth / TEXT_TO_IMAGE_RATIO / 2, height / 2 + imageHeight / 5, 0);
            pose.scale(scaleFactor, scaleFactor, 1);
            options.render(pose, guiGraphics, 0xFFDD0000, (int) relativeMouseX(pMouseX), (int) optionsRelativeMouseY(pMouseY));
            pose.popPose();
        }
    }

    private double relativeMouseX(double pMouseX) {
        return (pMouseX - width / 2 + imageWidth / TEXT_TO_IMAGE_RATIO / 2) * scaleFactor;
    }

    private double optionsRelativeMouseY(double pMouseY) {
        return (pMouseY - height / 2 - imageHeight / 5) * scaleFactor;
    }

    private boolean optionsEnabled() {
        return waitTicks == -1;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (options != null && optionsEnabled()) {
            return options.mouseClicked((int) relativeMouseX(pMouseX), (int) optionsRelativeMouseY(pMouseY), pButton);
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (options != null) {
            return options.mouseDragged((int) relativeMouseX(pMouseX), (int) optionsRelativeMouseY(pMouseY), pButton, pDragX, pDragY);
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (options != null) {
            return options.mouseScrolled((int) relativeMouseX(pMouseX), (int) optionsRelativeMouseY(pMouseY), pDelta);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (options != null) {
            return options.mouseReleased((int) relativeMouseX(pMouseX), (int) optionsRelativeMouseY(pMouseY), pButton);
        }
        return false;
    }

}
