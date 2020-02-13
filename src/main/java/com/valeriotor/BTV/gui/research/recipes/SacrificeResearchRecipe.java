package com.valeriotor.BTV.gui.research.recipes;

import java.util.ArrayList;
import java.util.List;

import com.valeriotor.BTV.gui.GuiHelper;
import com.valeriotor.BTV.gui.research.GuiResearchPage;
import com.valeriotor.BTV.gui.research.ResearchRecipe;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.sacrifice.SacrificeRecipe;
import com.valeriotor.BTV.util.MathHelperBTV;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SacrificeResearchRecipe extends ResearchRecipe{
	
	
	private static final ResourceLocation CRAFTING_TEX = new ResourceLocation(References.MODID, "textures/gui/sacrifice_crafting.png");
	private SacrificeRecipe recipe;
	private List<ItemStack> inputs = new ArrayList<>();
	private int page = 0;
	
	public SacrificeResearchRecipe(String recipeKey, SacrificeRecipe recipe) {
		super(recipeKey);
		this.recipe = recipe;
		this.output = recipe.getKeyStack();
		for(int i = 0; i < recipe.getSize(); i++) {
			inputs.add(new ItemStack(recipe.getInputInSlot(i)));
		}
	}
	
	@Override
	public void render(GuiResearchPage gui, int mouseX, int mouseY) {
		super.render(gui, mouseX, mouseY);
		gui.mc.renderEngine.bindTexture(CRAFTING_TEX);
		gui.drawModalRectWithCustomSizedTexture(-114, -114, 0, 0, 228, 228, 228, 228);
		
		gui.mc.renderEngine.bindTexture(gui.ARROW);
		int sel = hoveringArrow(gui, mouseX, mouseY);
		if(this.page < this.recipe.getSize() - 1) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(120, 0, 0);
			if(sel == 1)
				GlStateManager.scale(1.5, 1.5, 1);
			gui.drawModalRectWithCustomSizedTexture(-16, -16, 0, 0, 32, 32, 32, 32);
			GlStateManager.popMatrix();
		}
		if(this.page > 0) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(-120, 0, 0);
			GlStateManager.rotate(180, 0, 0, 1);
			if(sel == 0)
				GlStateManager.scale(1.5, 1.5, 1);
			gui.drawModalRectWithCustomSizedTexture(-16, -16, 0, 0, 32, 32, 32, 32);
			GlStateManager.popMatrix();
		}
        RenderHelper.enableGUIStandardItemLighting();
        

		int iX = -64, iY = -34;
		GuiHelper.drawItemStack(gui, this.inputs.get(page), iX, iY);
		GuiHelper.drawItemStack(gui, this.recipe.getOutputInSlot(page), 50, iY);
		
		int hover = hoveringItem(gui, mouseX, mouseY);
		if(hover != -1) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(mouseX - gui.width / 2, mouseY - gui.height / 2, 0);
			if(hover == 1)
				gui.renderTooltip(this.recipe.getOutputInSlot(page), 0, 0);
			else
				gui.renderTooltip(this.inputs.get(page), 0, 0);
			
			GlStateManager.popMatrix();
		}
		
	}
	
	@Override
	public boolean mouseClicked(GuiResearchPage gui, int mouseX, int mouseY, int mouseButton) {
		int sel = hoveringArrow(gui, mouseX, mouseY);
		if(sel == 0)
			this.page = MathHelperBTV.clamp(0, recipe.getSize(), page - 1);
		else if(sel == 1)
			this.page = MathHelperBTV.clamp(0, recipe.getSize(), page + 1);
		if(sel == -1)
			return false;
		return true;		
	}
	
	private int hoveringItem(GuiResearchPage gui, int mouseX, int mouseY) {
		mouseX -= gui.width / 2;
		mouseY -= gui.height / 2;
		if(gui.mc.gameSettings.guiScale == 3 || gui.mc.gameSettings.guiScale == 0) {
			mouseX = mouseX * 4 / 3;
			mouseY = mouseY * 4 / 3;
		}
		int iX = -79, iY = -49;
		if(mouseY > iY && mouseY < iY + 40) {
			if(mouseX > iX && mouseX < iX + 40) {
				return 0;
			}
			if(mouseX > 35 && mouseX < 75) {
				return 1;
			}
		}
		return -1;
	}
	
}
