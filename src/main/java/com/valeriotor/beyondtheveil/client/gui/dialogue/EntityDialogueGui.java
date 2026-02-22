package com.valeriotor.beyondtheveil.client.gui.dialogue;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.gui.elements.DialogueOptions;
import com.valeriotor.beyondtheveil.container.dialogue.EntityDialogueMenu;
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
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public class EntityDialogueGui extends AbstractContainerScreen<EntityDialogueMenu> {

    private static final ResourceLocation SHOREMAN_TEXTURE = new ResourceLocation(References.MODID, "textures/gui/dialogue/shoreman.png");
    private static final ResourceLocation BLOOD_CULTIST_TEXTURE = new ResourceLocation(References.MODID, "textures/gui/dialogue/blood_cultist.png");
    private static final float TEXT_WIDTH_RATIO = 0.9F;
    private static final float TEXT_HEIGHT_RATIO = 0.85F;
    private float scaleFactor = 1;
    private DialogueOptions options;
    private int branch;
    private int indexInBranch;
    private double stringProgress;
    private int lastStringProgressSize, prevLastStringProgressSize;
    private int totalTicks = 0;
    private EntityDialogueBox dialogueBox;
    private final ResourceLocation texture;


    public EntityDialogueGui(EntityDialogueMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 512;
        this.imageHeight = 166;
        branch = pMenu.getBranch();
        indexInBranch = pMenu.getIndexInBranch();
        texture = switch (pMenu.getTemplate().getType()) {
            case BLOOD_CULTIST -> BLOOD_CULTIST_TEXTURE;
            default -> SHOREMAN_TEXTURE;
        };
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

        String npcLine = menu.getNpcLine();
        dialogueBox = new EntityDialogueBox((int) (textWidth * 100 / 100), 75, npcLine);

        Optional<PlayerData> resolve = minecraft.player.getCapability(PlayerDataProvider.PLAYER_DATA).resolve();
        if (resolve.isPresent()) {
            PlayerData data = resolve.get();
            List<DialogueBranch.DialogueOption> dialogueOptions = menu.getDialogueOptions(data);
            BiConsumer<DialogueOptions, Integer> optionChosen = (o, i) -> {
                Messages.sendToServer(new SendDialogueOptionToServerPacket(i));
            };
            this.options = DialogueOptions.makeOptions(dialogueOptions, (int) (textWidth * 8 / 10), minecraft.font, (int) (textWidth), 45, (int) (textWidth * 3 / 100), optionChosen);
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
            init();
        }
        if (dialogueBox != null) {
            dialogueBox.tick();
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
        RenderSystem.enableBlend();
        guiGraphics.blit(getTexture(), (int) (-imageWidth / 2), (int) (-imageHeight), 512, 166, 0, 0, 512, 166, 512, 166);
        RenderSystem.disableBlend();

        if (dialogueBox != null) {
            pose.pushPose();
            pose.translate(-imageWidth * TEXT_WIDTH_RATIO / 2F, -135, 0);
            dialogueBox.render(pose, guiGraphics, 0xFFFFFFFF, pMouseX, pMouseY, pPartialTick);
            pose.popPose();
        }

        if (options != null && shouldShowOptions()) {
            pose.pushPose();
            pose.translate(-imageWidth * TEXT_WIDTH_RATIO / 2F, -60, 0);
            options.render(pose, guiGraphics, 0xFFDD0000, (int) ((pMouseX - width / 2 + imageWidth * TEXT_WIDTH_RATIO * scaleFactor / 2) / scaleFactor), (int) ((pMouseY - height + 60 * scaleFactor) / scaleFactor), pPartialTick);
            pose.popPose();
        }

        pose.popPose();
    }

    @NotNull
    private ResourceLocation getTexture() {
        return texture;
    }

    private boolean shouldShowOptions() {
        DialogueTemplate template = menu.getTemplate();
        if (template.getType() == DialogueType.SHOREMAN_DRUNK && template.getID().equals("drunk1")) {
            return totalTicks > 60;
        }
        return dialogueBox != null && dialogueBox.isFinished();
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
        if (dialogueBox != null) {
            if (dialogueBox.mouseScrolled((int) ((pMouseX - width / 2 + imageWidth * TEXT_WIDTH_RATIO * scaleFactor / 2) / scaleFactor), (int) ((pMouseY - height + 135 * scaleFactor) / scaleFactor), pDelta)) {
                return true;
            }
        }
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
