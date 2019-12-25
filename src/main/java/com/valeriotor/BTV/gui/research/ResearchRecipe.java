package com.valeriotor.BTV.gui.research;

import com.valeriotor.BTV.dreaming.Memory;
import com.valeriotor.BTV.gui.GuiHelper;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ResearchRecipe {
	
	public static ResearchRecipe getRecipe(String recipeKey) {
		if(Item.REGISTRY.getObject(new ResourceLocation(recipeKey.split(";")[0])) == ItemRegistry.memory_phial) {
			return new MemoryRecipe(recipeKey);
		}
		return null;
	}
	
	protected ItemStack output;
	
	protected ResearchRecipe(String recipeKey) {
		String[] ss = recipeKey.split(";");
		Item a = Item.REGISTRY.getObject(new ResourceLocation(ss[0]));
		output = new ItemStack(a);
		if(a == ItemRegistry.memory_phial) {
			ItemHelper.checkTagCompound(output).setString("memory", ss[1]);
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
	
	public ItemStack getOutput() {
		return this.output;
	}
	
	private static class MemoryRecipe extends ResearchRecipe {
		
		private ItemStack input;
		protected MemoryRecipe(String recipeKey) {
			super(recipeKey);
			String dataName = ItemHelper.checkStringTag(output, "memory", Memory.LEARNING.getDataName());
			input = new ItemStack(Memory.getMemoryFromDataName(dataName).getItem());
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
			//gui.drawTexturedModalRect(-57, -57, 0, 0, 114, 114);
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
	
}
