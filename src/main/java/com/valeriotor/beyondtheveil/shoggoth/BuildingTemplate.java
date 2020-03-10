package com.valeriotor.beyondtheveil.shoggoth;

import com.valeriotor.beyondtheveil.gui.GuiCityMapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BuildingTemplate {
	
	public final int width;
	public final int height;
	public final boolean longBuilding;
	public final int index;
	public final String name;
	protected BlockBuffer buffer;
	
	public BuildingTemplate(int width, int height, boolean longBuilding, String name, int index) {
		this.width = width;
		this.height = height;
		this.longBuilding = longBuilding;
		this.index = index;
		BuildingRegistry.templates[index] = this;
		this.name = name;
	}
	
	@SideOnly(Side.CLIENT)
	public void drawTexture(GuiCityMapper gui, int xTopLeft, int yTopLeft) {
		this.drawScaledTexture(gui, xTopLeft, yTopLeft, 2);
	}
	
	@SideOnly(Side.CLIENT)
	public void drawScaledTexture(GuiCityMapper gui, int xTopLeft, int yTopLeft, float scale) {
		this.drawScaledTexture(gui, xTopLeft, yTopLeft, scale, 32, 32);
	}
	
	@SideOnly(Side.CLIENT)
	public abstract void drawScaledTexture(GuiCityMapper gui, int xTopLeft, int yTopLeft, float scale, int width, int height);
	
	
	protected void drawHelper(GuiCityMapper gui, int xTopLeft, int yTopLeft, int u, int v, float scale) {
		GlStateManager.enableBlend();
		GlStateManager.color(1, 1, 1, 1); 
		Minecraft.getMinecraft().renderEngine.bindTexture(BuildingRegistry.GuiIcons);
		gui.drawModalRectWithCustomSizedTexture(xTopLeft, yTopLeft, u*scale, v*scale, (int)(32*scale), (int)(32*scale), 128*scale, 128*scale);
		GlStateManager.disableBlend();
	}
	
	public abstract boolean isDefault();
	public boolean playerKnowsBuilding(EntityPlayer p) {
		return false;
	}
	
	public BlockBuffer getBlockBuffer() {
		return this.buffer;
	}
	
	@SideOnly(Side.CLIENT)
	public String getLocalizedName() {
		return I18n.format("building." + name);
	}
	
}
