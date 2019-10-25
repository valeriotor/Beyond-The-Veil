package com.valeriotor.BTV.gui;

import com.valeriotor.BTV.lib.BTVSounds;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageDagonDialogue;
import com.valeriotor.BTV.worship.DagonDialogues;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiDagon extends GuiScreen{
	
	private int thumpTimer = 0;
	private int tensionTimer = -1;
	private int count = -1;
	private final int maxCount;
	private final String translateKey;
	private String displayString = "";
	private static final int SINGLE_THUMP_TIME = 50;
	private static final int TENSION_TIME = 20 * 32;
	
	public GuiDagon(DagonDialogues dd) {
		this.maxCount = dd.talkCount;
		this.translateKey = dd.toString().toLowerCase();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int alpha = count == -1 ? (int)(((float)this.thumpTimer)/(SINGLE_THUMP_TIME*3) * 255) << 24 : 255 << 24;
		drawRect(0, 0, this.width, this.height, alpha);
		GlStateManager.pushMatrix();
		GlStateManager.scale(3, 3, 3);
		int i = 0;
		for(String s : GuiHelper.splitStringsByWidth(displayString, this.width / 3 - 20, mc.fontRenderer))
			drawCenteredString(mc.fontRenderer, s, this.width/6, this.height/6 + (i++) * 15, 0xFFFFFFFF);
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		this.tensionTimer = (this.tensionTimer + 1) % TENSION_TIME;
		if(this.tensionTimer == 0) 
			mc.player.playSound(BTVSounds.dagonTension, 0.75F, 1);
		
		this.thumpTimer = (this.thumpTimer + 1) % (SINGLE_THUMP_TIME * 3);
		if(this.thumpTimer % SINGLE_THUMP_TIME == 0) {
			if(this.thumpTimer == 0) {
				mc.player.playSound(BTVSounds.dagonThump, 5, 1);
				this.count++;
				if(this.count < this.maxCount) this.displayString = I18n.format("dagon." + translateKey + "." +  String.valueOf(count));
				else if(this.count == this.maxCount) this.displayString = "";
				else {
					BTVPacketHandler.INSTANCE.sendToServer(new MessageDagonDialogue());
					mc.displayGuiScreen((GuiScreen)null);
				}
			}
			else {
				mc.player.playSound(BTVSounds.dagonThump, 1, 1);
			}
		}
		
		super.updateScreen();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
}