package com.valeriotor.BTV.shoggoth.buildings;

import java.io.IOException;
import java.util.HashMap;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.gui.GuiCityMapper;
import com.valeriotor.BTV.items.TestItem.JSonStructureBuilder;
import com.valeriotor.BTV.shoggoth.BlockBuffer;
import com.valeriotor.BTV.shoggoth.BuildingTemplate;

import net.minecraft.block.Block;

public class BuildingClinic extends BuildingTemplate{

	public BuildingClinic(String name, int index) {
		super(20, 28, false, name, index);
		String file;
		try {
			file = Resources.toString(BeyondTheVeil.class.getResource("/assets/beyondtheveil/buildings/clinic.json"), Charsets.UTF_8);
			JSonStructureBuilder jssb = BeyondTheVeil.gson.fromJson(file, JSonStructureBuilder.class);
			HashMap<Block, byte[][]> map = jssb.getMap();
			this.buffer = new BlockBuffer(map, 20, 13, 28);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void drawScaledTexture(GuiCityMapper gui, int xTopLeft, int yTopLeft, float scale, int width, int height) {
		this.drawHelper(gui, xTopLeft, yTopLeft, 0, 32, scale);
	}

	@Override
	public boolean isDefault() {
		return true;
	}
	
	
}
