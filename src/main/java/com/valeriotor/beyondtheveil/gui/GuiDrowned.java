package com.valeriotor.beyondtheveil.gui;

import java.io.IOException;

import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.ritual.MessageRitualToServer;
import com.valeriotor.beyondtheveil.worship.DrowningRitual.Phase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;

public class GuiDrowned extends GuiScreen{
	
	private final byte phase;
	private int yourselfTimer = 100;
	private int knowTimer = -999;
	private String gnawingFull;
	private String gnawingShown = "";
	private boolean greatDreamer;
	private boolean ancientGods;
	
	public GuiDrowned(byte phase, boolean greatDreamer, boolean ancientGods) {
		this.phase = phase;
		Minecraft.getMinecraft().player.playSound(SoundEvents.ENTITY_PLAYER_DEATH, 1, 1);
		Minecraft.getMinecraft().player.performHurtAnimation();
		this.gnawingFull = I18n.format("gui.drowned.gnawing");
		if(this.phase == Phase.YOURSELF.ordinal()) Minecraft.getMinecraft().player.playSound(BTVSounds.worthless, 0.5F, 1);
		this.greatDreamer = greatDreamer;
		this.ancientGods = ancientGods;
	}
	
	@Override
	public void initGui() {
		if(this.phase == 1 || this.phase == 2) {
			this.buttonList.add(new GuiButton(0, this.width / 2 - 120, this.height / 4 + 72, 240, 20, I18n.format(!greatDreamer ? "gui.drowned.greatdreamer" : "gui.drowned.nogd")));
			this.buttonList.add(new GuiButton(1, this.width / 2 - 120, this.height / 4 + 96, 240, 20, I18n.format(!ancientGods ? "gui.drowned.ancientgods" : "gui.drowned.noac")));
			if(this.phase == 2) {
				this.buttonList.add(new GuiButton(2, this.width / 2 - 120, this.height / 4 + 120, 240, 20, I18n.format("gui.drowned.yourself")));
				this.buttonList.get(0).enabled = false;
				this.buttonList.get(1).enabled = false;
			}
			if(greatDreamer) buttonList.get(0).enabled = false;
			if(ancientGods) buttonList.get(1).enabled = false;
		} else {
			this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 72, I18n.format("gui.drowned.believe")));
			this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 96, I18n.format("gui.drowned.know")));
		}
		if(this.phase == Phase.YOURSELF.ordinal() && this.yourselfTimer >= 1)  
			for(GuiButton b : this.buttonList) b.visible = false;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(this.phase == Phase.YOURSELF.ordinal() && this.yourselfTimer >= 0) {
        	this.drawMeanStrings(partialTicks);
        } else {
            this.drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
	        GlStateManager.pushMatrix();
	        GlStateManager.scale(2.0F, 2.0F, 2.0F);
	        this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, I18n.format("gui.drowned.youdrowned"), this.width / 2 / 2, 30, 16777215);
	        GlStateManager.popMatrix();
        }
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void drawMeanStrings(float partialTicks) {
		if((yourselfTimer & 1) == 0) 
	        this.drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, I18n.format("gui.drowned.youare"), this.width / 2 / 2, this.height / 2 / 2 - 10, 16777215);
        if((this.yourselfTimer % 8 != 0 && this.yourselfTimer - 1 % 30 != 0) || this.yourselfTimer == 88) {
        	String s = getStringSuffix(yourselfTimer / 30);
	        this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, I18n.format("gui.drowned." + s), this.width / 2  / 2, this.height / 2 / 2 + 10, 16777215);
        }
        GlStateManager.popMatrix();
        for(int i = 0; i < (100 - yourselfTimer) / 2; i++) {
        	int y = this.height - 24 * (i + 1);
        	if(y > -16) {
        		String s = I18n.format("gui.drowned.youare").concat(I18n.format("gui.drowned.".concat(getStringSuffix(i%3))));
        		this.drawCenteredString(mc.fontRenderer, s, this.width / 4, y, 0xFFFFFFFF);
        		this.drawCenteredString(mc.fontRenderer, s, 3 * this.width / 4, y, 0xFFFFFFFF);
        	}
        }
        this.drawCenteredString(mc.fontRenderer, gnawingShown, this.width / 2, this.height / 4, 0xFFFFFFFF);
        this.drawCenteredString(mc.fontRenderer, gnawingShown, this.width / 2, 3 * this.height / 4, 0xFFFFFFFF);
	}
	
	private String getStringSuffix(int num) {
		switch(num) {
		case 0: return "insignificant";
    	case 1: return "worthless";
    	case 2:
    	default: return "nothing";
		}
	}
	
	@Override
	public void updateScreen() {
		if(this.phase == Phase.YOURSELF.ordinal() && this.yourselfTimer >= 0) {
			if(this.yourselfTimer == 0) {
				for(GuiButton b : this.buttonList) b.visible = true;
			}	
			this.yourselfTimer--;
			if(this.gnawingShown.length() < this.gnawingFull.length()) {
				this.gnawingShown = this.gnawingFull.substring(0, this.gnawingShown.length() + 1);
			} else {
				this.gnawingShown = "";
			}
		} else if(this.knowTimer > 0) {
			this.knowTimer--;
			if(this.knowTimer <= 0) {
				BTVPacketHandler.INSTANCE.sendToServer(new MessageRitualToServer((byte)4));
				this.mc.player.playSound(BTVSounds.breathe, 0.5F, 1);
				this.mc.displayGuiScreen((GuiScreen)null);
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id < 4) {
			BTVPacketHandler.INSTANCE.sendToServer(new MessageRitualToServer((byte)button.id));
			this.mc.displayGuiScreen((GuiScreen)null);
		} else if(button.id == 4){
			this.knowTimer = 85;
			this.mc.player.playSound(BTVSounds.tension, 0.5F, 1);
			for(GuiButton b : this.buttonList) b.enabled = false;
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {}
	
	
	
}
