package com.valeriotor.beyondtheveil.gui.research.recipes;

import com.valeriotor.beyondtheveil.crafting.GearBenchRecipe;
import com.valeriotor.beyondtheveil.gui.GuiHelper;
import com.valeriotor.beyondtheveil.gui.research.GuiResearchPage;
import com.valeriotor.beyondtheveil.gui.research.ResearchRecipe;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GearBenchResearchRecipe extends ResearchRecipe {
	private final GearBenchRecipe recipe;
	public GearBenchResearchRecipe(GearBenchRecipe recipe) {
		super(recipe.getOutputName());
		this.recipe = recipe;
	}
	private static final ResourceLocation CRAFTING_TEX = new ResourceLocation(References.MODID, "textures/gui/gear_bench_recipe_crafting.png");
	
	@Override
	public void render(GuiResearchPage gui, int mouseX, int mouseY) {
		super.render(gui, mouseX, mouseY);
		gui.mc.renderEngine.bindTexture(CRAFTING_TEX);
		gui.drawModalRectWithCustomSizedTexture(-140, -140, 0, 0, 280, 280, 280, 280);
		
		RenderHelper.enableGUIStandardItemLighting();
		int iX = 35 - 140, iY = 56 - 140;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				ItemStack stack = recipe.getStackInRowColumn(i, j);
				if(stack != null && stack.getItem() != Items.AIR) {
					GuiHelper.drawItemStack(gui, stack, iX + 40 * j, iY + 40 * i);
				}
			}
		}
		GuiHelper.drawItemStack(gui, recipe.getOutput(), 92, -21);
		gui.getItemRender().renderItemOverlayIntoGUI(gui.mc.fontRenderer, recipe.getOutput(), 92, -21, null);
		int hover = hoveringItem(gui, mouseX, mouseY);
		if(hover != -1) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(mouseX - gui.width / 2, mouseY - gui.height / 2, 0);
			if(hover == 17)
				gui.renderTooltip(output, 0, 0);
			else {
				if(hover < 16) {
					gui.renderTooltip(recipe.getStackInSlot(hover), 0, 0);
				}
			}
			
			GlStateManager.popMatrix();
		}
	}
	
	private int hoveringItem(GuiResearchPage gui, int mouseX, int mouseY) {
		mouseX -= gui.width / 2;
		mouseY -= gui.height / 2;
		if(gui.mc.gameSettings.guiScale == 3 || gui.mc.gameSettings.guiScale == 0) {
			mouseX = mouseX * 4 / 3;
			mouseY = mouseY * 4 / 3;
		}
		int iX = 19 - 140, iY = 40 - 140;
		int x = (mouseX - iX) / 40, y = (mouseY - iY)/40;
		if(x > -1 && x < 4 && y > -1 && y < 4) {
			return y * 4 + x;
		}
		if(mouseX > 87 && mouseX < 117 && mouseY > -35 && mouseY < 2)
			return 17;
		return -1;
	}
	
}