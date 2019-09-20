package com.valeriotor.BTV.gui.container;

import com.valeriotor.BTV.items.container.ContainerDreamBottle;
import com.valeriotor.BTV.lib.References;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiDreamBottle extends GuiContainer{

	private static final ResourceLocation texture = new ResourceLocation(References.MODID + ":textures/gui/dream_bottle.png");
	private static final ResourceLocation textureCharges = new ResourceLocation(References.MODID + ":textures/gui/dream_bottle_charges.png");
	private int fifthbuckets = 0;
	
	public GuiDreamBottle(ContainerDreamBottle inventorySlotsIn, int charges) {
		super(inventorySlotsIn);
		this.xSize = 175;
		this.ySize = 196;
		this.fifthbuckets = charges/200;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		int x = (this.width - this.xSize)/2;
		int y = (this.height)/2 - 115;
		drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(textureCharges);
		drawModalRectWithCustomSizedTexture(this.width/2 + 60, this.height/2 - 60, 0, 0, 32, 44, 64, 64);
		drawModalRectWithCustomSizedTexture(this.width/2 + 62, this.height/2 - 58 + 2 * (20 - fifthbuckets), 34, 2 + 2 * (20 - fifthbuckets), 28, 2 * fifthbuckets, 64, 64);
		if(mouseX > width/2 + 60 && mouseX < width/2 + 92 && mouseY > height/2 - 60 && mouseY < height/2 - 16) {
			drawHoveringText(I18n.format("gui.dream_bottle.charges", this.fifthbuckets/5), mouseX - 64, mouseY);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);

		
	}

}
