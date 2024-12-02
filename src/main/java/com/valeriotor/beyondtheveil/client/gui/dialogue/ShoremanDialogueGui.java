package com.valeriotor.beyondtheveil.client.gui.dialogue;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.gui.elements.DialogueOptions;
import com.valeriotor.beyondtheveil.container.dialogue.ShoremanDialogueMenu;
import com.valeriotor.beyondtheveil.dialogue.DialogueBranch;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.networking.SendDialogueOptionToServerPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ShoremanDialogueGui extends AbstractContainerScreen<ShoremanDialogueMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/gui/dialogue/shoreman.png");
    private static final float TEXT_WIDTH_RATIO = 0.9F;
    private static final float TEXT_HEIGHT_RATIO = 0.85F;
    private float scaleFactor = 1;
    private DialogueOptions options;
    private int branch;
    private int indexInBranch;
    private double stringProgress;
    private int lastStringProgressSize, prevLastStringProgressSize;
    private double speed = 1.4;
    private int pauseTicks = 0;
    private int totalTicks = 0;
    private int currentLine = 0;
    private List<String> localizedNpcLines = new ArrayList<>();
    private List<String> displayedLines = new ArrayList<>();
    private Set<Character> storedFormattings = new HashSet<>();


    public ShoremanDialogueGui(ShoremanDialogueMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 512;
        this.imageHeight = 166;
        branch = pMenu.getBranch();
        indexInBranch = pMenu.getIndexInBranch();
        /*pMenu.addSlotListener(new ContainerListener() {
            @Override
            public void slotChanged(AbstractContainerMenu pContainerToSend, int pDataSlotIndex, ItemStack pStack) {

            }

            @Override
            public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue) {
                ShoremanDialogueGui.this.stringCounter = 0;
                ShoremanDialogueGui.this.init();
            }
        });*/
    }

    @Override
    protected void init() {
        super.init();
        if (minecraft == null) {
            return;
        }

        // Gui should occupy between 60% and 80% of the screen width
        if (imageWidth > width * 4F / 5) {
            scaleFactor = width * 4F / 5 / imageWidth;
        } else if (imageWidth < width * 3.5F / 5) {
            scaleFactor = width * 3.5F / 5 / imageWidth;
        }
        // Unless you use widescreen...
        if (imageHeight * scaleFactor > height / 2F) {
            scaleFactor *= (height / 2F) / (imageHeight * scaleFactor);
        }
        int textWidth = (int) (imageWidth * TEXT_WIDTH_RATIO);

        //this.npcLines.clear();
        String npcLine = menu.getNpcLine();
        while (npcLine.startsWith("|")) {
            npcLine = npcLine.substring(1);
            pauseTicks += 5;
        }
        if (npcLine.contains("I said")) {
            npcLine = "]]" + npcLine;
        }
        //npcLine = "In concealing — or eradicating — their existence, the church||| of my forefathers would have been rid of a people worshipping nothing less than the human mind's inability to grasp the truths of the world — ours and others. Heretical, by any means.";
        //npcLine = "Oh. A traveller.||| \nWelcome.";
        //this.npcLines.addAll(minecraft.font.split(FormattedText.of(npcLine), (int) (textWidth / scaleFactor)));

        Optional<PlayerData> resolve = minecraft.player.getCapability(PlayerDataProvider.PLAYER_DATA).resolve();
        if (resolve.isPresent()) {
            PlayerData data = resolve.get();
            List<DialogueBranch.DialogueOption> dialogueOptions = menu.getDialogueOptions(data);
            BiConsumer<DialogueOptions, Integer> optionChosen = (o, i) -> {
                Messages.sendToServer(new SendDialogueOptionToServerPacket(i));
            };
            this.options = DialogueOptions.makeOptions(dialogueOptions, (int) (textWidth * 8 / 10), minecraft.font, (int) (textWidth), 45, (int) (textWidth * 3 / 100), optionChosen);
        }

        if (displayedLines.isEmpty()) {
            displayedLines.add("");
            String[] split = npcLine.split("\n");
            for (String l : split) {
                String reducedNpcLine = l.replaceAll("[\\[\\]|]", "");
                int a = reducedNpcLine.length();
                minecraft.font.getSplitter().splitLines(l, textWidth, Style.EMPTY, true, (pStyle, pCurrentPos, pContentWidth) -> {
                    int currentIndex = 0, startIndex = 0, endIndex = -1;
                    for (int i = 0; i < reducedNpcLine.length(); i++) {
                        if (currentIndex == pCurrentPos) { // pos = 1, w = 5 abcdefg [[abc[
                            startIndex = i;
                            break;
                        }
                        char c = reducedNpcLine.charAt(i);
                        if (c != '[' && c != ']') {
                            currentIndex++;
                        }
                    }
                    for (int i = startIndex; i < reducedNpcLine.length(); i++) {
                        if (currentIndex - startIndex == pContentWidth - pCurrentPos) { // pos = 1, w = 5 abcdefg [[abc[
                            endIndex = i;
                            break;
                        }
                        char c = reducedNpcLine.charAt(i);
                        if (c != '[' && c != ']' && c != '|') {
                            currentIndex++;
                        }
                    }
                    if (endIndex == -1) {
                        endIndex = l.length();
                    }
                /*if (pCurrentPos == 0 && false) {
                    startIndex = 0;
                } else {
                    boolean flag = false;
                    for (int i = 0; i < reducedNpcLine.toCharArray().length; i++) {
                        if (currentIndex == pCurrentPos) { // pos = 1, w = 5 abcdefg [[abc[
                            startIndex = i;
                            flag = true;
                        }
                        char c = reducedNpcLine.charAt(i);
                        if (c != '[' && c != ']') {
                            currentIndex++;
                        }
                        if (flag && currentIndex - startIndex > pContentWidth - 1 - pCurrentPos) { // TODO -pCurrentPos?
                            endIndex = i;
                            break;
                        }
                    }
                    if (endIndex == 0) {
                        endIndex++;
                    }
                }*/
                    localizedNpcLines.add(l.substring(startIndex, endIndex));
                });

            }
        }

    }

    @Override
    protected void containerTick() {
        totalTicks++;
        if (menu.getBranch() != branch || menu.getIndexInBranch() != indexInBranch) {
            branch = menu.getBranch();
            indexInBranch = menu.getIndexInBranch();
            stringProgress = 0;
            prevLastStringProgressSize = 0;
            lastStringProgressSize = 0;
            currentLine = 0;
            speed = 1.4;
            localizedNpcLines.clear();
            displayedLines.clear();
            init();
        }
        if (pauseTicks > 0) {
            pauseTicks--;
        } else {
            stringProgress += speed;
            prevLastStringProgressSize = lastStringProgressSize;
            lastStringProgressSize = (int) Math.floor(stringProgress);
            tryAddCharacter();
        }
    }

    private void tryAddCharacter() {
        if (lastStringProgressSize > prevLastStringProgressSize && currentLine < localizedNpcLines.size()) {
            String line = localizedNpcLines.get(currentLine);
            for (int i = prevLastStringProgressSize + 1; i < lastStringProgressSize && i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == ',' || c == '.' || c == '?' || c == '!' || c == '|') {
                    lastStringProgressSize = prevLastStringProgressSize;
                    stringProgress = lastStringProgressSize;
                }
            }
            if (lastStringProgressSize > prevLastStringProgressSize) {
                boolean skipChar = false;
                while (stringProgress < line.length()) {
                    int index = (int) Math.floor(stringProgress);
                    char c = line.charAt(index);
                    char next = line.length() > index + 1 ? line.charAt(index + 1) : '\0';
                    if (c == '[' || c == ']' || c == '§' || skipChar || c == '|') {
                        if (skipChar) {
                            skipChar = false;
                            if (c == 'r') {
                                storedFormattings.clear();
                            } else {
                                storedFormattings.add(c);
                            }
                        }
                        if (c == '§') {
                            skipChar = true;
                        }
                        if (c == '[') {
                            speed -= 0.2;
                        } else if (c == ']') {
                            speed += 0.2;
                        } else if (c == '|') {
                            pauseTicks += 5;
                        }
                        stringProgress += 1;
                    } else {
                        if(c != next && !(line.length() == index + 1 && currentLine == localizedNpcLines.size() - 1)) {
                            if (c == ',') {
                                pauseTicks += 4;
                            } else if (c == '.' || c == '?' || c == '!') {
                                pauseTicks += 7;
                            }
                        }
                        break;
                    }
                }
                if (stringProgress >= line.length()) {
                    displayedLines.set(currentLine, (localizedNpcLines.get(currentLine).replaceAll("[\\[\\]|]", "")));
                    displayedLines.add("");
                    currentLine++;
                    stringProgress = 0;
                    prevLastStringProgressSize = 0;
                    lastStringProgressSize = 0;
                }
            }
        }
        if (currentLine < localizedNpcLines.size()) {
            String s = localizedNpcLines.get(currentLine);
            String sub = s.substring(0, Math.min(s.length(), (int) (stringProgress == 0 ? 0 : stringProgress + 1))).replaceAll("[\\[\\]|]", "");
            displayedLines.set(currentLine, sub);
            prevLastStringProgressSize = lastStringProgressSize;
            lastStringProgressSize = (int) Math.floor(stringProgress);
        }
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
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(width / 2F, height, 0);
        pose.scale(scaleFactor, scaleFactor, 1);
        //guiGraphics.blit(TEXTURE, (int) (-imageWidth * scaleFactor / 2), (int) (-imageHeight * scaleFactor), 0, 0, this.imageWidth, this.imageHeight);
        guiGraphics.blit(TEXTURE, (int) (-imageWidth / 2), (int) (-imageHeight), 512, 166, 0, 0, 512, 166, 512, 166);

        lastStringProgressSize = Math.max(lastStringProgressSize, (int) Math.floor(stringProgress + pauseTicks > 0 ? 0 : (pPartialTick * speed)));
        tryAddCharacter();

        int yOffset = 0;
        for (String npcLine : displayedLines) {
            guiGraphics.drawString(minecraft.font, npcLine, (int) (-imageWidth * TEXT_WIDTH_RATIO / 2), (int) (-imageHeight * TEXT_HEIGHT_RATIO) + yOffset, 0xFFFFFFFF);
            yOffset += 15;
        }

        if (options != null && shouldShowOptions()) {
            pose.pushPose();
            pose.translate(-imageWidth * TEXT_WIDTH_RATIO / 2F, -60, 0);
            options.render(pose, guiGraphics, 0xFFDD0000, (int) ((pMouseX - width / 2 + imageWidth * TEXT_WIDTH_RATIO * scaleFactor / 2) / scaleFactor), (int) ((pMouseY - height + 60 * scaleFactor) / scaleFactor));
            pose.popPose();
        }

        pose.popPose();
        guiGraphics.drawString(minecraft.font, "%d, %d".formatted(pMouseX, pMouseY), 0, 0, 0xFFFFFFFF);
    }

    private boolean shouldShowOptions() {
        DialogueTemplate template = menu.getTemplate();
        if (template.getType() == DialogueType.SHOREMAN_DRUNK && template.getID().equals("drunk1")) {
            return totalTicks > 60;
        }
        return currentLine >= localizedNpcLines.size();
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (options != null && shouldShowOptions()) {
            return options.mouseClicked((int) ((pMouseX - width / 2 + imageWidth * TEXT_WIDTH_RATIO * scaleFactor / 2) / scaleFactor), (int) ((pMouseY - height + 60 * scaleFactor) / scaleFactor), pButton);
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (options != null) {
            return options.mouseDragged((int) ((pMouseX - width / 2 + imageWidth * TEXT_WIDTH_RATIO * scaleFactor / 2) / scaleFactor), (int) ((pMouseY - height + 60 * scaleFactor) / scaleFactor), pButton, pDragX, pDragY);
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (options != null) {
            return options.mouseScrolled((int) ((pMouseX - width / 2 + imageWidth * TEXT_WIDTH_RATIO * scaleFactor / 2) / scaleFactor), (int) ((pMouseY - height + 60 * scaleFactor) / scaleFactor), pDelta);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (options != null) {
            return options.mouseReleased((int) ((pMouseX - width / 2 + imageWidth * TEXT_WIDTH_RATIO * scaleFactor / 2) / scaleFactor), (int) ((pMouseY - height + 60 * scaleFactor) / scaleFactor), pButton);
        }
        return false;
    }
}
