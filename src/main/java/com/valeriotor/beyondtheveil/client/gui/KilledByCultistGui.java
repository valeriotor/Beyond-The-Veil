package com.valeriotor.beyondtheveil.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import javax.annotation.Nullable;
import java.util.List;

public class KilledByCultistGui extends Screen {

    private int delayTicker;
    private Component deathScore;
    private final List<Button> exitButtons = Lists.newArrayList();


    protected KilledByCultistGui() {
        super(Component.translatable("deathScreen.title"));
    }

    @Override
    protected void init() {
        this.delayTicker = 0;
        this.exitButtons.clear();
        Component component = Component.translatable("gui.killed_by_cultist.respawn");
        this.exitButtons.add(this.addRenderableWidget(Button.builder(component, b -> {
            onClose();
            b.active = false;
        }).bounds(this.width / 2 - 100, this.height / 4 + 72, 200, 20).build()));
        this.setButtonsActive(false);
        this.deathScore = Component.translatable("deathScreen.score").append(": ").append(Component.literal(Integer.toString(this.minecraft.player.getScore())).withStyle(ChatFormatting.YELLOW));
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.fillGradient(0, 0, this.width, this.height, 1615855616, -1602211792);
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(2.0F, 2.0F, 2.0F);
        pGuiGraphics.drawCenteredString(this.font, this.title, this.width / 2 / 2, 30, 16777215);
        pGuiGraphics.pose().popPose();
        /*if (this.causeOfDeath != null) {
            pGuiGraphics.drawCenteredString(this.font, this.causeOfDeath, this.width / 2, 85, 16777215);
        }*/

        pGuiGraphics.drawCenteredString(this.font, this.deathScore, this.width / 2, 100, 16777215);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

    }

    public void tick() {
        super.tick();
        ++this.delayTicker;
        if (this.delayTicker == 20) {
            this.setButtonsActive(true);
        }

    }

    private void setButtonsActive(boolean pActive) {
        for(Button button : this.exitButtons) {
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
