package com.valeriotor.BTV.gui;

import java.io.IOException;

import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.ritual.MessageRitualToServer;
import com.valeriotor.BTV.worship.DrowningRitual.Phase;

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
	private String s;
	
	public GuiDrowned(byte phase) {
		this.phase = phase;
		Minecraft.getMinecraft().player.playSound(SoundEvents.ENTITY_PLAYER_DEATH, 1, 1);
		Minecraft.getMinecraft().player.performHurtAnimation();
	}
	
	@Override
	public void initGui() {
		if(this.phase == 1 || this.phase == 2) {
			this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72, I18n.format("gui.drowned.greatdreamer")));
			this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("gui.drowned.ancientgods")));
			if(this.phase == 2) {
				this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120, I18n.format("gui.drowned.yourself")));
				this.buttonList.get(0).enabled = false;
				this.buttonList.get(1).enabled = false;
			}
		} else {
			this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 72, I18n.format("gui.drowned.believe")));
			this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 96, I18n.format("gui.drowned.know")));
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, I18n.format("gui.drowned.youdrowned"), this.width / 2 / 2, 30, 16777215);
        GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		if(this.phase == Phase.YOURSELF.ordinal() && this.yourselfTimer >= 0) {
			this.yourselfTimer--;
		} else if(this.knowTimer > 0) {
			this.knowTimer--;
			if(this.knowTimer <= 0) {
				BTVPacketHandler.INSTANCE.sendToServer(new MessageRitualToServer((byte)4));
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
			this.knowTimer = 80;
			// Play sound
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
