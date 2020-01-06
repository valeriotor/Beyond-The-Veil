package com.valeriotor.BTV.gui.research;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.gui.GuiHelper;
import com.valeriotor.BTV.gui.IItemRenderGui;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.research.Research.SubResearch;
import com.valeriotor.BTV.research.ResearchStatus;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.util.MathHelperBTV;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiResearchPage extends GuiScreen implements IItemRenderGui{
	
	private final ResearchStatus status;
	private final String title;
	private List<List<String>> pages = new ArrayList<>();
	private List<String> reqText;
	private List<ResearchRecipe> recipes = new ArrayList<>();
	private ResearchRecipe shownRecipe;
	private int page = 0;

	private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/res_page.png");
	public static final ResourceLocation ARROW = new ResourceLocation(References.MODID, "textures/gui/right_arrow.png");
	
	public GuiResearchPage(ResearchStatus status) {
		this.status = status;
		this.title = I18n.format(status.res.getName());
	}
	
	@Override
	public void initGui() {
		this.page = 0;
		pages.clear();
		recipes.clear();
		shownRecipe = null;
		String[] paragraphs = I18n.format(this.status.res.getStages()[this.status.getStage()].getTextKey()).split("<BR>");
		this.formatText(paragraphs);
		this.makeRecipes(this.status.res.getStages()[this.status.getStage()].getRecipes());
		IPlayerData data = mc.player.getCapability(PlayerDataProvider.PLAYERDATA, null);
		if(this.status.isComplete()) {
			for(SubResearch sr : this.status.res.getAddenda()) {
				if(sr.meetsRequirements(data)) {
					paragraphs = I18n.format(sr.getTextKey()).split("<BR>");
					this.formatText(paragraphs);
					this.makeRecipes(sr.getRecipes());
				}
			}
		}
		this.buttonList.clear();
		int bHeight = this.height / 2 + (mc.gameSettings.guiScale == 3 || mc.gameSettings.guiScale == 0 ? 90 : 130);
		GuiButton b = new GuiButton(0, this.width/2 - 100, bHeight, I18n.format("gui.research_page.complete"));
		this.buttonList.add(b);
		if(!status.canProgressStage(mc.player)) {
			this.buttonList.get(0).visible = false;
			String[] reqs = status.res.getStages()[this.status.getStage()].getRequirements();
			if(reqs != null)
				this.reqText = Arrays.stream(reqs)
								.map(s -> "research.".concat(s).concat(".text"))
								.map(I18n::format)
								.collect(Collectors.toList());
		}
		super.initGui();
	}
	
	private void formatText(String[] paragraphs) {
		List<String> ls = new ArrayList<>();
		int i = 0;
		pages.add(new ArrayList<>());
		for(String s : paragraphs) {
			ls = GuiHelper.splitStringsByWidth(s, 190, mc.fontRenderer);
			for(String ss : ls) {
				if(i > 210) {
					i = 0;
					pages.add(new ArrayList<>());
				}
				if(s.contains("<PAGE>")) {
					i = 0;
					String[] split = ss.split("<PAGE>");
					for(int j = 0; j < split.length; j++) {
						pages.get(pages.size() - 1).add(split[j]);
						if(j != split.length - 1)
							pages.add(new ArrayList<>());
					}
				} else pages.get(pages.size() - 1).add(ss);
				i += 15;
			}
			i += 15;
			pages.get(pages.size() - 1).add("");
		}
	}
	
	private void makeRecipes(String[] recipes) {
		for(String s : recipes) {
			ResearchRecipe r = ResearchRecipe.getRecipe(s);
			if(r != null) {
				this.recipes.add(r);
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawRect(0, 0, this.width, this.height, 0xFF000000); 
		GlStateManager.color(1, 1, 1);
		this.mc.renderEngine.bindTexture(BACKGROUND);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.width / 2, this.height / 2, 0);
		if(this.mc.gameSettings.guiScale == 3 || this.mc.gameSettings.guiScale == 0) {
			GlStateManager.scale(0.75, 0.75, 0.75);
		} else {
			
		}
		drawModalRectWithCustomSizedTexture(- 480, - 270, 0, 0, 960, 540, 960, 540);
		if(this.shownRecipe == null) { 
			GlStateManager.pushMatrix();	
			GlStateManager.scale(1.5, 1.5, 1);
			this.drawCenteredString(mc.fontRenderer, title, 0, - 100, 0xFFAAFFAA);
			GlStateManager.popMatrix();
			if(this.pages.size() > this.page * 2) {
				int i = 0;
				for(String s : this.pages.get(this.page * 2)) {
					this.drawString(mc.fontRenderer, s, - 205, - 110 + (i++)*15, 0xFFFFFFFF);
				}
			}
			if(this.pages.size() > this.page * 2 + 1) {
				int i = 0;
				for(String s : this.pages.get(this.page * 2 + 1)) {
					this.drawString(mc.fontRenderer, s, 5, - 110 + (i++)*15, 0xFFFFFFFF);
				}
			}
			if(this.buttonList.isEmpty() || !this.buttonList.get(0).visible) {
				if(this.reqText != null) {
					int i = 0;
					for(String s : this.reqText) {
						this.drawCenteredString(mc.fontRenderer, s, 0, 130 + (i++) * 15, 0xFFFE9600);
					}
				}
			}
			if(this.pages.size() > 2) {
				mc.renderEngine.bindTexture(ARROW);
				GlStateManager.pushMatrix();
				GlStateManager.translate(178, 131, 0);
				if(this.page < (this.pages.size() + 1) / 2 - 1) {
					if(hoveringRightArrow(mouseX, mouseY))
						GlStateManager.scale(1.5, 1.5, 1);
					drawModalRectWithCustomSizedTexture(-16, -16, 0, 0, 32, 32, 32, 32);
				}
				GlStateManager.popMatrix();
				GlStateManager.pushMatrix();
				GlStateManager.color(1, 1, 1);
				GlStateManager.translate(-178, 131, 0);
				GlStateManager.rotate(180, 0, 0, 1);
				if(this.page > 0) {
					if(hoveringLeftArrow(mouseX, mouseY))
						GlStateManager.scale(1.5, 1.5, 1);
					drawModalRectWithCustomSizedTexture(-16, -16, 0, 0, 32, 32, 32, 32);
				}
				GlStateManager.popMatrix();
			}
		} else
			shownRecipe.render(this, mouseX, mouseY);
		
		int hoveredKey = this.hoveringRecipeKey(mouseX, mouseY);
		for(int i = 0; i < 6 && i < recipes.size(); i++) {
			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.pushMatrix();
			GlStateManager.translate(-160 + i * 20, 125, 0);
			if(hoveredKey == i) {
				GlStateManager.scale(2, 2, 2);
			}
			recipes.get(i).renderKey(this);
			GlStateManager.popMatrix();
		}
		
		GlStateManager.popMatrix();
		if(hoveredKey != -1) {
			this.renderTooltip(recipes.get(hoveredKey).getOutput(), mouseX + 20, mouseY + 10);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 0) {
			ResearchUtil.progressResearchClient(mc.player, status.res.getKey());
			this.initGui();
		} 
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		int a = this.hoveringRecipeKey(mouseX, mouseY);
		if(hoveringLeftArrow(mouseX, mouseY)) {
			this.page = MathHelperBTV.clamp(0, (this.pages.size() + 1) / 2 - 1, this.page - 1);
		} else if(hoveringRightArrow(mouseX, mouseY)) {
			this.page = MathHelperBTV.clamp(0, (this.pages.size() + 1) / 2 - 1, this.page + 1);			
		} else if(a != -1){
			this.shownRecipe = recipes.get(a);
		} else if(this.shownRecipe == null || !this.shownRecipe.mouseClicked(this, mouseX, mouseY, mouseButton))
			this.shownRecipe = null;
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == 18)
			if(this.shownRecipe == null)
				this.mc.displayGuiScreen(new GuiNecronomicon());
			else
				this.shownRecipe = null;
		super.keyTyped(typedChar, keyCode);
	}
	
	private boolean hoveringLeftArrow(int mouseX, int mouseY) {
		if(this.mc.gameSettings.guiScale == 3 || this.mc.gameSettings.guiScale == 0)
			return mouseX > this.width / 2 - 182 * 3/4 && mouseX < this.width / 2 - 150 * 3/4 && mouseY > this.height / 2 + 115 * 3/4 && mouseY < this.height / 2 + (115 + 32) * 3/4;
		return mouseX > this.width / 2 - 194 && mouseX < this.width / 2 - 162 && mouseY > this.height / 2 + 115 && mouseY < this.height / 2 + 115 + 32;
	}

	private boolean hoveringRightArrow(int mouseX, int mouseY) {
		if(this.mc.gameSettings.guiScale == 3 || this.mc.gameSettings.guiScale == 0)
			return mouseX > this.width / 2 + 150 * 3/4 && mouseX < this.width / 2 + 182 * 3/4 && mouseY > this.height / 2 + 115 * 3/4 && mouseY < this.height / 2 + (115 + 32) * 3/4;
		return mouseX > this.width / 2 + 162 && mouseX < this.width / 2 + 194 && mouseY > this.height / 2 + 115 && mouseY < this.height / 2 + 115 + 32;
	}
	
	private int hoveringRecipeKey(int mouseX, int mouseY) {
		mouseX -= this.width / 2;
		mouseY -= this.height / 2;
		if(this.mc.gameSettings.guiScale == 3 || this.mc.gameSettings.guiScale == 0) {
			mouseX = mouseX * 4 / 3;
			mouseY = mouseY * 4 / 3;
		}
		if(mouseY > 125 && mouseY < 141) {
			int a = (mouseX + 160) / 20;
			if(a < 6 && a < recipes.size() && a >= 0)
				return a;
		}
		return -1;
	}

	@Override
	public RenderItem getItemRender() {
		return this.itemRender;
	}
	
	@Override
	public void renderTooltip(ItemStack stack, int x, int y) {
		this.renderToolTip(stack, x, y);
	}
	
	@Override
	public void updateScreen() {
		if(this.shownRecipe != null)
			this.shownRecipe.update();
	}
	
}
