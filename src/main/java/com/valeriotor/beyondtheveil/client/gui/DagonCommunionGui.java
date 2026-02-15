package com.valeriotor.beyondtheveil.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.networking.GenericToServerPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public class DagonCommunionGui extends Screen {

    private static final int TEXT_FADE_TICKS = 40;
    private int counter = 0;
    private List<FormattedCharSequence> firstLines = new ArrayList<>();
    private List<FormattedCharSequence> secondLines = new ArrayList<>();
    private static final int SCALE_FACTOR = 4;

    public DagonCommunionGui() {
        super(Component.translatable("gui.dagon.title"));
    }

    @Override
    public void tick() {
        if (counter % (20 * 31) == 0) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(BTVSounds.DAGON_TENSION.get(), 1));
        }
        if ((counter < 600 && counter % 60 == 0) || (counter >= 600 && counter < 1200 && counter % 40 == 0) || (counter >= 1200 && counter % 20 == 0)) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(BTVSounds.DAGON_THUMP.get(), 1));
        }
        counter++;
        int pMaxWidth = width * 4 / 5 / SCALE_FACTOR;
        if (counter == 80) {
            firstLines.addAll(Minecraft.getInstance().font.split(Component.translatable("gui.dagon.0"), pMaxWidth));
        } else if (counter == 280) {
            firstLines.clear();
            firstLines.addAll(Minecraft.getInstance().font.split(Component.translatable("gui.dagon.1"), pMaxWidth));
            secondLines.addAll(Minecraft.getInstance().font.split(Component.translatable("gui.dagon.2"), pMaxWidth));
        } else if (counter == 660) {
            firstLines.clear();
            secondLines.clear();
            firstLines.addAll(Minecraft.getInstance().font.split(Component.translatable("gui.dagon.3"), pMaxWidth));
            secondLines.addAll(Minecraft.getInstance().font.split(Component.translatable("gui.dagon.4"), pMaxWidth));
        } else if (counter == 1040) {
            firstLines.clear();
            secondLines.clear();
            firstLines.addAll(Minecraft.getInstance().font.split(Component.translatable("gui.dagon.5"), pMaxWidth));
            secondLines.addAll(Minecraft.getInstance().font.split(Component.translatable("gui.dagon.6"), pMaxWidth));
        } else if (counter == 1420) {
            firstLines.clear();
            secondLines.clear();
            firstLines.addAll(Minecraft.getInstance().font.split(Component.translatable("gui.dagon.7"), pMaxWidth));
            secondLines.addAll(Minecraft.getInstance().font.split(Component.translatable("gui.dagon.8"), pMaxWidth));
        } else if (counter >= 1800) {
            onClose();
            Messages.sendToServer(GenericToServerPacket.finishDagonQuest());
        }
    }

    @Override
    public void render(GuiGraphics gg, int pMouseX, int pMouseY, float pPartialTick) {
        float time = counter + pPartialTick;
        int alpha = Math.min(255, (int) ((time) * 255 / 60));
        gg.fill(0, 0, width, height, alpha << 24);

        PoseStack pose = gg.pose();
        pose.pushPose();
        pose.translate(width / 2D, height / 2D, 0);
        pose.scale(4, 4, 1);

        int alpha1 = 0, alpha2 = 0;
        if (counter > 80 && counter < 280) {
            alpha1 = fadeInAndOut(80, 280, time);
        } else if (counter > 280 && counter < 660) {
            alpha1 = fadeInAndOut(280, 660, time);
            alpha2 = fadeInAndOut(420, 660, time);
        } else if (counter > 660 && counter < 1040) {
            alpha1 = fadeInAndOut(660, 1040, time);
            alpha2 = fadeInAndOut(800, 1040, time);
        } else if (counter > 1040 && counter < 1420) {
            alpha1 = fadeInAndOut(1040, 1420, time);
            alpha2 = fadeInAndOut(1180, 1420, time);
        } else if (counter > 1420 && counter < 1800) {
            alpha1 = fadeInAndOut(1420, 1800, time);
            alpha2 = fadeInAndOut(1560, 1800, time);
        }
        int size = firstLines.size() + secondLines.size();
        int start = -size * 15 / 2;
        int i = 0;
        for (FormattedCharSequence firstLine : firstLines) {
            gg.drawCenteredString(Minecraft.getInstance().font, firstLine, 0, start + 15 * i++, (Math.max(10, alpha1) << 24) | 0xFFFFFF);
        }
        if (alpha2 > 0) {
            for (FormattedCharSequence secondLine : secondLines) {
                gg.drawCenteredString(Minecraft.getInstance().font, secondLine, 0, start + 15 * i++, (Math.max(10, alpha2) << 24) | 0xFFFFFF);
            }
        }
        pose.popPose();
    }

    private int fadeInAndOut(int start, int end, float time) {
        int alpha;
        if (time < start + TEXT_FADE_TICKS) {
            alpha = (int) ((time - start) * 255 / TEXT_FADE_TICKS);
        } else if (time > end - TEXT_FADE_TICKS) {
            alpha = (int) ((end - time) * 255 / TEXT_FADE_TICKS);
        } else if (time < start || time > end) {
            alpha = 0;
        } else {
            alpha = 255;
        }
        return Math.min(255, alpha);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        return false;
    }

    private enum Lines {
        HEARKEN,
        BLOOD,
        HEART,
        SQUANDER,
        DIE,
        HUMANITY,
        I,
        DEPART,
        LIGHTHOUSE;
    }
}
