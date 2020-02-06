package com.valeriotor.BTV.gui.research;

import java.awt.Point;
import java.util.List;

import com.valeriotor.BTV.dreaming.Memory;
import com.valeriotor.BTV.gui.GuiHelper;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.research.MultiblockSchematic;
import com.valeriotor.BTV.research.ResearchRegistry;
import com.valeriotor.BTV.util.ItemHelper;
import com.valeriotor.BTV.util.MathHelperBTV;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ResearchRecipe {
	
	public static ResearchRecipe getRecipe(String recipeKey) {
		String[] ss = recipeKey.split(";");
		if(ResearchRegistry.multiblocks.containsKey(ss[0])) {
			return new MultiBlockRecipe(recipeKey, ResearchRegistry.multiblocks.get(ss[0]));
		}else if(ss.length > 1 && Item.REGISTRY.getObject(new ResourceLocation(ss[0])) == ItemRegistry.memory_phial) {
			return new MemoryRecipe(recipeKey);
		} else if(ResearchRegistry.recipes.containsKey(recipeKey)) {
			return new CraftingRecipe(recipeKey, ResearchRegistry.recipes.get(recipeKey));
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
	
	private static class CraftingRecipe extends ResearchRecipe {
		
		List<IRecipe> recipes;
		int counter = 0;
		int page = 0;
		
		protected CraftingRecipe(String recipeKey, List<IRecipe> recipes) {
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
	
	private static class MultiBlockRecipe extends ResearchRecipe {
		
		private final String tooltip;
		private final ItemStack[][][] stacks;
		private Point topLeft, bottomRight;
		private int currentLayer = 0;
		
		protected MultiBlockRecipe(String recipeKey, MultiblockSchematic schem) {
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
	
}
