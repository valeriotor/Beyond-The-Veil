package com.valeriotor.beyondtheveil.client.gui.dialogue;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.gui.elements.DialogueOptions;
import com.valeriotor.beyondtheveil.container.dialogue.DrownedDialogueMenu;
import com.valeriotor.beyondtheveil.dialogue.DialogueBranch;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.networking.SendDialogueOptionToServerPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class DrownedDialogueGui extends AbstractContainerScreen<DrownedDialogueMenu> {
    private int branch;
    private int indexInBranch;
    private DialogueOptions options;
    private int fadeInTicks = 30;
    private int fadeOutTicks = -1;
    private float scaleFactor = 1;
    private int displayedLines = 6;
    private static final float TEXT_TO_IMAGE_RATIO = 2.5F;
    private String line;

    public DrownedDialogueGui(DrownedDialogueMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        updateDialogue();
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
        scaleFactor = 1;

        final double MIN_STRING_PROPORTION = 70 / 1440D;
        final double MAX_STRING_PROPORTION = 100 / 1440D;
        if (MIN_STRING_PROPORTION > 15D / imageHeight) {
            scaleFactor = (float) ((MIN_STRING_PROPORTION) / (15D / imageHeight));
        } else if (MAX_STRING_PROPORTION < 15D / imageHeight) {
            scaleFactor = (float) ((MAX_STRING_PROPORTION) / (15D / imageHeight));
        }

        displayedLines = (int) (imageHeight / 3 / 15 / scaleFactor);

        updateOptions();
        line = menu.getLine();

    }

    private void updateDialogue() {
        branch = menu.getBranch();
        indexInBranch = menu.getIndexInBranch();
    }

    private void updateOptions() {
        int listWidth = (int) getListWidth();
        Optional<PlayerData> resolve = minecraft.player.getCapability(PlayerDataProvider.PLAYER_DATA).resolve();
        if (resolve.isPresent()) {
            PlayerData data = resolve.get();
            List<DialogueBranch.DialogueOption> dialogueOptions = menu.getDialogueOptions(data);
            BiConsumer<DialogueOptions, Integer> optionChosen = (o, i) -> {
                fadeOutTicks = 30;
                fadeInTicks = -1;
                Messages.sendToServer(new SendDialogueOptionToServerPacket(i));
            };
            this.options = DialogueOptions.makeOptions(dialogueOptions, listWidth * 8 / 10, minecraft.font, listWidth, 60, (int) (listWidth * 3 / 100), optionChosen);
        }
    }

    private float getListWidth() {
        return imageWidth / TEXT_TO_IMAGE_RATIO / scaleFactor;
    }

    @Override
    protected void containerTick() {
        if (fadeOutTicks >= 0) {
            if (fadeOutTicks == 0) {
                fadeInTicks = 30;
                updateDialogue();
                updateOptions();
                line = menu.getLine();
            }
            fadeOutTicks--;
        } else if (fadeInTicks >= 0) {
            fadeInTicks--;
        }
    }

    private boolean optionsAvailable() {
        return fadeInTicks < 0 && fadeOutTicks < 0;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBg(guiGraphics, pPartialTick, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics gg, float pPartialTick, int pMouseX, int pMouseY) {
        gg.fill(0, 0, width, height, 0xFF000000);
        PoseStack pose = gg.pose();
        if (line != null) {
            int alpha = (int) (fadeOutTicks >= 0 ? (fadeOutTicks - 0) * 255 / 30 : (fadeInTicks >= 0 ? (30 - fadeInTicks + pPartialTick) * 255 / 30 : 255));
            if (alpha <= 16) {
                alpha = 16;
            }
            int pColor = (alpha << 24) | 0xFFFFFF;
            List<FormattedCharSequence> split = minecraft.font.split(Component.literal(line), (int) (width * 2 / 3 / scaleFactor));
            pose.pushPose();
            pose.translate(width / 2D, height / 2D - 15 * split.size() / 2D, 0);
            pose.scale(scaleFactor, scaleFactor, 1);
            for (int i = 0; i < split.size(); i++) {
                gg.drawCenteredString(minecraft.font, split.get(i), 0, i * 15, pColor);
            }
            pose.popPose();
        }
        if (options != null && optionsAvailable()) {
            pose.pushPose();
            double pX = width / 2D - imageWidth / TEXT_TO_IMAGE_RATIO / 2;
            pose.translate(pX, height, 0);
            pose.scale(scaleFactor, scaleFactor, 1);
            pose.translate(0, - 15 * displayedLines, 0);
            options.render(pose, gg, 0xFFDD0000, (int) optionsRelativeMouseX(pMouseX), (int) optionsRelativeMouseY(pMouseY), pPartialTick);
            pose.popPose();
        }
    }

    private double optionsRelativeMouseX(double pMouseX) {
        return (pMouseX - width / 2 + imageWidth / TEXT_TO_IMAGE_RATIO / 2) / scaleFactor;
    }

    private double optionsRelativeMouseY(double pMouseY) {
        return (pMouseY - height ) / scaleFactor + 15 * displayedLines;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (options != null && optionsAvailable()) {
            return options.mouseClicked(optionsRelativeMouseX(pMouseX), optionsRelativeMouseY(pMouseY), pButton);
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (options != null && optionsAvailable()) {
            return options.mouseDragged(optionsRelativeMouseX(pMouseX), optionsRelativeMouseY(pMouseY), pButton, pDragX, pDragY);
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (options != null && optionsAvailable()) {
            return options.mouseScrolled(optionsRelativeMouseX(pMouseX), optionsRelativeMouseY(pMouseY), pDelta);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (options != null && optionsAvailable()) {
            return options.mouseReleased(optionsRelativeMouseX(pMouseX), optionsRelativeMouseY(pMouseY), pButton);
        }
        return false;
    }
}
