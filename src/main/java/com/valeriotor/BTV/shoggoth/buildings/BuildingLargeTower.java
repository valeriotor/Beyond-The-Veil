package com.valeriotor.BTV.shoggoth.buildings;

import com.valeriotor.BTV.gui.GuiCityMapper;
import com.valeriotor.BTV.shoggoth.BuildingRegistry;
import com.valeriotor.BTV.shoggoth.BuildingTemplate;

import net.minecraft.client.Minecraft;

public class BuildingLargeTower extends BuildingTemplate{

	public BuildingLargeTower(String name, int index) {
		super(24, 24, false, index, name);
	}

	@Override
	public boolean isDefault() {
		return true;
	}

	@Override
	public void drawScaledTexture(GuiCityMapper gui, int xTopLeft, int yTopLeft, float scale) {
		this.drawHelper(gui, xTopLeft, yTopLeft, 32, 0, scale);
	}

}
