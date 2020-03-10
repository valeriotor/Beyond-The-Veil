package com.valeriotor.beyondtheveil.gui.research.recipes;

import java.util.ArrayList;
import java.util.List;

import com.valeriotor.beyondtheveil.gui.GuiHelper;
import com.valeriotor.beyondtheveil.gui.research.GuiResearchPage;
import com.valeriotor.beyondtheveil.gui.research.ResearchRecipe;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.sacrifice.SacrificeRecipe;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SacrificeResearchRecipe extends ResearchRecipe{
	
	
	private static final ResourceLocation CRAFTING_TEX = new ResourceLocation(References.MODID, "textures/gui/sacrifice_crafting.png");
	private SacrificeRecipe recipe;
	private List<ItemStack> inputs = new ArrayList<>();
	private List<ItemStack> outputs = new ArrayList<>();
	private int page = 0;
	
	public SacrificeResearchRecipe(String recipeKey, SacrificeRecipe recipe) {
		super(recipeKey);
		this.recipe = recipe;
		this.output = recipe.getKeyStack();
		for(int i = 0; i < recipe.getSize(); i++) {
			boolean multiple = recipe.isMultipleInSlot(i);
			ItemStack input = new ItemStack(recipe.getInputInSlot(i));
			ItemStack output = recipe.getOutputInSlot(i);
			if(multiple) {
				input.setCount(64);
				output.setCount(64);
			}
			inputs.add(input);
			outputs.add(output);
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
		GuiHelper.drawItemStack(gui, this.outputs.get(page), 50, iY);
		gui.getItemRender().renderItemOverlayIntoGUI(gui.mc.fontRenderer, this.inputs.get(page), iX, iY, null);
		gui.getItemRender().renderItemOverlayIntoGUI(gui.mc.fontRenderer, this.outputs.get(page), 50, iY, null);
		
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
