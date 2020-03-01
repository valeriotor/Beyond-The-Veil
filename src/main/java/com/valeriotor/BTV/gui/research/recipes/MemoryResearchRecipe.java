package com.valeriotor.BTV.gui.research.recipes;

import com.valeriotor.BTV.dreaming.Memory;
import com.valeriotor.BTV.gui.GuiHelper;
import com.valeriotor.BTV.gui.research.GuiResearchPage;
import com.valeriotor.BTV.gui.research.ResearchRecipe;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MemoryResearchRecipe extends ResearchRecipe {
	
	private ItemStack input;
	public MemoryResearchRecipe(String recipeKey) {
		super(recipeKey);
		String dataName = ItemHelper.checkStringTag(output, "memory", Memory.LEARNING.getDataName());
		input = Memory.getMemoryFromDataName(dataName).getItem();
	}

	private static final ResourceLocation MEMORY_TEX = new ResourceLocation(References.MODID, "textures/gui/recipe_memory.png");
	@Override
	public void render(GuiResearchPage gui, int mouseX, int mouseY) {
		super.render(gui, mouseX, mouseY);
		
		int x = mouseX - gui.width / 2, y = mouseY - gui.height / 2;
		int oX = 60, oY = (85-114), iX = (60-114), iY = (52-114), a = 16;
		if(gui.mc.gameSettings.guiScale == 3 || gui.mc.gameSettings.guiScale == 0) {
			oX = oX * 3 / 4;
			oY = oY * 3 / 4;
			iX = iX * 3 / 4;
			iY = iY * 3 / 4;
			a = 12;
		}
		gui.mc.renderEngine.bindTexture(MEMORY_TEX);
		gui.drawModalRectWithCustomSizedTexture(-114, -114, 0, 0, 228, 228, 228, 228);
        RenderHelper.enableGUIStandardItemLighting();
		GuiHelper.drawItemStack(gui, output, 60, -29);
		GuiHelper.drawItemStack(gui, input, -54, -62);
        RenderHelper.disableStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, 0);
		if(x > oX && x < oX + a && y > oY && y < oY + a) {
			gui.renderTooltip(output, 0, 0);
		} else if(x > iX && x < iX + a && y > iY && y < iY + a) {
			gui.renderTooltip(input, 0, 0);
		}
		GlStateManager.popMatrix();
	}
	
}