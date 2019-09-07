package com.valeriotor.BTV.shoggoth.buildings;

import com.valeriotor.BTV.gui.GuiCityMapper;
import com.valeriotor.BTV.shoggoth.BuildingTemplate;

public class BuildingWall extends BuildingTemplate{

	public BuildingWall(String name, int index) {
		super(2, 2, true, index, name);
	}

	@Override
	public void drawScaledTexture(GuiCityMapper gui, int xTopLeft, int yTopLeft, float scale) {
		this.drawHelper(gui, xTopLeft, yTopLeft, 96, 0, scale);
	}

	@Override
	public boolean isDefault() {
		return true;
	}

}
