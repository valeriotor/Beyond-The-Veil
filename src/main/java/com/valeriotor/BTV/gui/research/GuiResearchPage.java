package com.valeriotor.BTV.gui.research;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.valeriotor.BTV.gui.GuiHelper;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.research.ResearchStatus;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.util.MathHelperBTV;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiResearchPage extends GuiScreen{
	
	private final ResearchStatus status;
	private final String title;
	private List<List<String>> pages = new ArrayList<>();
	private List<String> reqText;
	private int page = 0;

	private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/res_page.png");
	private static final ResourceLocation ARROW = new ResourceLocation(References.MODID, "textures/gui/right_arrow.png");
	
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
			if(this.page == (this.pages.size() + 1) / 2 - 1)
				GlStateManager.color(0.5F, 0.5F, 0.5F);
			else if(hoveringRightArrow(mouseX, mouseY))
				GlStateManager.color(0.9F, 0.7F, 1, 0.7F);
			drawModalRectWithCustomSizedTexture(150, +115, 0, 0, 32, 32, 32, 32);
			
			GlStateManager.pushMatrix();
			GlStateManager.color(1, 1, 1);
			GlStateManager.translate(-150, 145, 0);
			GlStateManager.rotate(180, 0, 0, 1);
			if(this.page == 0)
				GlStateManager.color(0.5F, 0.5F, 0.5F);
			else if(hoveringLeftArrow(mouseX, mouseY))
				GlStateManager.color(0.9F, 0.7F, 1, 0.7F);
			drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 32, 32, 32, 32);
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
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
		if(hoveringLeftArrow(mouseX, mouseY)) {
			this.page = MathHelperBTV.clamp(0, (this.pages.size() + 1) / 2 - 1, this.page - 1);
		} else if(hoveringRightArrow(mouseX, mouseY)) {
			this.page = MathHelperBTV.clamp(0, (this.pages.size() + 1) / 2 - 1, this.page + 1);			
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == 18)
			this.mc.displayGuiScreen(new GuiNecronomicon());
		super.keyTyped(typedChar, keyCode);
	}
	
	private boolean hoveringLeftArrow(int mouseX, int mouseY) {
		if(this.mc.gameSettings.guiScale == 3 || this.mc.gameSettings.guiScale == 0)
			return mouseX > this.width / 2 - 182 * 3/4 && mouseX < this.width / 2 - 150 * 3/4 && mouseY > this.height / 2 + 115 * 3/4 && mouseY < this.height / 2 + (115 + 32) * 3/4;
		return mouseX > this.width / 2 - 182 && mouseX < this.width / 2 - 150 && mouseY > this.height / 2 + 115 && mouseY < this.height / 2 + 115 + 32;
	}

	private boolean hoveringRightArrow(int mouseX, int mouseY) {
		if(this.mc.gameSettings.guiScale == 3 || this.mc.gameSettings.guiScale == 0)
			return mouseX > this.width / 2 + 150 * 3/4 && mouseX < this.width / 2 + 182 * 3/4 && mouseY > this.height / 2 + 115 * 3/4 && mouseY < this.height / 2 + (115 + 32) * 3/4;
		return mouseX > this.width / 2 + 150 && mouseX < this.width / 2 + 182 && mouseY > this.height / 2 + 115 && mouseY < this.height / 2 + 115 + 32;
	}
	
}
