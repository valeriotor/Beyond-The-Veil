package com.valeriotor.BTV.gui.research.recipes;

import java.util.List;

import com.valeriotor.BTV.gui.GuiHelper;
import com.valeriotor.BTV.gui.research.GuiResearchPage;
import com.valeriotor.BTV.gui.research.ResearchRecipe;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.util.MathHelperBTV;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class CraftingResearchRecipe extends ResearchRecipe {
	
	List<IRecipe> recipes;
	int counter = 0;
	int page = 0;
	
	public CraftingResearchRecipe(String recipeKey, List<IRecipe> recipes) {
		super(recipeKey);
		this.recipes = recipes;
	}

	private static final ResourceLocation CRAFTING_TEX = new ResourceLocation(References.MODID, "textures/gui/recipe_crafting.png");
	@Override
	public void render(GuiResearchPage gui, int mouseX, int mouseY) {
		super.render(gui, mouseX, mouseY);

		gui.mc.renderEngine.bindTexture(CRAFTING_TEX);
		gui.drawModalRectWithCustomSizedTexture(-114, -114, 0, 0, 228, 228, 228, 228);
		
		gui.mc.renderEngine.bindTexture(gui.ARROW);
		int sel = hoveringArrow(gui, mouseX, mouseY);
		if(this.page < this.recipes.size() - 1) {
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
		List<Ingredient> ings = recipes.get(page).getIngredients();
		int iX = 35 - 114, iY = 56 - 114;
		for(int i = 0; i < ings.size(); i++) {
			ItemStack[] stacks = ings.get(i).getMatchingStacks();
			if(stacks != null && stacks.length > 0) {
				GuiHelper.drawItemStack(gui, stacks[counter % 20 % stacks.length], iX + 40 * (i % 3), iY + 40 * (i / 3));
			}
		}
		
		GuiHelper.drawItemStack(gui, output, 186 - 114, 96 - 114);
		gui.getItemRender().renderItemOverlayIntoGUI(gui.mc.fontRenderer, output, 186 - 114, 96 - 114, null);
		int hover = hoveringItem(gui, mouseX, mouseY);
		if(hover != -1) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(mouseX - gui.width / 2, mouseY - gui.height / 2, 0);
			if(hover == 10)
				gui.renderTooltip(output, 0, 0);
			else {
				if(hover < ings.size()) {
					ItemStack[] stacks = ings.get(hover).getMatchingStacks();
					if(stacks != null && stacks.length > 0) {
						gui.renderTooltip(stacks[counter % 20 % stacks.length], 0, 0);
					}
				}
			}
			
			GlStateManager.popMatrix();
		}
	}
	
	@Override
	public void update() {
		this.counter++;
		this.counter %= 200;
	}
	
	@Override
	public boolean mouseClicked(GuiResearchPage gui, int mouseX, int mouseY, int mouseButton) {
		int sel = hoveringArrow(gui, mouseX, mouseY);
		if(sel == 0)
			this.page = MathHelperBTV.clamp(0, recipes.size(), page - 1);
		else if(sel == 1)
			this.page = MathHelperBTV.clamp(0, recipes.size(), page + 1);
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
		int iX = 24 - 114, iY = 45 - 114;
		int x = (mouseX - iX) / 40, y = (mouseY - iY)/40;
		if(x > -1 && x < 3 && y > -1 && y < 3) {
			return y * 3 + x;
		}
		if(mouseX > 72 && mouseX < 90 && mouseY > -18 && mouseY < 0)
			return 10;
		return -1;
	}
	
}

