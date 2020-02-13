package com.valeriotor.BTV.gui.research.recipes;

import java.awt.Point;

import com.valeriotor.BTV.gui.GuiHelper;
import com.valeriotor.BTV.gui.research.GuiResearchPage;
import com.valeriotor.BTV.gui.research.ResearchRecipe;
import com.valeriotor.BTV.research.MultiblockSchematic;
import com.valeriotor.BTV.util.MathHelperBTV;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class MultiBlockResearchRecipe extends ResearchRecipe {
	
	private final String tooltip;
	private final ItemStack[][][] stacks;
	private Point topLeft, bottomRight;
	private int currentLayer = 0;
	
	public MultiBlockResearchRecipe(String recipeKey, MultiblockSchematic schem) {
		super(recipeKey);
		this.output = schem.getKeyStack();
		this.tooltip = schem.getLocalizedName();
		this.stacks = schem.getSchematic();
		int sideLength = stacks[0].length;
		topLeft = new Point(-16*sideLength/2, -16*sideLength/2);
		bottomRight = new Point(16*sideLength/2, 16*sideLength/2);
	}
	
	@Override
	public void renderTooltip(GuiResearchPage gui, int x, int y) {
		gui.drawHoveringText(tooltip, x, y);
	}
	
	@Override
	public void render(GuiResearchPage gui, int mouseX, int mouseY) {
		super.render(gui, mouseX, mouseY);
		
		gui.drawRect(-114, -114, 114, 114, 0xFF000000);
		GlStateManager.color(1, 1, 1);
		gui.drawCenteredString(gui.mc.fontRenderer, I18n.format("multiblock.layer") + " " + Integer.toString(currentLayer+1), 0, -120, 0xFFFFFFFF);
		gui.mc.renderEngine.bindTexture(gui.ARROW);
		int sel = hoveringArrow(gui, mouseX, mouseY);
		if(this.currentLayer < this.stacks.length - 1) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(120, 0, 0);
			if(sel == 1)
				GlStateManager.scale(1.5, 1.5, 1);
			gui.drawModalRectWithCustomSizedTexture(-16, -16, 0, 0, 32, 32, 32, 32);
			GlStateManager.popMatrix();
		}
		if(this.currentLayer > 0) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(-120, 0, 0);
			GlStateManager.rotate(180, 0, 0, 1);
			if(sel == 0)
				GlStateManager.scale(1.5, 1.5, 1);
			gui.drawModalRectWithCustomSizedTexture(-16, -16, 0, 0, 32, 32, 32, 32);
			GlStateManager.popMatrix();
		}
		
        RenderHelper.enableGUIStandardItemLighting();
		ItemStack[][] layer = stacks[currentLayer];
		for(int i = 0; i < layer.length; i++) {
			ItemStack[] line = layer[i];
			for(int j = 0; j < line.length; j++) {
				ItemStack stack = line[j];
				if(stack != null) {
					GuiHelper.drawItemStack(gui, stack, topLeft.x + i * 16, topLeft.y + j * 16);
				}
			}
		}
		mouseX -= gui.width/2;
		mouseY -= gui.height/2;
		if(gui.mc.gameSettings.guiScale == 3 || gui.mc.gameSettings.guiScale == 0) {
			mouseX = mouseX * 4 / 3;
			mouseY = mouseY * 4 / 3;
		}
		int a = (mouseX - topLeft.x) / 16;
		if(a >= 0 && a < layer.length) {
			int b = (mouseY - topLeft.y) / 16;
			if(b >= 0 && b < layer.length) {
				ItemStack stack = stacks[currentLayer][a][b];
				if(stack != null) {
					GlStateManager.pushMatrix();
					GlStateManager.translate(mouseX, mouseY, 0);
					gui.renderTooltip(stack, 0, 0);
					GlStateManager.popMatrix();
				}
			}
		}
	}
	
	@Override
	public boolean mouseClicked(GuiResearchPage gui, int mouseX, int mouseY, int mouseButton) {
		int sel = hoveringArrow(gui, mouseX, mouseY);
		if(sel == 0)
			this.currentLayer = MathHelperBTV.clamp(0, stacks.length, currentLayer - 1);
		else if(sel == 1)
			this.currentLayer = MathHelperBTV.clamp(0, stacks.length, currentLayer + 1);
		if(sel == -1)
			return false;
		return true;		
	}
	
}

