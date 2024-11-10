package com.valeriotor.beyondtheveil.client.gui.dialogue;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.gui.elements.DialogueOptions;
import com.valeriotor.beyondtheveil.container.dialogue.ShoremanDialogueMenu;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.networking.SendDialogueOptionToServerPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ShoremanDialogueGui extends AbstractContainerScreen<ShoremanDialogueMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/gui/dialogue/shoreman.png");
    private int stringCounter;
    private float scaleFactor = 1;
    private List<FormattedCharSequence> npcLines = new ArrayList<>();
    private DialogueOptions options;
    private int branch;
    private int indexInBranch;
    private static final float TEXT_WIDTH_RATIO = 0.9F;
    private static final float TEXT_HEIGHT_RATIO = 0.85F;


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
        int textWidth = (int) (imageWidth * scaleFactor * TEXT_WIDTH_RATIO);

        this.npcLines.clear();
        String npcLine = menu.getNpcLine();
        this.npcLines.addAll(minecraft.font.split(FormattedText.of(npcLine), (int) (textWidth / scaleFactor)));

        Optional<PlayerData> resolve = minecraft.player.getCapability(PlayerDataProvider.PLAYER_DATA).resolve();
        if (resolve.isPresent()) {
            PlayerData data = resolve.get();
            List<String> dialogueOptions = menu.getDialogueOptions(data);
            Consumer<Integer> optionChosen = i -> {
                Messages.sendToServer(new SendDialogueOptionToServerPacket(i));
            };
            this.options = DialogueOptions.makeOptions(dialogueOptions, (int) (textWidth / scaleFactor * 8 / 10), minecraft.font, (int) (textWidth / scaleFactor), 45, (int) (textWidth / scaleFactor * 3 / 100), optionChosen);
        }

    }

    @Override
    protected void containerTick() {
        if (menu.getBranch() != branch || menu.getIndexInBranch() != indexInBranch) {
            branch = menu.getBranch();
            indexInBranch = menu.getIndexInBranch();
            stringCounter = 0; // TODO
            init();
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

        int yOffset = 0;
        for (FormattedCharSequence npcLine : npcLines) {
            guiGraphics.drawString(minecraft.font, npcLine, (int) (-imageWidth * TEXT_WIDTH_RATIO / 2), (int) (-imageHeight * TEXT_HEIGHT_RATIO) + yOffset, 0xFFFFFFFF);
            yOffset += 15;
        }

        if (options != null) {
            pose.pushPose();
            pose.translate(-imageWidth * TEXT_WIDTH_RATIO / 2F, -60, 0);
            options.render(pose, guiGraphics, 0xFFDD0000, (int) ((pMouseX - width / 2 + imageWidth * TEXT_WIDTH_RATIO * scaleFactor / 2) / scaleFactor), (int) ((pMouseY - height + 60 * scaleFactor) / scaleFactor));
            pose.popPose();
        }

        pose.popPose();
        guiGraphics.drawString(minecraft.font, "%d, %d".formatted(pMouseX, pMouseY), 0, 0, 0xFFFFFFFF);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (options != null) {
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
