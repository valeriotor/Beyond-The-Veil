package com.valeriotor.beyondtheveil.client.gui.dialogue;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.gui.elements.DialogueOptions;
import com.valeriotor.beyondtheveil.container.dialogue.DoubleDialogueMenu;
import com.valeriotor.beyondtheveil.container.dialogue.EntityDialogueMenu;
import com.valeriotor.beyondtheveil.dialogue.DialogueBranch;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.networking.SendDialogueOptionToServerPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class ShoremanCultistDialogueGui extends AbstractContainerScreen<DoubleDialogueMenu> {


    private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/gui/dialogue/shoreman_cultist.png");
    private static final int TOTAL_FADEOUT = 50;
    private static final double TIME_BEFORE_FADEOUT = 20D;
    private static final float TEXT_WIDTH_RATIO = 0.9F;
    private float scaleFactor = 1;
    private DialogueOptions options;
    private int branch;
    private int indexInBranch;
    private EntityDialogueBox shoremanBox;
    private EntityDialogueBox cultistBox;
    private int exchangeIndex = 0;
    private int fadeoutTicks = 0;

    public ShoremanCultistDialogueGui(DoubleDialogueMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 512;
        this.imageHeight = 166;
        shoremanBox = new EntityDialogueBox(2000, 75, I18n.get("dialogue.shoreman_cultist.0"));
        cultistBox = new EntityDialogueBox(2000, 75, "");
    }

    @Override
    protected void init() {
        super.init();
        if (minecraft == null) {
            return;
        }

        // Gui should occupy between 70% and 80% of the screen width
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

        //String npcLine = menu.getNpcLine();
        shoremanBox = new EntityDialogueBox((int) (textWidth * 45 / 100), 75, shoremanBox.getLines());
        cultistBox = new EntityDialogueBox((int) (textWidth * 45 / 100), 75, cultistBox.getLines());
//
        Optional<PlayerData> resolve = minecraft.player.getCapability(PlayerDataProvider.PLAYER_DATA).resolve();
        List<DialogueBranch.DialogueOption> dialogueOptions = List.of(new DialogueBranch.DialogueOption(I18n.get("dialogue.shoreman_cultist.do_not.option"), DialogueBranch.OptionType.NORMAL),
                new DialogueBranch.DialogueOption(I18n.get("dialogue.shoreman_cultist.go_ahead.option"), DialogueBranch.OptionType.NORMAL));

        BiConsumer<DialogueOptions, Integer> optionChosen = (o, i) -> {
            Messages.sendToServer(new SendDialogueOptionToServerPacket(i));
            if (i == 0) {
                exchangeIndex = 11;
                cultistBox = new EntityDialogueBox((int) (imageWidth * TEXT_WIDTH_RATIO * 45 / 100), 75, I18n.get("dialogue.shoreman_cultist.do_not.0"));
            } else {
                exchangeIndex = 20;
                shoremanBox = new EntityDialogueBox((int) (imageWidth * TEXT_WIDTH_RATIO * 45 / 100), 75, I18n.get("dialogue.shoreman_cultist.go_ahead.0"));
            }
        };
        this.options = DialogueOptions.makeOptions(dialogueOptions, (int) (textWidth * 38 / 100), minecraft.font, (int) (textWidth * 45 / 100), 45, (int) (textWidth * 2 / 100), optionChosen);

    }

    @Override
    protected void containerTick() {
        super.containerTick();
        shoremanBox.tick();
        cultistBox.tick();
        fadeoutTicks++;
        if (exchangeIndex % 2 == 0 && shoremanBox.isFinished() && exchangeIndex < 7) {
            exchangeIndex++;
            fadeoutTicks = 0;
            cultistBox = new EntityDialogueBox((int) (imageWidth * TEXT_WIDTH_RATIO * 45 / 100), 75, I18n.get("dialogue.shoreman_cultist." + exchangeIndex));
        } else if (exchangeIndex % 2 == 1 && cultistBox.isFinished() && exchangeIndex < 7) {
            exchangeIndex++;
            fadeoutTicks = 0;
            shoremanBox = new EntityDialogueBox((int) (imageWidth * TEXT_WIDTH_RATIO * 45 / 100), 75, I18n.get("dialogue.shoreman_cultist." + exchangeIndex));
        } else if (exchangeIndex == 11 && cultistBox.isFinished()) {
            exchangeIndex++;
            fadeoutTicks = 0;
            shoremanBox = new EntityDialogueBox((int) (imageWidth * TEXT_WIDTH_RATIO * 45 / 100), 75, I18n.get("dialogue.shoreman_cultist.do_not.1"));
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
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(width / 2F, height, 0);
        pose.scale(scaleFactor, scaleFactor, 1);
        //guiGraphics.blit(TEXTURE, (int) (-imageWidth * scaleFactor / 2), (int) (-imageHeight * scaleFactor), 0, 0, this.imageWidth, this.imageHeight);
        RenderSystem.enableBlend();
        guiGraphics.blit(TEXTURE, (int) (-imageWidth / 2), (int) (-imageHeight), 512, 166, 0, 0, 512, 166, 512, 166);
        RenderSystem.disableBlend();

        if (shoremanBox != null && (exchangeIndex % 2 == 0 || fadeoutTicks < TOTAL_FADEOUT)) {
            pose.pushPose();
            pose.translate(-imageWidth * TEXT_WIDTH_RATIO / 2F, -135, 0);
            int alpha = 0xFF;
            if (exchangeIndex % 2 == 1) {
                double fadeLevel = Mth.clamp((TOTAL_FADEOUT + TIME_BEFORE_FADEOUT - fadeoutTicks) / TOTAL_FADEOUT, 0, 1);
                alpha = (int) Math.min(0x10 + 0xEF * fadeLevel, 255);
            }
            shoremanBox.render(pose, guiGraphics, (alpha << 24) | 0xFFFFFF, pMouseX, pMouseY, pPartialTick);
            pose.popPose();
        }

        if (cultistBox != null) {
            pose.pushPose();
            pose.translate(imageWidth * (1 - TEXT_WIDTH_RATIO) / 2, -135, 0);
            int alpha = 0xFF;
            if (exchangeIndex % 2 == 0) {
                double fadeLevel = Mth.clamp((TOTAL_FADEOUT + TIME_BEFORE_FADEOUT - fadeoutTicks) / TOTAL_FADEOUT, 0, 1);
                alpha = (int) Math.min(0x10 + 0xEF * fadeLevel, 255);
            }
            cultistBox.render(pose, guiGraphics, (alpha << 24) | 0xFFFFFF, pMouseX, pMouseY, pPartialTick);
            pose.popPose();
        }

        if (options != null && shouldShowOptions()) {
            pose.pushPose();
            pose.translate(imageWidth * (1 - TEXT_WIDTH_RATIO) / 2, -60, 0);
            options.render(pose, guiGraphics, 0xFFDD0000, (int) optionsMouseX(pMouseX), (int) ((pMouseY - height + 60 * scaleFactor) / scaleFactor), pPartialTick);
            pose.popPose();
        }

        pose.popPose();
    }

    private boolean shouldShowOptions() {
        return exchangeIndex == 7 && cultistBox.isFinished();
    }

    private double optionsMouseX(double mouseX) {
        return (mouseX - width / 2D - imageWidth * (1 - TEXT_WIDTH_RATIO) / 2 * scaleFactor) / scaleFactor;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (options != null && shouldShowOptions()) {
            return options.mouseClicked((int) optionsMouseX(pMouseX), (int) ((pMouseY - height + 60 * scaleFactor) / scaleFactor), pButton);
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (options != null) {
            return options.mouseDragged((int) optionsMouseX(pMouseX), (int) ((pMouseY - height + 60 * scaleFactor) / scaleFactor), pButton, pDragX, pDragY);
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (shoremanBox.mouseScrolled((int) ((pMouseX - width / 2 + imageWidth * TEXT_WIDTH_RATIO * scaleFactor / 2) / scaleFactor), (int) ((pMouseY - height + 135 * scaleFactor) / scaleFactor), pDelta)) {
            return true;
        }
        if (cultistBox.mouseScrolled((int) optionsMouseX(pMouseX), (int) ((pMouseY - height + 135 * scaleFactor) / scaleFactor), pDelta)) {
            return true;
        }
        if (options != null) {
            return options.mouseScrolled((int) optionsMouseX(pMouseX), (int) ((pMouseY - height + 60 * scaleFactor) / scaleFactor), pDelta);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (options != null) {
            return options.mouseReleased((int) optionsMouseX(pMouseX), (int) ((pMouseY - height + 60 * scaleFactor) / scaleFactor), pButton);
        }
        return false;
    }
}
