package com.valeriotor.beyondtheveil.gui.research;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.lwjgl.input.Mouse;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capabilities.ResearchProvider;
import com.valeriotor.beyondtheveil.gui.GuiHelper;
import com.valeriotor.beyondtheveil.gui.IItemRenderGui;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.research.Research;
import com.valeriotor.beyondtheveil.research.ResearchConnection;
import com.valeriotor.beyondtheveil.research.ResearchRegistry;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.ConfigLib;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import com.valeriotor.beyondtheveil.util.SyncUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiNecronomicon extends GuiScreen implements IItemRenderGui{
	
	int topX;
	int topY;
	int factor = 3;
	List<Research> newClickables = new ArrayList<>();
	List<Research> clickables = new ArrayList<>();
	List<Research> visibles = new ArrayList<>();
	List<ResearchConnection> connections = new ArrayList<>();
	List<Point> stars = new ArrayList<>();
	int counter = 0;
	int connectionColor = 0xFF002000;
	
	private static final ResourceLocation RESEARCH_BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/res_background.png");
	
	public GuiNecronomicon() {
		HashMap<String, ResearchStatus> map = Minecraft.getMinecraft().player.getCapability(ResearchProvider.RESEARCH, null).getResearches();
		IPlayerData data = Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null);
		this.topX = data.getOrSetInteger(PlayerDataLib.NECRO_X, -400, false);
		this.topY = data.getOrSetInteger(PlayerDataLib.NECRO_Y, -200, false);
		if(map.get("FIRSTDREAMS").getStage() == -1) {
			this.topX = -400;
			this.topY = -200;
		}
		this.factor = data.getOrSetInteger(PlayerDataLib.NECRO_FACTOR, 3, false);
		for(Entry<String, ResearchStatus> entry : map.entrySet()) {
			if(entry.getValue().isKnown(map, data)) {
				if(entry.getValue().getStage() == -1)
					newClickables.add(entry.getValue().res);
				else
					clickables.add(entry.getValue().res);
			}
			else if(entry.getValue().isVisible(map, data))
				visibles.add(entry.getValue().res);
		}
		
		for(ResearchConnection rc: ResearchRegistry.connections) {
			if(rc.isVisible(map, data)) {
				connections.add(rc);
			}
		}
		this.connectionColor = (255 << 24) | (ConfigLib.connectionRed << 16) | (ConfigLib.connectionGreen << 8) | ConfigLib.connectionBlue;
	}
	
	@Override
	public void initGui() {
		stars.clear();
		Random r = mc.player.getRNG();
		int a = 100 + r.nextInt(50);
		for(int i = 0; i < a; i++) {
			stars.add(new Point(r.nextInt(this.width), r.nextInt(this.height)));
		}
		
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawRect(0, 0, this.width, this.height, 0xFF000000); 
		for(ResearchConnection rc : connections)
			this.drawConnection(rc, partialTicks);
		for(Point p : stars) {
			this.drawRect(p.x, p.y, p.x+1, p.y+1, 0xFFFFFFFF);
		}
		GlStateManager.color(0.8F, 0.8F, 0.8F);
		mc.renderEngine.bindTexture(RESEARCH_BACKGROUND);
		for(Research r : clickables) this.drawResearchBackground(r);
		GlStateManager.color(0.25F, 0.25F, 0.25F);
		for(Research r : visibles) this.drawResearchBackground(r);
		float coloring = 0.52F + (float) (Math.sin((this.counter + partialTicks) / 30 * 2 * Math.PI) / 4);
		GlStateManager.color(coloring, coloring, coloring);
		for(Research r : newClickables) this.drawResearchBackground(r);

		RenderHelper.enableStandardItemLighting();
		for(Research r : clickables) this.drawResearch(r, mouseX, mouseY);
		for(Research r : visibles) this.drawResearch(r, mouseX, mouseY);
		for(Research r : newClickables) this.drawResearch(r, mouseX, mouseY);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		this.counter++;
		super.updateScreen();
	}
	
	private void drawResearch(Research res, int mouseX, int mouseY) {
		int resX = res.getX() * 15 * factor, resY = res.getY() * 15 * factor;
		if(resX > topX - 24 && resX < topX + this.width && resY > topY - 24 && resY < topY + this.height) {		
			ItemStack[] icons = res.getIconStacks();
			GuiHelper.drawItemStack(this, icons[counter % 20 % icons.length], resX - topX, resY - topY);
			if(mouseX > resX - topX - 4 && mouseX < resX - topX + 20 && mouseY > resY - topY - 4 && mouseY < resY - topY + 20) {
				drawHoveringText(I18n.format(res.getName()), mouseX, mouseY);
			}
		}
	}
	
	private void drawResearchBackground(Research res) {
		int resX = res.getX() * 15 * factor, resY = res.getY() * 15 * factor;
		if(resX > topX - 24 && resX < topX + this.width && resY > topY - 24 && resY < topY + this.height) {
			drawModalRectWithCustomSizedTexture(resX - topX - 4, resY - topY - 4, 0, 0, 24, 24, 24, 24);	
		}
	}
	
	private void drawConnection(ResearchConnection rc, float partialTicks) {
		if(rc.shouldRender(topX, topY, width, height)) {
			Point left = rc.getLeftPoint(), right = rc.getRightPoint();
			double dist = left.distance(right) * 15 * factor;
			int lx = left.x * 15 * factor, ly = left.y * 15 * factor, rx = right.y * 15 * factor, ry = right.y * 15 * factor;
			GlStateManager.pushMatrix();
			double phi = Math.asin((right.y - left.y)*15*factor/dist);
			GlStateManager.translate(lx - topX + 8, ly - topY + 8, 0);
			GlStateManager.rotate((float)(phi * 180 / Math.PI), 0F, 0F, 1F);
			for(int i = 0; i < dist; i++) {
				int signum = (int) Math.signum(counter % 80 - 40);
				double amplifier = 15 * (signum  * Math.pow((counter % 40 + partialTicks) / 20 - 1, 4) - signum);
				int x = i, y = (int) (amplifier * Math.sin(i * Math.PI / dist));
				drawRect(x, y, x + 1, y+1, this.connectionColor);
			}
			GlStateManager.popMatrix();
			
		}
	}
	
	@Override
	public void handleMouseInput() throws IOException {
		if(Mouse.isButtonDown(0)) {
			topX = MathHelperBTV.clamp(-700, 3840 - this.width/2, topX - Mouse.getDX());
			topY = MathHelperBTV.clamp(-700, 2160 - this.height/2, topY + Mouse.getDY());
		}
		this.factor = MathHelperBTV.clamp(2, 5, this.factor + (int)Math.signum(Mouse.getDWheel()));
		super.handleMouseInput();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		for(Research res : this.clickables) {
			if(openResearch(res, mouseX, mouseY)) return;
		}
		for(Research res : this.newClickables) {
			if(openResearch(res, mouseX, mouseY)) return;
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1 || keyCode == 18)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
			SyncUtil.addIntDataOnClient(mc.player, false, PlayerDataLib.NECRO_X, this.topX);
			SyncUtil.addIntDataOnClient(mc.player, false, PlayerDataLib.NECRO_Y, this.topY);
			SyncUtil.addIntDataOnClient(mc.player, false, PlayerDataLib.NECRO_FACTOR, this.factor);
            if (this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
        }
	}
	
	private boolean openResearch(Research res, int mouseX, int mouseY) {
		int resX = res.getX() * 15 * factor - topX - 4, resY = res.getY() * 15 * factor - topY - 4;
		if(mouseX > resX - 4 && mouseX < resX + 24 && mouseY > resY - 4 && mouseY < resY + 24) {
			ResearchStatus status = ResearchUtil.getResearch(mc.player, res.getKey());
			if(status.getStage() == - 1) ResearchUtil.progressResearchClient(mc.player, res.getKey());
			SyncUtil.addIntDataOnClient(mc.player, false, PlayerDataLib.NECRO_X, this.topX);
			SyncUtil.addIntDataOnClient(mc.player, false, PlayerDataLib.NECRO_Y, this.topY);
			SyncUtil.addIntDataOnClient(mc.player, false, PlayerDataLib.NECRO_FACTOR, this.factor);
			this.mc.displayGuiScreen(new GuiResearchPage(status));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	@Override
	public RenderItem getItemRender() {
		return this.itemRender;
	}

	@Override
	public void renderTooltip(ItemStack stack, int x, int y) {
		this.renderToolTip(stack, x, y);
	}
}
