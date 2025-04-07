package com.valeriotor.beyondtheveil.client.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.gui.dialogue.MirrorDialogueGui;
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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class DrownedGui extends AbstractContainerScreen<DrownedContainer> {

    private int delayTicker;
    private Component deathScore;
    private final List<Button> exitButtons = Lists.newArrayList();
    private final int phase;
    private int whoIsListeningTimer;
    private static final int TIME_PER_TALK = 110;
    private SimpleSoundInstance simpleSoundInstance;
    private MutableComponent insignificant;
    private MutableComponent worthless;

    public DrownedGui(DrownedContainer pMenu, Inventory pPlayerInventory, Component title) {
        super(pMenu, pPlayerInventory, Component.translatable("gui.drowned.title"));
        phase = menu.getPhase();
        if (phase == BaptismTimer.Phase.TALK.ordinal()) {
            whoIsListeningTimer = TIME_PER_TALK * 3 + 1;
        }
    }

    @Override
    protected void init() {
        insignificant = Component.translatable("gui.drowned.insignificant");
        worthless = Component.translatable("gui.drowned.insignificant");
        this.delayTicker = 0;
        this.exitButtons.clear();
        BaptismTimer.Phase value = BaptismTimer.Phase.values()[phase];
        int numberOfOptions = value.getOptions();
        for (int i = 0; i < numberOfOptions; i++) {
            int number = i;
            Button button = Button.builder(Component.translatable(String.format("gui.drowned.option_%d_%d", phase, i)), b -> {
                onClose();
                Messages.sendToServer(GenericToServerPacket.chooseBaptismOption(number));
            }).bounds(this.width / 2 - 150, this.height / 4 + 72 + 24 * i, 300, 20).build();
            this.exitButtons.add(this.addRenderableWidget(button));
            if (value.getIgnored().contains(Integer.valueOf(i))) {
                button.active = false;
            }
        }
        this.setButtonsActive(false);
        this.deathScore = Component.translatable("deathScreen.score").append(": ").append(Component.literal(Integer.toString(this.minecraft.player.getScore())).withStyle(ChatFormatting.YELLOW));
    }


    @Override
    public void render(GuiGraphics gg, int pMouseX, int pMouseY, float pPartialTick) {
        final int yOffset = 10;
        ;
        float FACTOR = 2 * Math.min(1, width * 1 / 3F / minecraft.font.width(Component.translatable("gui.drowned.gnawing").getString()));
        PoseStack pose = gg.pose();
        if (whoIsListeningTimer == 0) {
            gg.fillGradient(0, 0, this.width, this.height, 1615855616, -1602211792);
            pose.pushPose();
            pose.scale(2.0F, 2.0F, 2.0F);
            gg.drawCenteredString(this.font, this.title, this.width / 2 / 2, 30, 16777215);
            pose.popPose();
        /*if (this.causeOfDeath != null) {
            pGuiGraphics.drawCenteredString(this.font, this.causeOfDeath, this.width / 2, 85, 16777215);
        }*/

            gg.drawCenteredString(this.font, this.deathScore, this.width / 2, 100, 16777215);

            for (Renderable renderable : this.renderables) {
                renderable.render(gg, pMouseX, pMouseY, pPartialTick);
            }
        } else if (whoIsListeningTimer > 2 * TIME_PER_TALK) {
            gg.fillGradient(0, 0, this.width, this.height, 1615855616, -1602211792);
            int ticks = 3 * TIME_PER_TALK - whoIsListeningTimer;
            for (int i = 0; i < height * 2; i += 30) {
                int pY = ((int) (i - (ticks + pPartialTick) * 2));
                if(pY > 0 && pY < height + 30) {
                    gg.drawCenteredString(minecraft.font, i % 60 == 0 ? insignificant : worthless, width / 8, pY, 0xFFFFFFFF);
                    gg.drawCenteredString(minecraft.font, i % 60 == 30 ? worthless : insignificant, 7 * width / 8, pY, 0xFFFFFFFF);
                }
            }
            String string = Component.translatable("gui.drowned.gnawing").getString();
            String string2 = Component.translatable("gui.drowned.gnawing2").getString();
            pose.pushPose();
            pose.translate(width / 2, height / 2, 0);
            pose.scale(FACTOR, FACTOR, 1);
            gg.drawCenteredString(minecraft.font, string.substring(0, Mth.clamp(ticks, 1, string.length())), 0, 0 - yOffset, 0xFFFFFFFF);
            if (ticks > string.length() + 15) {
                gg.drawCenteredString(minecraft.font, string2.substring(0, Mth.clamp(ticks - (string.length() + 15), 1, string2.length())), 0, 25 - yOffset, 0xFFFFFFFF);
            }
            pose.popPose();
        } else if (whoIsListeningTimer > TIME_PER_TALK) {
            gg.fill(0, 0, width, height, 0xFF000000);
            int ticks = 2 * TIME_PER_TALK - whoIsListeningTimer;
            String string = Component.translatable("gui.drowned.ocean").getString();
            String string2 = Component.translatable("gui.drowned.ocean2").getString();
            pose.pushPose();
            pose.translate(width / 2, height / 2, 0);
            pose.scale(FACTOR, FACTOR, 1);
            gg.drawCenteredString(minecraft.font, string.substring(0, Mth.clamp(ticks, 1, string.length())), 0, 0 - yOffset, 0xFFFFFFFF);
            if (ticks > string.length() + 15) {
                gg.drawCenteredString(minecraft.font, string2.substring(0, Mth.clamp(ticks - (string.length() + 15), 1, string2.length())), 0, 25 - yOffset, 0xFFFFFFFF);
            }
            pose.popPose();
        } else {
            gg.blit(MirrorDialogueGui.TEXTURE, 0, 0, width, height, 0, 0, 2560, 1440, 2560, 1440);
            int ticks = TIME_PER_TALK - whoIsListeningTimer;
            String string = Component.translatable("gui.drowned.you").getString();
            String string2 = Component.translatable("gui.drowned.you2").getString();
            String string3 = Component.translatable("gui.drowned.you3").getString();
            pose.pushPose();
            pose.translate(width / 2, height / 2, 0);
            pose.scale(FACTOR, FACTOR, 1);
            gg.drawCenteredString(minecraft.font, string.substring(0, Mth.clamp(ticks, 1, string.length())), 0, 0 - yOffset - 25, 0xFF3F3F15);
            if (ticks > string.length() + 15) {
                gg.drawCenteredString(minecraft.font, string2.substring(0, Mth.clamp(ticks - (string.length() + 15), 1, string2.length())), 0, 25 - yOffset - 25, 0xFF3F3F15);
            }
            if (ticks > string.length() + 15 + string2.length()) {
                gg.drawCenteredString(minecraft.font, string3.substring(0, Mth.clamp(ticks - (string.length() + 15 + string2.length()), 1, string3.length())), 0, 50 - yOffset - 25, 0xFF3F3F15);
            }
            pose.popPose();

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
                        SoundEvent sound = whoIsListeningTimer == 3 * TIME_PER_TALK ? BTVSounds.BAPTISM_GS.get() : (whoIsListeningTimer == 2 * TIME_PER_TALK ? BTVSounds.WATER_DREAM_SHORT.get() : BTVSounds.TENSION.get());
                        simpleSoundInstance = SimpleSoundInstance.forUI(sound, 1);
                        minecraft.getSoundManager().play(simpleSoundInstance);
                    }
                }
            }
        }
    }

    private void setButtonsActive(boolean pActive) {
        List<Button> buttons = this.exitButtons;
        for (int i = 0; i < buttons.size(); i++) {
            BaptismTimer.Phase value = BaptismTimer.Phase.values()[phase];
            Button button = buttons.get(i);
            if (!value.getIgnored().contains(i)) {
                button.active = pActive;
            } else {
                button.active = false;
            }
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
