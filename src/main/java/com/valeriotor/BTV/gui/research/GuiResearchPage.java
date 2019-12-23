package com.valeriotor.BTV.gui.research;

import java.util.ArrayList;
import java.util.List;

import com.valeriotor.BTV.gui.GuiHelper;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.research.ResearchStatus;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiResearchPage extends GuiScreen{
	
	private final ResearchStatus status;
	private final String title;
	private List<List<String>> pages = new ArrayList<>();
	private String reqText = "";
	private int page = 0;

	private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/res_page.png");
	
	public GuiResearchPage(ResearchStatus status) {
		this.status = status;
		this.title = I18n.format(status.res.getName());
	}
	
	@Override
	public void initGui() {
		pages.clear();
		String[] paragraphs = I18n.format(this.status.res.getStages()[this.status.getStage()].getTextKey()).split("<BR>");
		List<String> ls = new ArrayList<>();
		int i = 0;
		pages.add(new ArrayList<>());
		for(String s : paragraphs) {
			ls = GuiHelper.splitStringsByWidth(s, 190, mc.fontRenderer);
			for(String ss : ls) {
				if(i > 240) {
					i = 0;
					pages.add(new ArrayList<>());
				}
				pages.get(pages.size() - 1).add(ss);
			}
			i += 15;
			pages.get(pages.size() - 1).add("");
		}
		super.initGui();
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
		GlStateManager.pushMatrix();	
		GlStateManager.scale(1.5, 1.5, 1);
		this.drawCenteredString(mc.fontRenderer, title, 0, - 100, 0xFFAAFFAA);
		GlStateManager.popMatrix();
		if(this.pages.size() > this.page * 2) {
			int i = 0;
			for(String s : this.pages.get(this.page * 2)) {
				this.drawString(mc.fontRenderer, s, - 200, - 110 + (i++)*15, 0xFFFFFFFF);
			}
		}
		if(this.pages.size() > this.page * 2 + 1) {
			int i = 0;
			for(String s : this.pages.get(this.page * 2 + 1)) {
				this.drawString(mc.fontRenderer, s, 0, - 110 + (i++)*15, 0xFFFFFFFF);
			}
		}
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	
}
