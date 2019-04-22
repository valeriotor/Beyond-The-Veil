package com.valeriotor.BTV.gui;

import com.valeriotor.BTV.lib.BTVSounds;
import com.valeriotor.BTV.lib.References;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiAlienisDream extends GuiScreen{
	
	private static final ResourceLocation texture = new ResourceLocation(References.MODID + ":textures/gui/black.png");
	private int timePassed = 0;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.color(1, 1, 1, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(0, 0, 0, 0, width, height);
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		this.timePassed++;
		if(this.timePassed > 1000) {
			this.mc.displayGuiScreen((GuiScreen)null);
		}else if(this.timePassed == 20) {
			Minecraft.getMinecraft().player.playSound(BTVSounds.dreamAlienis, 1, 1);
		}
		super.updateScreen();
	}
}
