package com.valeriotor.beyondtheveil.gui;

import java.io.IOException;

import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageDagonDialogue;
import com.valeriotor.beyondtheveil.worship.DagonDialogues;

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
	private int SINGLE_THUMP_TIME = 50;
	private float scale = 3;
	private static final int TENSION_TIME = 20 * 30;
	
	public GuiDagon(DagonDialogues dd) {
		this.maxCount = dd.talkCount;
		this.translateKey = dd.toString().toLowerCase();
		if(dd == DagonDialogues.FINAL) {
			this.SINGLE_THUMP_TIME -= 10;
			this.count = 0;
			this.displayString = I18n.format("dagon." + translateKey + "." +  String.valueOf(count));
		}
	}
	
	public GuiDagon() {
		this(DagonDialogues.FINAL);
	}
	
	@Override
	public void initGui() {
		switch(mc.gameSettings.guiScale) {
		case 0: this.scale = 1.5F; break;
		case 1:
		case 2: this.scale = 3; break;
		case 3: this.scale = 2;
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int alpha = count == -1 ? (int)(((float)this.thumpTimer)/(SINGLE_THUMP_TIME*3) * 255) << 24 : 255 << 24;
		drawRect(0, 0, this.width, this.height, alpha);
		GlStateManager.pushMatrix();
		GlStateManager.scale(this.scale, this.scale, this.scale);
		int i = 0;
		for(String s : GuiHelper.splitStringsByWidth(displayString, (int)( this.width / scale - 20), mc.fontRenderer))
			drawCenteredString(mc.fontRenderer, s, (int)(this.width/2/scale), (int)(this.height/2/scale) + (i++) * 15, 0xFFFFFFFF);
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		this.tensionTimer = (this.tensionTimer + 1) % TENSION_TIME;
		if(this.tensionTimer == 0) 
			mc.player.playSound(BTVSounds.dagonTension, 0.45F, 1);
		
		this.thumpTimer = (this.thumpTimer + 1) % (SINGLE_THUMP_TIME * 3);
		if(this.thumpTimer % SINGLE_THUMP_TIME == 0) {
			if(this.thumpTimer == 0) {
				mc.player.playSound(BTVSounds.dagonThump, 10, 1);
				this.count++;
				if(this.count < this.maxCount) {
					this.displayString = I18n.format("dagon." + translateKey + "." +  String.valueOf(count));
					if(this.count % (this.maxCount / 3) == 0) {
						if(this.SINGLE_THUMP_TIME > 10)
							this.SINGLE_THUMP_TIME -= 10;
					}
				}
				else if(this.count == this.maxCount) {
					BTVPacketHandler.INSTANCE.sendToServer(new MessageDagonDialogue());
					this.displayString = "";
					this.SINGLE_THUMP_TIME += 20;
				}
				else {
					mc.displayGuiScreen((GuiScreen)null);
				}
			}
			else {
				mc.player.playSound(BTVSounds.dagonThump, 5, 1);
			}
		}
		
		super.updateScreen();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {}
	
}
