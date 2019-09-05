package com.valeriotor.BTV.shoggoth.buildings;

import com.valeriotor.BTV.gui.GuiCityMapper;
import com.valeriotor.BTV.shoggoth.BuildingTemplate;

public class BuildingTower extends BuildingTemplate{

	public BuildingTower(String name, int index) {
		super(16, 16, false, index, name);
	}

	@Override
	public boolean isDefault() {
		return true;
	}

	@Override
	public void drawScaledTexture(GuiCityMapper gui, int xTopLeft, int yTopLeft, float scale) {
		this.drawHelper(gui, xTopLeft, yTopLeft, 0, 0, scale);
	}
	
	

}
