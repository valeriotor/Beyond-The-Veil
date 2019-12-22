package com.valeriotor.BTV.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.input.Mouse;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.capabilities.ResearchProvider;
import com.valeriotor.BTV.research.Research;
import com.valeriotor.BTV.research.ResearchStatus;
import com.valeriotor.BTV.util.MathHelperBTV;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

public class GuiNecronomicon extends GuiScreen{
	
	int topX = 0;
	int topY = 0;
	List<Research> clickables = new ArrayList<>();
	List<Research> visibles = new ArrayList<>();
	int counter = 0;
	
	public GuiNecronomicon() {
		HashMap<String, ResearchStatus> map = Minecraft.getMinecraft().player.getCapability(ResearchProvider.RESEARCH, null).getResearches();
		IPlayerData data = Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null);
		for(Entry<String, ResearchStatus> entry : map.entrySet()) {
			if(entry.getValue().isKnown(map, data)) {
				clickables.add(entry.getValue().res);
			}
			else if(entry.getValue().isVisible(map, data))
				visibles.add(entry.getValue().res);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawRect(0, 0, this.width, this.height, 0xFF000000); 
		for(Research r : clickables) this.drawResearch(r);
		for(Research r : visibles) this.drawResearch(r);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		this.counter++;
		super.updateScreen();
	}
	
	@Override
	public void handleMouseInput() throws IOException {
		if(Mouse.isButtonDown(0)) {
			topX = MathHelperBTV.clamp(-360, 960 - this.width/2, topX - Mouse.getDX());
			topY = MathHelperBTV.clamp(-360, 540 - this.height/2, topY + Mouse.getDY());
		}
		super.handleMouseInput();
	}
	
	
	private void drawResearch(Research res) {
		int resX = res.getX() * 50, resY = res.getY() * 50;
		if(resX > topX && resX < topX + this.width && resY > topY && resY < topY + this.height) {
			ItemStack[] icons = res.getIconStacks();
			this.drawItemStack(icons[counter % 20 % icons.length], resX - topX, resY - topY);
		}
	}
	
	
	// Shamelessly (CTRL+C-CTRL+V)ed from GuiContainer
	private void drawItemStack(ItemStack stack, int x, int y)
    {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        this.itemRender.zLevel = 200.0F;
        net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRenderer;
        this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        //this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y - (this.draggedStack.isEmpty() ? 0 : 8), altText);
        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
    }
}
