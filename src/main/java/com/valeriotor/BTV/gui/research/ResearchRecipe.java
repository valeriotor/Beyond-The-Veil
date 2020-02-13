package com.valeriotor.BTV.gui.research;

import com.valeriotor.BTV.crafting.GearBenchRecipeRegistry;
import com.valeriotor.BTV.gui.GuiHelper;
import com.valeriotor.BTV.gui.research.recipes.CraftingResearchRecipe;
import com.valeriotor.BTV.gui.research.recipes.GearBenchResearchRecipe;
import com.valeriotor.BTV.gui.research.recipes.MemoryResearchRecipe;
import com.valeriotor.BTV.gui.research.recipes.MultiBlockResearchRecipe;
import com.valeriotor.BTV.gui.research.recipes.SacrificeResearchRecipe;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.research.ResearchRegistry;
import com.valeriotor.BTV.sacrifice.SacrificeRecipeRegistry;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ResearchRecipe {
	
	public static ResearchRecipe getRecipe(String recipeKey) {
		String[] ss = recipeKey.split(";");
		if(SacrificeRecipeRegistry.getRecipe(recipeKey) != null) {
			return new SacrificeResearchRecipe(recipeKey, SacrificeRecipeRegistry.getRecipe(recipeKey));
		} else if(ResearchRegistry.multiblocks.containsKey(ss[0])) {
			return new MultiBlockResearchRecipe(recipeKey, ResearchRegistry.multiblocks.get(ss[0]));
		} else if(ss.length > 1 && Item.REGISTRY.getObject(new ResourceLocation(ss[0])) == ItemRegistry.memory_phial) {
			return new MemoryResearchRecipe(recipeKey);
		} else if(GearBenchRecipeRegistry.recipesFromKeys.containsKey(recipeKey)) {
			return new GearBenchResearchRecipe(GearBenchRecipeRegistry.recipesFromKeys.get(recipeKey));
		} else if(ResearchRegistry.recipes.containsKey(recipeKey)) {
			return new CraftingResearchRecipe(recipeKey, ResearchRegistry.recipes.get(recipeKey));
		}
		return null;
	}
	
	protected ItemStack output;
	
	protected ResearchRecipe(String recipeKey) {
		String[] ss = recipeKey.split(";");
		Item a = Item.REGISTRY.getObject(new ResourceLocation(ss[0]));
		if(a != null) {
			output = new ItemStack(a);
			if(ss.length > 1 && a == ItemRegistry.memory_phial) {
				ItemHelper.checkTagCompound(output).setString("memory", ss[1]);
			}
		}
	}
	
	//private static final ResourceLocation BACKGROUND_TEX = new ResourceLocation(References.MODID, "textures/gui/recipe_background.png");
	public void render(GuiResearchPage gui, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1);
		//gui.mc.renderEngine.bindTexture(BACKGROUND_TEX);
		//gui.drawModalRectWithCustomSizedTexture(-182, -182, 0, 0, 364, 364, 364, 364);
	}
	
	public void renderKey(GuiResearchPage gui) {
		GuiHelper.drawItemStack(gui, output, 0, 0);
	}
	
	public void renderTooltip(GuiResearchPage gui, int x, int y) {
		gui.renderTooltip(output, x, y);
	}
	
	public ItemStack getOutput() {
		return this.output;
	}
	
	public void update() {}
	
	public boolean mouseClicked(GuiResearchPage gui, int mouseX, int mouseY, int mouseButton) {
		return false;
	}
	
	protected int hoveringArrow(GuiResearchPage gui, int mouseX, int mouseY) {
		mouseX -= gui.width / 2;
		mouseY -= gui.height / 2;
		if(gui.mc.gameSettings.guiScale == 3 || gui.mc.gameSettings.guiScale == 0) {
			mouseX = mouseX * 4 / 3;
			mouseY = mouseY * 4 / 3;
		}
		int a = -1;
		if(mouseX > 104 && mouseX < 136 && mouseY > -16 && mouseY < 16) {
			a = 1;
		} else if(mouseX > -136 && mouseX < -104 && mouseY > -16 && mouseY < 16) {
			a = 0;
		}
		return a;
	}
	
	
	
	
	
	
		
}
