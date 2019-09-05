package com.valeriotor.BTV.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.shoggoth.Building2D;
import com.valeriotor.BTV.shoggoth.BuildingRegistry;
import com.valeriotor.BTV.shoggoth.BuildingTemplate;
import com.valeriotor.BTV.tileEntities.TileCityMapper;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiCityMapper extends GuiScreen{
	
	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/gui/city_mapper.png");
	private final BlockPos pos;
	private TileCityMapper te;
	private final List<BuildingTemplate> availableBuildings;
	private int scrollOffset = 0;
	private Building2D selectedBuilding = null;
	private int selectedIndex = -1;
	private final DynamicTexture map;
	
	public GuiCityMapper(BlockPos pos) {
		this.pos = pos;
		TileEntity te = Minecraft.getMinecraft().player.world.getTileEntity(pos);
		if(te instanceof TileCityMapper) {
			this.te = (TileCityMapper)te; 
		}
		List<BuildingTemplate> temp = new ArrayList<>();
		for(BuildingTemplate template : BuildingRegistry.templates) {
			if(template == null) break;
			if(template.isDefault() || template.playerKnowsBuilding(Minecraft.getMinecraft().player))
				temp.add(template);
		}
		availableBuildings = ImmutableList.copyOf(temp);
		BufferedImage map = new BufferedImage(201, 201, BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < 201; x++) {
			for(int y = 0; y < 201; y++) {
				int rgb = MapColor.COLORS[this.te.colors[x][y]].colorValue;
				int height = this.te.heights[x][y];
				int offset = (pos.getY() - height) * 4;
				int r = inBetween(0, 255, ((rgb >> 16) & 255) - offset);
				int g = inBetween(0, 255, ((rgb >> 8) & 255) - offset);
				int b = inBetween(0, 255, ((rgb) & 255) - offset);
				map.setRGB(x, y, 0xFF000000 | (r << 16) | (g << 8) | b);
			}
		}
		this.map = new DynamicTexture(map);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int numBuildings = this.availableBuildings.size();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawModalRectWithCustomSizedTexture(this.width / 2 - 229, this.height/2 - 107, 0, 0, 466, 215, 512, 512);
		int scrollBarOffset = this.height/2 - 100;
		if(numBuildings > 2) {
			scrollBarOffset += 182 * this.scrollOffset / (numBuildings / 2);
		}
		drawModalRectWithCustomSizedTexture(this.width / 2 + 93 + 129, scrollBarOffset, 467, 0, 9, 19, 512, 512);
		if(this.selectedBuilding != null) {
			int xOffset = this.selectedIndex == -1 ? 0 : this.pos.getX() - 100;
			int yOffset = this.selectedIndex == -1 ? 0 : this.pos.getZ() - 100;
			drawString(Minecraft.getMinecraft().fontRenderer, this.selectedBuilding.getLocalizedName(), this.width/2 - 220, this.height/2 - 98, 0xFFFFFFFF);
			drawString(Minecraft.getMinecraft().fontRenderer, I18n.format("gui.city_mapper.buildingx", xOffset + this.selectedBuilding.centerX), this.width/2 - 220, this.height/2 - 83, 0xFFFFFFFF);
			drawString(Minecraft.getMinecraft().fontRenderer, I18n.format("gui.city_mapper.buildingy", yOffset + this.selectedBuilding.centerY), this.width/2 - 220, this.height/2 - 68, 0xFFFFFFFF);
			drawString(Minecraft.getMinecraft().fontRenderer, I18n.format("gui.city_mapper.buildingrot", I18n.format(String.format("gui.city_mapper.rot%d", this.selectedBuilding.rotation))), this.width/2 - 220, this.height/2 - 53, 0xFFFFFFFF);
		}
		int i = this.getHoveredMenuBuilding(mouseX, mouseY);
		if(i != -1) {
			int left = this.width / 2 + 93 + 64 * (i%2);
			int top = this.height / 2 - 100 + 64 * (i/2 - this.scrollOffset);
			drawRect(left, top, left + 64, top + 64, 0x55FFFFFF);
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, map.getGlTextureId());
		drawTexturedModalRect(this.width/2 - 115, this.height/2 - 100, 0, 0, 201, 201);
		GlStateManager.enableBlend();
		GlStateManager.color(1, 1, 1, 1);
		for(i = this.scrollOffset*2; i < numBuildings && i < this.scrollOffset*2 + 6; i++) {
			availableBuildings.get(i).drawTexture(this, this.width / 2 + 93 + 64 * (i%2), this.height / 2 - 100 + 64 * (i/2 - this.scrollOffset));
		}
		
		GlStateManager.color(1, 1, 1, 1);
		for(Building2D b : te.buildings) b.render(this);
		if(this.selectedBuilding != null) {
			if(!this.selectedBuilding.isLongBuilding()) {
				int hwidth = selectedBuilding.getWidth()/2, hheight = selectedBuilding.getHeight()/2;
				drawRect(mouseX - hwidth, mouseY - hheight, mouseX + hwidth, mouseY + hheight, this.intersects(mouseX, mouseY) || !this.isSelectedInsideMap(mouseX, mouseY) ? 0x99FF0000 : 0x9900FF00);
			}
		}
		GlStateManager.disableBlend();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private static int inBetween(int lowerEnd, int higherEnd, int value) {
		return Math.min(higherEnd, Math.max(lowerEnd, value));
	}
	
	@Override
	public void handleMouseInput() throws IOException {
		int i = -Integer.signum(Mouse.getEventDWheel());
		if(this.selectedBuilding == null) {
			this.scrollOffset = inBetween(0, (availableBuildings.size() - 1)/2, this.scrollOffset + i);
		}
		else{
			this.selectedBuilding.rotation = (this.selectedBuilding.rotation + i + 4)%4;
		}
		super.handleMouseInput();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(this.selectedBuilding == null) {
			if(mouseButton == 0) {
				int i = this.getHoveredMenuBuilding(mouseX, mouseY);
				if(i != -1) {
					this.selectedBuilding = new Building2D(this.availableBuildings.get(i).index);
				} else {
					int j = this.getHoveredMapBuilding(mouseX, mouseY);
					if(j != -1) {
						this.selectedBuilding = te.buildings.get(j);
						this.selectedIndex = j;
					}
				}
			}
		} else {
			if(mouseButton == 1) {
				this.selectedBuilding = null;
				this.selectedIndex = -1;
			}
			else if(isSelectedInsideMap(mouseX, mouseY)) {
				if(!this.selectedBuilding.isLongBuilding()) {
					if(this.intersects(mouseX, mouseY)) return;
					this.selectedBuilding.setCenter(mouseX - (this.width / 2 - 115), mouseY - (this.height / 2 - 100));
					if(this.selectedIndex == -1) te.buildings.add(this.selectedBuilding);
					this.selectedBuilding = null;
					this.selectedIndex = -1;
				}
			} else {
				if(this.selectedIndex == -1) this.selectedBuilding = null;
				else {
					this.te.buildings.remove(selectedIndex);
					this.selectedBuilding = null;
					this.selectedIndex = -1;
				}
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	private int getHoveredMenuBuilding(int mx, int my) {
		int cx = width/2, cy = height/2;
		if(mx > cx + 93 && mx < cx + 213 && my > cy - 100 && my < cy + 92) {
			int ix = (mx - cx - 93)/64;
			int iy = (my - cy + 100)/64;
			int i = (iy << 1) | ix;
			if(i + this.scrollOffset*2 < this.availableBuildings.size()) return i + this.scrollOffset * 2;
		}
		return -1;
	}
	
	private int getHoveredMapBuilding(int mouseX, int mouseY) {
		for(int i = 0; i < te.buildings.size(); i++) {
			if(te.buildings.get(i).containsPoint(mouseX, mouseY, this.width, this.height)) return i;
		}
		return -1;
	}
	
	private boolean intersects(int mouseX, int mouseY) {
		for(Building2D b : te.buildings) {
			if(b.intersects(selectedBuilding, mouseX, mouseY, this.width, this.height)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isSelectedInsideMap(int mouseX, int mouseY) {
		if(this.selectedBuilding == null) return false;
		int cx = this.width/2, cy = this.height/2;
		return mouseX > cx - 115 + selectedBuilding.getWidth()/2 && mouseX < cx + 115 - selectedBuilding.getWidth()/2 &&
				mouseY > cy - 100 + selectedBuilding.getHeight()/2 && mouseY < cy + 100 - selectedBuilding.getHeight()/2;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	
}
