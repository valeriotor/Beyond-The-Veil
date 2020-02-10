package com.valeriotor.BTV.gui.container;

import com.valeriotor.BTV.lib.References;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiGearBench extends GuiContainer{

	private static final ResourceLocation texture = new ResourceLocation(References.MODID + ":textures/gui/gear_bench.png");
		
	public GuiGearBench(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		this.xSize = 176;
		this.ySize = 191;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.renderEngine.bindTexture(texture);
		int x = (this.width - this.xSize)/2;
		int y = (this.height)/2 - 115;
		drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);	
	}

}
