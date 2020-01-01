package com.valeriotor.BTV.gui;

import com.valeriotor.BTV.lib.BTVSounds;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncStringDataToServer;
import com.valeriotor.BTV.util.SyncUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiAlienisDream extends GuiScreen{
	
	private int timePassed = 0;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawRect(0, 0, width, height, 0xFF000000);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		this.timePassed++;
		if(this.timePassed > 1000) {
			this.mc.displayGuiScreen((GuiScreen)null);
			SyncUtil.addStringDataOnClient(mc.player, false, "eldritchDream");
		}else if(this.timePassed == 20) {
			Minecraft.getMinecraft().player.playSound(BTVSounds.dreamAlienis, 1, 1);
		}
		super.updateScreen();
	}
	
	@Override
	public void onGuiClosed() {
		if(this.timePassed < 990) {
			this.mc.getSoundHandler().stopSounds();
		}
		super.onGuiClosed();
	}
}
