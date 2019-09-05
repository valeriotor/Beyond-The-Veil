package com.valeriotor.BTV.shoggoth;

import com.valeriotor.BTV.gui.GuiCityMapper;

import net.minecraft.client.Minecraft;
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
	
	public BuildingTemplate(int width, int height, boolean longBuilding, int index, String name) {
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
	public abstract void drawScaledTexture(GuiCityMapper gui, int xTopLeft, int yTopLeft, float scale);
	
	protected void drawHelper(GuiCityMapper gui, int xTopLeft, int yTopLeft, int u, int v, float scale) {
		Minecraft.getMinecraft().renderEngine.bindTexture(BuildingRegistry.GuiIcons);
		gui.drawModalRectWithCustomSizedTexture(xTopLeft, yTopLeft, u*scale, v*scale, (int)(32*scale), (int)(32*scale), 128*scale, 128*scale);
	}
	
	public abstract boolean isDefault();
	public boolean playerKnowsBuilding(EntityPlayer p) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public String getLocalizedName() {
		return I18n.format("building." + name);
	}
	
}
