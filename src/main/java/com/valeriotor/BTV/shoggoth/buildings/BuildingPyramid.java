package com.valeriotor.BTV.shoggoth.buildings;

import com.valeriotor.BTV.gui.GuiCityMapper;
import com.valeriotor.BTV.shoggoth.BuildingTemplate;

public class BuildingPyramid extends BuildingTemplate{

	public BuildingPyramid(String name, int index) {
		super(28, 28, false, index, name);
	}

	@Override
	public void drawScaledTexture(GuiCityMapper gui, int xTopLeft, int yTopLeft, float scale) {
		this.drawHelper(gui, xTopLeft, yTopLeft, 64, 0, scale);
	}

	@Override
	public boolean isDefault() {
		return true;
	}

}
