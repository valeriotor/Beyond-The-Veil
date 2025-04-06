package com.valeriotor.beyondtheveil.client.gui;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.container.DrownedContainer;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.networking.GenericToServerPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.util.timers.BaptismTimer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class DrownedGui extends AbstractContainerScreen<DrownedContainer> {

    private int delayTicker;
    private Component deathScore;
    private final List<Button> exitButtons = Lists.newArrayList();
    private final int phase;
    private int whoIsListeningTimer;
    private static final int TIME_PER_TALK = 90;
    private SimpleSoundInstance simpleSoundInstance;

    public DrownedGui(DrownedContainer pMenu, Inventory pPlayerInventory, Component title) {
        super(pMenu, pPlayerInventory, Component.translatable("gui.drowned.title"));
        phase = menu.getPhase();
        if (phase == BaptismTimer.Phase.TALK.ordinal()) {
            whoIsListeningTimer = TIME_PER_TALK * 3 + 1;
        }
    }

    @Override
    protected void init() {
        this.delayTicker = 0;
        this.exitButtons.clear();
        int numberOfOptions = BaptismTimer.Phase.values()[phase].getOptions();
        for (int i = 0; i < numberOfOptions; i++) {
            int number = i;
            this.exitButtons.add(this.addRenderableWidget(Button.builder(Component.translatable(String.format("gui.drowned.option_%d_%d", phase, i)), b -> {
                onClose();
                Messages.sendToServer(GenericToServerPacket.chooseBaptismOption(number));
            }).bounds(this.width / 2 - 100, this.height / 4 + 72 + 24 * i, 200, 20).build()));
        }
        this.setButtonsActive(false);
        this.deathScore = Component.translatable("deathScreen.score").append(": ").append(Component.literal(Integer.toString(this.minecraft.player.getScore())).withStyle(ChatFormatting.YELLOW));
    }


    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (whoIsListeningTimer == 0) {
            pGuiGraphics.fillGradient(0, 0, this.width, this.height, 1615855616, -1602211792);
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().scale(2.0F, 2.0F, 2.0F);
            pGuiGraphics.drawCenteredString(this.font, this.title, this.width / 2 / 2, 30, 16777215);
            pGuiGraphics.pose().popPose();
        /*if (this.causeOfDeath != null) {
            pGuiGraphics.drawCenteredString(this.font, this.causeOfDeath, this.width / 2, 85, 16777215);
        }*/

            pGuiGraphics.drawCenteredString(this.font, this.deathScore, this.width / 2, 100, 16777215);

            for (Renderable renderable : this.renderables) {
                renderable.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            }
        } else if (whoIsListeningTimer > 2 * TIME_PER_TALK) {

        } else if (whoIsListeningTimer > TIME_PER_TALK) {

        } else {

        }

    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {

    }

    @Override
    public void containerTick() {
        ++this.delayTicker;
        if (this.delayTicker == 20) {
            this.setButtonsActive(true);
        }
        if (whoIsListeningTimer > 0) {
            if (whoIsListeningTimer % TIME_PER_TALK == 0) {
                if (minecraft != null) {

                }
            }
            whoIsListeningTimer--;
            if (whoIsListeningTimer % TIME_PER_TALK == 0) {
                if (minecraft != null) {
                    if (simpleSoundInstance != null) {
                        minecraft.getSoundManager().stop(simpleSoundInstance);
                    }
                    if (whoIsListeningTimer > 0) {
                        simpleSoundInstance = SimpleSoundInstance.forUI(BTVSounds.FLETUM_WEEPING.get(), 1);
                        minecraft.getSoundManager().play(simpleSoundInstance);
                    }
                }
            }
        }
    }

    private void setButtonsActive(boolean pActive) {
        for (Button button : this.exitButtons) {
            button.active = pActive;
        }

    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
