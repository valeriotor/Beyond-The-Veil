package com.valeriotor.beyondtheveil.gui;

import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageArenaFight;
import com.valeriotor.beyondtheveil.worship.DOSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;

public class GuiArena extends GuiScreen {
    private final boolean bruteUnlocked;
    private final boolean myrmidonUnlocked;
    private final boolean scionUnlocked;
    private final BlockPos arenaStarterPos;

    public GuiArena(BlockPos arenaStarterPos) {
        this.arenaStarterPos = arenaStarterPos;
        bruteUnlocked = true;
        myrmidonUnlocked = DOSkill.HEALTH.isUnlockable(Minecraft.getMinecraft().player);
        scionUnlocked = false;
    }


    @Override
    public void initGui() {
        int id = 0;
        String bruteKey = bruteUnlocked ? I18n.format("entity.deep_one_brute.name") : "???";
        String myrmidonKey = myrmidonUnlocked ? I18n.format("entity.deep_one_myrmidon.name") : "???";
        String scionKey = scionUnlocked ? I18n.format("entity.scion_of_dagon.name") : "???";
        GuiButton bruteButton = new GuiButton(id++, width/3, height/3, width/3, 20, bruteKey);
        GuiButton myrmidonButton = new GuiButton(id++, width/3, height/2, width/3, 20, myrmidonKey);
        GuiButton scionButton = new GuiButton(id++, width/3, 2*height/3, width/3, 20, scionKey);
        bruteButton.enabled = bruteUnlocked;
        myrmidonButton.enabled = myrmidonUnlocked;
        scionButton.enabled = scionUnlocked;
        buttonList.add(bruteButton);
        buttonList.add(myrmidonButton);
//        buttonList.add(scionButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        BTVPacketHandler.INSTANCE.sendToServer(new MessageArenaFight(button.id, arenaStarterPos));
        mc.displayGuiScreen(null);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
